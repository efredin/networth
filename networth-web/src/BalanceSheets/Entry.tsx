import React from 'react';
import { Entry } from './state';
import { Table, Form } from 'semantic-ui-react';
import * as State from './state';

export interface EntryProps {
  entry: Entry;
  updateEntry: (entry: State.Entry) => void;
}

export default (props: EntryProps) => {
  const { updateEntry, entry } = props;
  const { label, value } = entry;
  return (
    <Table.Row>
      <Table.Cell>
        <Form.Input
          type="text"
          value={label}
          placeholder="Label"
          className="label"
          fluid
          onChange={(e, data) => updateEntry({ ...entry, label: data.value })}
        />
      </Table.Cell>
      <Table.Cell>
        <Form.Input
          type="text"
          value={value}
          placeholder="0.00"
          className="value"
          fluid
          onChange={(e, data) => updateEntry({ ...entry, value: data.value })}
        />
      </Table.Cell>
    </Table.Row>
  );
};
