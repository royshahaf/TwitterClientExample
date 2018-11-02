import React, { Component } from 'react';
import './App.css';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import Topics from './topics/Topics';
import MessageDisplay from './messages/MessageDisplay';

class App extends Component {
  constructor (props) {
    super(props);
    this.state={requesting: "adam", requested: "adam", requestingValue: "adam", requestedValue: "adam"};

    this.handleRequestingChange = this.handleRequestingChange.bind(this);
    this.handleRequestingSubmit = this.handleRequestingSubmit.bind(this);

    this.handleRequestedChange = this.handleRequestedChange.bind(this);
    this.handleRequestedSubmit = this.handleRequestedSubmit.bind(this);
  }
  
  handleRequestingChange(event) {
    this.setState({requestingValue: event.target.value});
  }

  handleRequestingSubmit(event) {
    this.setState({requesting: this.state.requestingValue});
    event.preventDefault();
  }

  handleRequestedChange(event) {
    this.setState({requestedValue: event.target.value});
  }

  handleRequestedSubmit(event) {
      this.setState({requested: this.state.requestedValue});
      event.preventDefault();
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleRequestingSubmit}>
          <label>
            Username (requesting):
            <input type="text" value={this.state.requestingValue} onChange={this.handleRequestingChange} />
          </label>
          <input type="submit" value="Submit" />
        </form>
        <form onSubmit={this.handleRequestedSubmit}>
          <label>
              Username (requested):
              <input type="text" value={this.state.requestedValue} onChange={this.handleRequestedChange} />
          </label>
          <input type="submit" value="Submit" />
        </form>
        <Tabs>
          <TabList>
            <Tab>Tweets</Tab>
            <Tab>Topics</Tab>
          </TabList>

          <TabPanel>
            <MessageDisplay></MessageDisplay>
          </TabPanel>
          <TabPanel>
            <Topics requesting={this.state.requesting}></Topics>
          </TabPanel>
        </Tabs>
      </div>
    );
  }
}

export default App;
