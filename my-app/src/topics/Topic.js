import React, { Component } from 'react';
import TopicsService from './TopicService';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';

class Topic extends Component {
    constructor(props) {
        super()
        this.state = {requesting: props.requesting, requested: props.requested, topic: props.topic.name, topicValue: "default"}

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }

    handleRemove() {
        TopicsService.deleteTopic(this.state.requesting, this.state.requested, this.state.topic)
    }

    handleChange(event) {
        this.setState({topicValue: event.target.value});
    }

    handleSubmit(event) {
        TopicsService.editTopic(this.state.requesting, this.state.requested, this.state.topic, this.state.topicValue);
        event.preventDefault();
    }

    render() {
        return (
          <Card>
            <div>
                {this.props.topic.name}
                <form onSubmit={this.handleSubmit}>
                    <label>
                        New topic:
                        <input type="text" value={this.state.topicValue} onChange={this.handleChange} />
                    </label>
                    <input type="submit" value="Change" />
                </form>
                <Button variant="contained" color="primary" onClick={this.handleRemove.bind(this)}>Remove</Button>
            </div>
          </Card>
        );
    }
}

export default Topic;