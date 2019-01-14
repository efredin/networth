import { state as bs } from './BalanceSheets';
import { state as cur } from './Currencies';

export default interface State {
  balanceSheets: bs.BalanceSheet;
  currencies: cur.CurrencyState;
}
