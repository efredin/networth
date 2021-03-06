import { Action } from 'redux';

export interface BalanceSheetState {
  id: string;
  currency: string;
  assets: Entry[];
  liabilities: Entry[];
  pendingOperationCount: number;
}

export interface Entry {
  id: string;
  label: string;
  longTerm: boolean;
  value: string;
}

const initialState: BalanceSheetState = {
  id: undefined,
  currency: 'CAD',
  assets: [],
  liabilities: [],
  pendingOperationCount: 0
};

export enum Actions {
  Init = '/balanceSheets/init',
  Set = '/balanceSheets/set',
  Load = '/balanceSheets/load',
  Create = '/balanceSheets/create',
  SetEntry = '/balanceSheets/entry/set',
  CreateEntry = '/balanceSheetse/entry/create',
  UpdateEntry = '/balanceSheets/entry/update',
  DeleteEntry = '/balanceSheets/entry/delete',
  IncrementOperationCount = '/balanceSheets/operations/increment',
  DecrementOperationCount = '/balanceSheets/operations/decrement',
  Convert = '/balanceSheets/convert'
}

export interface InitAction {
  type: Actions.Init;
}
export function init(): InitAction {
  return { type: Actions.Init };
}

export interface SetAction {
  type: Actions.Set;
  balanceSheet: BalanceSheetState;
}
export function set(balanceSheet: BalanceSheetState): SetAction {
  return {
    type: Actions.Set,
    balanceSheet
  };
}

export interface LoadAction {
  type: Actions.Load;
  id: string;
}
export function load(id: string) {
  return {
    type: Actions.Load,
    id
  };
}

export interface CreateAction {
  type: Actions.Create;
}
export function create(): CreateAction {
  return {
    type: Actions.Create
  };
}

export interface SetEntryAction {
  type: Actions.SetEntry;
  entry: Entry;
  entryType: EntryType;
}
export function setEntry(entry: Entry, entryType: EntryType): SetEntryAction {
  return {
    type: Actions.SetEntry,
    entry,
    entryType
  };
}

export interface CreateEntryAction {
  type: Actions.CreateEntry;
  balanceSheetId: string;
  entry: Partial<Entry>;
  entryType: EntryType;
}
export function createEntry(balanceSheetId: string, entry: Partial<Entry>, entryType: EntryType): CreateEntryAction {
  return {
    type: Actions.CreateEntry,
    balanceSheetId,
    entry,
    entryType
  };
}

export type EntryType = 'asset' | 'liability';
export interface UpdateEntryAction {
  type: Actions.UpdateEntry;
  balanceSheetId: string;
  entry: Entry;
  entryType: EntryType;
}
export function updateEntry(balanceSheetId: string, entry: Entry, entryType: EntryType): UpdateEntryAction {
  return {
    type: Actions.UpdateEntry,
    balanceSheetId,
    entry,
    entryType
  };
}

export interface DeleteEntryAction {
  type: Actions.DeleteEntry;
  balanceSheetId: string;
  entryId: string;
  entryType: EntryType;
}
export function deleteEntry(balanceSheetId: string, entryId: string, entryType: EntryType): DeleteEntryAction {
  return {
    type: Actions.DeleteEntry,
    balanceSheetId,
    entryId,
    entryType
  };
}

export interface IncrementOperationCountAction {
  type: Actions.IncrementOperationCount;
}
export function incrementOperationCount(): IncrementOperationCountAction {
  return { type: Actions.IncrementOperationCount };
}

export interface DecrementOperationCountAction {
  type: Actions.DecrementOperationCount;
}
export function decrementOperationCount(): DecrementOperationCountAction {
  return { type: Actions.DecrementOperationCount };
}

export interface ConvertAction {
  type: Actions.Convert;
  balanceSheetId: string;
  currency: string;
}
export function convert(balanceSheetId: string, currency: string): ConvertAction {
  return {
    type: Actions.Convert,
    balanceSheetId,
    currency
  };
}

export function reducer(state: BalanceSheetState = initialState, action: Action) {
  const sheet = { ...state };
  let entry: Entry;
  let resource: string;
  let ix: number;
  
  const getResource = (entryType: EntryType) => entryType === 'asset' ? 'assets' : 'liabilities';

  switch (action.type) {

    // Set the full balance sheet
    case Actions.Set:
      const { balanceSheet } = (action as SetAction);
      return { ...state, ...balanceSheet };

    // Set a single entry of a balance sheet
    case Actions.SetEntry:
      const setAction = action as SetEntryAction;
      entry = setAction.entry;
      resource = getResource(setAction.entryType);
      ix = sheet[resource].findIndex(e => e.id === entry.id || !e.id);
      if (ix >= 0) {
        sheet[resource] = [
          ...sheet[resource].slice(0, ix),
          entry,
          ...sheet[resource].slice(ix + 1)
        ];
      } else {
        sheet[resource] = [
          ...sheet[resource],
          entry
        ];
      }
      return sheet;

    // Remove a single entry from a balance sheet
    case Actions.DeleteEntry:
      const deleteAction = action as DeleteEntryAction;
      resource = getResource(deleteAction.entryType);
      ix = sheet[resource].findIndex(e => e.id === deleteAction.entryId);
      if (ix >= 0) {
        sheet[resource] = [
          ...sheet[resource].slice(0, ix),
          ...sheet[resource].slice(ix + 1)
        ];
      }
      return sheet;

    case Actions.IncrementOperationCount:
      return { ...state, pendingOperationCount: state.pendingOperationCount + 1 };

    case Actions.DecrementOperationCount:
      return { ...state, pendingOperationCount: state.pendingOperationCount - 1 }; 

    case Actions.Convert:
      const convertAction = action as ConvertAction;
      return { ...state, currency: convertAction.currency };

    default:
      return state;
  }
}
