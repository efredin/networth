import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import createSagaMiddleware from 'redux-saga';
import * as BalanceSheets from './BalanceSheets';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware, combineReducers, compose } from 'redux';
import 'semantic-ui-css/semantic.min.css';

// setup redux dev tools
const composeEnhancers = typeof window === 'object' && (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    ? (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ })
    : compose;

const sagaMiddleware = createSagaMiddleware();
const store = createStore(
  combineReducers({
    balanceSheets: BalanceSheets.state.reducer,
  }),
  composeEnhancers(applyMiddleware(sagaMiddleware))
);

sagaMiddleware.run(BalanceSheets.sagas);
store.dispatch(BalanceSheets.state.init());

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root') as HTMLElement
);
