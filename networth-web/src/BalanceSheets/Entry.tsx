import React from 'react';
import { Entry } from './state';
import { Table, Form } from 'semantic-ui-react';
import NumberFormat from 'react-number-format';
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
          placeholder="Add another"
          className="label"
          fluid
          onChange={(e, data) => updateEntry({ ...entry, label: data.value })}
        />
      </Table.Cell>
      <Table.Cell>
        <Form.Input
          type="text"
          className="value"
          fluid
          children={
            <NumberFormat
              value={value}
              placeholder="0.00" 
              thousandSeparator={true}
              prefix="$" 
              onValueChange={(data) => updateEntry({ ...entry, value: data.value })}
            />
          }
        />
      </Table.Cell>
    </Table.Row>
  );
};
