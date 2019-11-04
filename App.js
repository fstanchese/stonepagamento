import React, { Component }  from 'react';

import Home from './Home';
import PagamentoCartao from './PagamentoCartao';
import { createSwitchNavigator, createAppContainer } from 'react-navigation';

export default class App extends Component {
  
  render() {
    return (
         <AppContainer />  
    );
  }
};

const AppSwitchNavigator = createSwitchNavigator({
  Home: { screen: Home },
  PagamentoCartao: { screen: PagamentoCartao },
});

const AppContainer = createAppContainer(AppSwitchNavigator);

