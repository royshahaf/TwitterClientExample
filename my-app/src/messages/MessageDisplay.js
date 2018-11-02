import React, { Component } from 'react';
import MessageDetails from './MessageDetails';
import Websocket from 'react-websocket';

class MessageDisplay extends Component {

    constructor(){
        super();
        this.state = { messages: [] }
    }

    handleData(data) {
        var messageList = this.state.messages;
        if (messageList.length > 20) {
            messageList.pop();
        }
        messageList.unshift(JSON.parse(data));
        this.setState({messages: messageList});
    }

    render() {
        var messageList = this.state.messages.map(function(message) {
            return (<MessageDetails key={message.id + message.text} message={message} />);
        });
        return (

          <div className="commentBox">
            <Websocket url='ws://localhost:8090/tweets'
              onMessage={this.handleData.bind(this)}/>
            {messageList}
          </div>
        );
    }
}

export default MessageDisplay;