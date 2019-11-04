import React, { Component }  from 'react';
import { StyleSheet, Text, View, TouchableOpacity, NativeModules, Image } from 'react-native';
const simbolo = require('./imagens/simbolo.png');

export default class Home extends Component {
  
  componentDidMount() {
    NativeModules.Pagamento.iniciar();
  }

  render() {
    return (
        <View style={styles.containerTop}>
        <View style={{ flexDirection: 'row'}}>
            <Text style={styles.legendaTopBold}>Pagamento</Text>
            <Image style={{position: "absolute", bottom: 0, right: -98}} source={simbolo}/>
        </View> 

        <View style={styles.lineTop}/>

        <View style={{marginTop: 40,width:'90%', flexDirection: 'row', justifyContent:'space-between', marginLeft: 15, marginRight: 15}}>
            <Text style={{color: 'white', fontSize: 18}}>Tag</Text>
            <Text style={{color: 'white', fontSize: 18}}>#123456</Text>
        </View>

        <View style={styles.lineMiddle} />

        <TouchableOpacity style={styles.containerMiddle} onPress={() => this.props.navigation.navigate('EntradaSenha')}> 
            <Text style={{color: 'white', fontSize: 18, textAlign: 'center', marginTop: 25}}>Crédito</Text>
            <Text style={styles.legendaGrande}>Valor</Text>
            <Text style={styles.legendaGrande}>R$ 50,00</Text>
            <Text style={{color: 'white', fontSize: 18, textAlign: 'center', marginBottom: 25}}>Insira o cartão</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.buttonVoltar} onPress={() => this.props.navigation.navigate('CarregarTag')}>
            <Text style={styles.legenda}>Cancelar</Text>
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
  containerBottom: {
    alignItems: 'center',
    backgroundColor: '#7AEF85',
  },
  containerMiddle: {
    marginBottom: 10, 
    flex:3, 
    width: "90%", 
    borderRadius:10, 
    justifyContent: 'space-between', 
    backgroundColor: '#7AEF85'
  },
  legendaTopBold: {
    color: 'white',
    fontWeight: 'bold',
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
    buttonVoltar: { 
      alignContent: 'flex-end',
      justifyContent: 'center', 
      borderRadius:10, 
      height: 91, 
      marginBottom: 10, 
      width: "90%",
      backgroundColor: '#FF5E5E' 
    },
    buttonRemover: { 
      borderRadius:10, 
      backgroundColor: '#7AEF85' 
    },
    legenda: {
      color: 'white',
      fontSize: 18,
      textAlign: 'center'
    },
    legendaGrande: {
      color: 'white',
      fontSize: 36,
      textAlign: 'center'
    },
});