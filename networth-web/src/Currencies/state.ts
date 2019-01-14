import { Action } from 'redux';

export interface CurrencyList {
  [key: string]: string;
}

export interface CurrencyState extends CurrencyList { }
const initialState: CurrencyState = { };

export enum Actions {
  Load = '/currencies/load',
  Set = '/currencies/set'
}

export interface LoadAction {
  type: Actions.Load;
}
export function load(): LoadAction {
  return { type: Actions.Load };
}

export interface SetAction {
  type: Actions.Set;
  currencies: CurrencyList;
}
export function set(currencies: CurrencyList): SetAction {
  return {
    type: Actions.Set,
    currencies
  };
}

export function reducer(state: CurrencyState = initialState, action: Action) {
  switch (action.type) {

    case Actions.Set:
      const loadAction = action as SetAction;
      return { ...state, ...loadAction.currencies };

    default:
      return state;
  }
}
