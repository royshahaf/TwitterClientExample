import { Component } from 'react'
import './index.css';
import { Provider as AlertProvider } from 'react-alert'
import AlertTemplate from 'react-alert-template-basic'
import App from './App';
import * as serviceWorker from './serviceWorker';
var React = require('react')
var createReactClass = require('create-react-class');

    module.exports=createReactClass({
            render:function(){
                return(
                    <div>
                        <h1> the list  </h1>
                    </div>   
                )
            })

var ReactDOM = require('react-dom'); 
ReactDOM.render(<GroceryItemList/>,app);

	    // optional cofiguration
const options = {
    position: 'bottom center',
    timeout: 5000,
    offset: '30px',
    transition: 'scale'
  }
   
  class Root extends Component  {
    render () {
      return (
        <AlertProvider template={AlertTemplate} {...options}>
          <App />
        </AlertProvider>
      )
    }
  }
   
ReactDOM.render(<Root />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
