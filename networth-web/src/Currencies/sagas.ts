import axios, { AxiosResponse } from 'axios';
import { all, call, put, takeLatest } from 'redux-saga/effects';
import { Actions, LoadAction, set } from './state';
import * as env from '../env.json';

/** Load an existing balance sheet */
function* handleLoad(action: LoadAction) {
  const url = `//${env.apiFqdn}/currencies`;
  const response: AxiosResponse<any> = yield call([axios, 'get'], url);
  yield put(set(response.data));
}

export default function*() {
  yield all([
    takeLatest(Actions.Load, handleLoad)
  ]);
}
