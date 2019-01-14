import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import createSagaMiddleware from 'redux-saga';
import * as BalanceSheets from './BalanceSheets';
import * as Currencies from './Currencies';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware, combineReducers, compose } from 'redux';
import 'semantic-ui-css/semantic.min.css';
import { all } from 'redux-saga/effects';

// setup redux dev tools
const composeEnhancers = typeof window === 'object' && (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    ? (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ })
    : compose;

const sagaMiddleware = createSagaMiddleware();
const store = createStore(
  combineReducers({
    balanceSheets: BalanceSheets.state.reducer,
    currencies: Currencies.state.reducer
  }),
  composeEnhancers(applyMiddleware(sagaMiddleware))
);

// combine all sagas
function* sagas() {
  yield all([
    BalanceSheets.sagas(),
    Currencies.sagas()
  ]);
}
sagaMiddleware.run(sagas);

// dispatch init events
store.dispatch(BalanceSheets.state.init());
store.dispatch(Currencies.state.load());

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root') as HTMLElement
);
