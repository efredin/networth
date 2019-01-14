import React from 'react';
import { Container } from 'semantic-ui-react';
import { BalanceSheet } from './BalanceSheets';
import './home.less';

const Home = () => (
  <Container className="home">
    <BalanceSheet />
  </Container>
);

export default Home;
