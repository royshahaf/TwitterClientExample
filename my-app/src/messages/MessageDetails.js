import React, { Component } from 'react';
import Tweet from 'react-tweet'

class MessageDetails extends Component {
    render() {
        const linkProps = {target: '_blank', rel: 'noreferrer'}
        // return <Card>
        // {this.props.message.text}
        // </Card>
        return <Tweet data={this.props.message} linkProps={linkProps}/>
    }
}

export default MessageDetails;