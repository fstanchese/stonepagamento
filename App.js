import React, { Component }  from 'react';
import { StyleSheet, Text, View, TouchableOpacity, Image } from 'react-native';
const simbolo = require('./imagens/simbolo.png');

export default class CarregarTag extends Component {
    render() {
        return (
          <View style={styles.containerTop}>
              <View style={{ flexDirection: 'row'}}>

              <Text style={styles.legendaTopBold}>Pagamento</Text>
              <Image style={{position: "absolute", bottom: 0, right: -98}} source={simbolo}/>
              </View>
       
              <View style={styles.lineTop}></View>

              <View style={{marginTop: 50, width:'90%', flexDirection: 'row', justifyContent:'space-between'}}>
                  <Text style={{color: 'white', fontSize: 18}}>Tag</Text>
                  <Text style={{color: 'white', fontSize: 18}}>#123456</Text>
              </View>

              <View style={styles.lineMiddle} />

              <View style={{width:'90%', flexDirection: 'row', justifyContent:'space-between'}}>
                <Text style={{color: 'white', fontSize: 36}}>Valor</Text>
                <Text style={{color: 'white', fontSize: 36}}>R$ 50,00</Text>
              </View>

              <View style={styles.lineMiddle} />

              <View style={{marginBottom:30, marginTop: 10}}>
              <Text style={styles.legenda}>Forma de Pagamento</Text>
              </View>
              <TouchableOpacity style={styles.buttonSmall} onPress={() => this.props.navigation.navigate('PagamentoCartao')}>
                <Text style={styles.legenda}>Cartão de débito</Text>
              </TouchableOpacity> 
              <TouchableOpacity style={styles.buttonSmall} onPress={() => this.props.navigation.navigate('PagamentoCartao')}>
                <Text style={styles.legenda}>Cartão de crédito</Text>
              </TouchableOpacity> 
              <TouchableOpacity style={styles.buttonSmall} onPress={() => this.props.navigation.navigate('PagamentoDinheiro')}>
                <Text style={styles.legenda}>Dinheiro</Text>
              </TouchableOpacity>  

             <TouchableOpacity style={styles.buttonVoltar} onPress={() => this.props.navigation.navigate('Caixa')}>
                <Text style={styles.legenda}>Voltar</Text>
              </TouchableOpacity>                        
              </View>
        );
    }
};

const styles = StyleSheet.create({
  containerTop: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: '#404040',
  },
  buttonSmall: { 
    justifyContent: 'center', 
    borderRadius:10, 
    height: 72, 
    width: "90%",
    backgroundColor: '#606060' ,
    marginBottom: 10
  },
  legenda: {
    color: 'white',
    fontSize: 18,
    textAlign: 'center'
  },
  buttonVoltar: { 
    flex: 2,
    alignContent: 'space-between',
    justifyContent: 'center', 
    borderRadius:10, 
    height: 91, 
    marginBottom: 10, 
    width: "90%",
    backgroundColor: '#FF5E5E' 
  },
  legendaTopBold: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 25,
    textAlign: 'center'
  },
  legendaTop: {
    fontFamily: 'Josefin Sans-Thin',
    color: 'white',
    fontSize: 25,
    textAlign: 'center'
  },
  lineTop: {
    borderWidth: 1,
    borderColor:'#60B8F3',
    width: "90%"    
  },
  lineMiddle: {
    borderWidth: 1,
    borderColor:'#606060',
    width: "90%",
    marginBottom: 10, 
    marginTop: 10, 
  },
});
    
