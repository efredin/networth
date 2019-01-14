import { takeEvery, all, put, takeLatest, call, select } from 'redux-saga/effects';
import {
  Actions, 
  InitAction, 
  CreateAction, 
  LoadAction, 
  UpdateEntryAction,
  CreateEntryAction,
  DeleteEntryAction,
  Entry,
  EntryType,
  create,
  load,
  set,
  setEntry,
  ConvertAction,
  incrementOperationCount,
  decrementOperationCount
} from './state';
import RootState from '../State';
import axios, { AxiosResponse } from 'axios';

// todo: normally this would be loaded via json on app initialization
// and a config file would not be part of the build
import * as env from '../env.json';
import { delay } from 'redux-saga';

const getEntry = (entryType: EntryType) => 
  entryType === 'asset' ? 'assets' : 'liabilities';

/** Initialize the application. */
function* handleInit(action: InitAction) {
  // attempt to restore id from local storage
  const balanceSheetId = yield call([localStorage, 'getItem'], 'balanceSheetId');
  if (balanceSheetId) {
    yield put(load(balanceSheetId));
  } else {
    yield put(create());
  }
}

/** Load an existing balance sheet */
function* handleLoad(action: LoadAction) {
  const url = `//${env.apiFqdn}/balancesheets/${action.id}`;
  const response: AxiosResponse<any> = yield call([axios, 'get'], url);
  yield put(set(response.data));
}

/** Create a new balance sheet */
function* handleCreate(action: CreateAction) {
  const currency = yield select((state: RootState) => state.balanceSheets.currency) || 'CAD';
  const url = `//${env.apiFqdn}/balancesheets`;
  const response: AxiosResponse<any> = yield call([axios, 'post'], url, {
    currency,
    assets: [
      { label: 'Chequing', longTerm: false }, 
      { label: 'Savings for Taxes', longTerm: false },
      { label: 'Rainy Day Fund', longTerm: false },
      { label: 'Savings for Fun', longTerm: false },
      { label: 'Savings for Personal Development', longTerm: false },
      { label: 'Investment 1', longTerm: false },
      { label: 'Investment 2', longTerm: false },
      { label: 'Investment 3', longTerm: false },
      { label: 'Investment 4', longTerm: false },
      { label: 'Investment 5', longTerm: false },
      { label: 'Primary Home', longTerm: true },
      { label: 'Second Home', longTerm: true },
      { label: 'Other', longTerm: true },
    ],
    liabilities: [
      { label: 'Credit Card 1', longTerm: false },
      { label: 'Credit Card 2', longTerm: false },
      { label: 'Other', longTerm: false },
      { label: 'Mortgage 1', longTerm: true },
      { label: 'Mortgage 2', longTerm: true },
      { label: 'Investment Loan', longTerm: true },
      { label: 'Student Loan', longTerm: true },
      { label: 'Car Loan', longTerm: true }
    ]
  });
  yield put(set(response.data));
  yield call([localStorage, 'setItem'], 'balanceSheetId', response.data.id);
}

/** Create a new balance sheet entry (asset / liability) */
function* handleCreateEntry(action: CreateEntryAction) {
  yield put(setEntry(action.entry as Entry, action.entryType));
  yield put(incrementOperationCount());

  const resource = getEntry(action.entryType);
  const url = `//${env.apiFqdn}/balancesheets/${action.balanceSheetId}/${resource}`;
  const response: AxiosResponse<any> = yield call([axios, 'post'], url, {
    // defaults to prevent null values in text boxes
    label: '', 
    value: 0.00,
    ...action.entry
  });
  yield put(setEntry(response.data, action.entryType));
  yield put(decrementOperationCount());
}

/** Update an existing balance sheet entry */
function* handleUpdateEntry(action: UpdateEntryAction) {
  yield put(setEntry(action.entry, action.entryType));
  
  // delay combined with takeLatest is effectively debounce
  yield call(delay, 1000);
  yield put(incrementOperationCount());

  const resource = getEntry(action.entryType);
  const url = `//${env.apiFqdn}/balancesheets/${action.balanceSheetId}/${resource}/${action.entry.id}`;
  yield call([axios, 'put'], url, action.entry);
  yield put(decrementOperationCount());
}

function* handleDeleteEntry(action: DeleteEntryAction) {
  yield put(incrementOperationCount());
  const resource = getEntry(action.entryType);
  const url = `//${env.apiFqdn}/balancesheets/${action.balanceSheetId}/${resource}/${action.entryId}`;
  yield call([axios, 'delete'], url);
  yield put(decrementOperationCount());
}

function* handleConvert(action: ConvertAction) {
  yield put(incrementOperationCount());
  const url = `//${env.apiFqdn}/balancesheets/${action.balanceSheetId}/convert/${action.currency}`;
  const response: AxiosResponse<any> = yield call([axios, 'put'], url);
  yield put(set(response.data));
  yield put(decrementOperationCount());
}

export default function*() {
  yield all([
    takeEvery(Actions.Init, handleInit),
    takeLatest(Actions.Load, handleLoad),
    takeLatest(Actions.Create, handleCreate),
    takeEvery(Actions.CreateEntry, handleCreateEntry),
    takeLatest(Actions.UpdateEntry, handleUpdateEntry),
    takeEvery(Actions.DeleteEntry, handleDeleteEntry),
    takeLatest(Actions.Convert, handleConvert)
  ]);
}
