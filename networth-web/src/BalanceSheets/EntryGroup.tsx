import React from 'react';
import { Table } from 'semantic-ui-react';
import Entry from './Entry';
import * as State from './state';

export interface EntryGroupProps {
  label: string;
  entries: State.Entry[];
  updateEntry: (entry: State.Entry) => void;
}

export default class EntryGroup extends React.Component<EntryGroupProps> {
  render() {
    const { label, updateEntry } = this.props;

    // append a blank entry to the end of the group
    const entries = [ ...this.props.entries ];
    if (!entries.length || entries[entries.length - 1].id) {
      entries.push({ id: null } as State.Entry);
    }
    
    return (
      <>
        <Table.Row className="group">
          <Table.Cell colSpan={2}>{label}</Table.Cell>
        </Table.Row>
        {entries.map((entry, ix) => (
          <Entry entry={entry} key={ix} updateEntry={updateEntry} />
        ))}
      </>
    );
  }
}
