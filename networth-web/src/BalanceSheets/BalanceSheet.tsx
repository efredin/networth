import React from 'react';
import { Grid, Table, Segment, Header, Button } from 'semantic-ui-react';
import { Dispatch, AnyAction } from 'redux';
import { connect } from 'react-redux';
import EntryGroup from './EntryGroup';
import NumberFormat from 'react-number-format';
import * as State from './state';
import './BalanceSheet.less';

export interface BalanceSheetStateProps extends State.BalanceSheet {
}

export interface BalanceSheetDispatchProps {
  create: () => void;
  createEntry: (balanceSheetId: string, entry: Partial<State.Entry>, entryType: State.EntryType) => void;
  updateEntry: (balanceSheetId: string, entry: State.Entry, entryType: State.EntryType) => void;
  deleteEntry: (balanceSheetId: string, entryId: string, entryType: State.EntryType) => void;
}

export interface BalanceSheetProps extends BalanceSheetStateProps, BalanceSheetDispatchProps {
}

const sum = (entries: State.Entry[]) => entries
      .map(e => parseFloat(e.value) || 0)
      .reduce((s, v) => s + v, 0);

export class BalanceSheet extends React.Component<BalanceSheetProps> {

  createOrUpdateEntry = (entry: State.Entry, entryType: State.EntryType) => {
    const { id, createEntry, updateEntry, deleteEntry } = this.props;
    if (entry.id) {
      if (!entry.label && !entry.value) {
        deleteEntry(id, entry.id, entryType);
      } else {
        updateEntry(id, entry, entryType);
      }
    } else {
      createEntry(id, entry, entryType);
    }
  }

  render() {
    const { id, create, assets, liabilities } = this.props;

    if (!id) {
      return <Segment basic loading={true} />;
    }

    // split assets & liabilities into groups
    const cashAndInvestments = assets.filter(a => !a.longTerm);
    const longTermAssets = assets.filter(a => a.longTerm);
    const shortTermLiabilities = liabilities.filter(l => !l.longTerm);
    const longTermDebt = liabilities.filter(l => l.longTerm);

    // calculate totals 
    const totalAssets = sum(assets);
    const totalLiabilities = sum(liabilities);
    const netWorth = totalAssets - totalLiabilities;

    return (
      <Grid container columns="equal" stackable className="balanceSheet">
        <Grid.Row>
          <Grid.Column>
            <Header as="h1">Net Worth Calculator</Header>
          </Grid.Column>
        </Grid.Row>
        <Grid.Row>
          <Grid.Column>
            <Table singleLine>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell>Net Worth</Table.HeaderCell>
                  <Table.HeaderCell textAlign="right">
                    <NumberFormat 
                      value={netWorth} 
                      displayType="text" 
                      decimalScale={2} 
                      fixedDecimalScale={true} 
                      thousandSeparator={true}
                      prefix="$"
                      color={netWorth >= 0 ? 'green' : 'red'}
                    />
                  </Table.HeaderCell>
                </Table.Row>
              </Table.Header>
            </Table>
          </Grid.Column>
        </Grid.Row>
        <Grid.Row>
          <Grid.Column>
            <Table singleLine>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell colSpan={2}>Assets</Table.HeaderCell>
                </Table.Row>
              </Table.Header>
              <Table.Body>
                <EntryGroup 
                  entries={cashAndInvestments}
                  label="Cash & Investments"
                  updateEntry={(entry) => this.createOrUpdateEntry({ ...entry, longTerm: false }, 'asset')}
                />
                <EntryGroup 
                  entries={longTermAssets}
                  label="Long Term Assets"
                  updateEntry={(entry) => this.createOrUpdateEntry({ ...entry, longTerm: true }, 'asset')}
                />
              </Table.Body>
              <Table.Footer>
                <Table.Row>
                  <Table.Cell>Total Assetes</Table.Cell>
                  <Table.Cell textAlign="right">
                    <NumberFormat 
                      value={totalAssets} 
                      displayType="text" 
                      decimalScale={2} 
                      fixedDecimalScale={true} 
                      thousandSeparator={true}
                      prefix="$" 
                    />
                  </Table.Cell>
                </Table.Row>
              </Table.Footer>
            </Table>
          </Grid.Column>
          <Grid.Column>
            <Table singleLine>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell colSpan={2}>Liabilities</Table.HeaderCell>
                </Table.Row>
              </Table.Header>
              <Table.Body>
              <EntryGroup 
                  entries={shortTermLiabilities}
                  label="Short Term Liabilities"
                  updateEntry={(entry) => this.createOrUpdateEntry({ ...entry, longTerm: false }, 'liability')}
                />
                <EntryGroup 
                  entries={longTermDebt}
                  label="Long Term Debt"
                  updateEntry={(entry) => this.createOrUpdateEntry({ ...entry, longTerm: true }, 'liability')}
                />
              </Table.Body>
              <Table.Footer>
                <Table.Row>
                  <Table.Cell>Total Liabilities</Table.Cell>
                  <Table.Cell textAlign="right">
                    <NumberFormat 
                      value={totalLiabilities} 
                      displayType="text" 
                      decimalScale={2} 
                      fixedDecimalScale={true} 
                      thousandSeparator={true}
                      prefix="$" 
                    />
                  </Table.Cell>
                </Table.Row>
              </Table.Footer>
            </Table>
          </Grid.Column>
        </Grid.Row>
        <Grid.Row>
          <Grid.Column textAlign="center">
            <Button onClick={() => create()} content="Start Over" />
          </Grid.Column>
        </Grid.Row>
      </Grid>
    );
  }
}

export default connect<BalanceSheetStateProps, BalanceSheetDispatchProps>(
  (state: any) => state.balanceSheets,
  (dispatch: Dispatch<AnyAction>) => ({
    create: () => dispatch(State.create()),
    createEntry: (balanceSheetId: string, entry: Partial<State.Entry>, entryType: State.EntryType) => dispatch(State.createEntry(balanceSheetId, entry, entryType)),
    updateEntry: (balanceSheetId: string, entry: State.Entry, entryType: State.EntryType) => dispatch(State.updateEntry(balanceSheetId, entry, entryType)),
    deleteEntry: (balanceSheetId: string, entryId: string, entryType: State.EntryType) => dispatch(State.deleteEntry(balanceSheetId, entryId, entryType))
  })
)(BalanceSheet);
