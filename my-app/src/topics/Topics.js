import React, { Component } from 'react';
import TopicService from './TopicService';
import Topic from './Topic';
import Grid from '@material-ui/core/Grid';
import { withAlert } from 'react-alert'

class Topics extends Component {

    constructor(props) {
        super();
        this.state = { topics: [], requested: "adam", topicValue: 'default', value: "adam", requesting: props.requesting};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.handleTopicChange = this.handleTopicChange.bind(this);
        this.handleTopicSubmit = this.handleTopicSubmit.bind(this);
    }
    
    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        this.setState({requested: this.state.value});
        event.preventDefault();
    }

    handleTopicChange(event) {
        this.setState({topicValue: event.target.value});
    }

    handleTopicSubmit(event) {
        TopicService.addTopic(this.state.requesting, this.state.requested, this.state.topicValue);
        event.preventDefault();
    }

    async componentDidMount() {
        var intervalId = setInterval(async () => this.getTopics(), 1000);
        this.setState({intervalId: intervalId});
    }

    async getTopics() {
        const response = await TopicService.getTopics(this.state.requesting, this.state.requested);
        if (response) {
            this.setState({ topics: response.data });
        } else {
            this.props.alert.show("Are you sure you have permissions to view this?");
            this.setState({ topics: [] });
        }
    }

    componentWillReceiveProps(props) {
        this.setState({requesting: props.requesting});
    }

    componentWillUnmount() {
        clearInterval(this.state.intervalId);
    }

    render() {
        return (
          <div className="commentBox">
                <form onSubmit={this.handleTopicSubmit}>
                    <label>
                        Topic to add:
                        <input type="text" value={this.state.topicValue} onChange={this.handleTopicChange} />
                    </label>
                    <input type="submit" value="Add" />
                </form>
                <Grid container spacing={24}>
                    {this.state.topics.map(topic => (
                    <Grid item key={topic.name}>
                        <Topic topic={topic} requesting={this.state.requesting} requested={this.state.requested}></Topic>
                    </Grid>
                ))}
                </Grid>
          </div>
        );
    }
}

export default withAlert(Topics);