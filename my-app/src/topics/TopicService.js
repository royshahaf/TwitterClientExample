import { Component } from 'react';
import axios from 'axios'

class TopicsService extends Component {
    
    static async getTopics(requesting, requested) {
        try {
            return await axios.get('http://localhost:8080/topics/view/' + requesting + '/' + requested)
        } catch (error) {
            console.error(error)
        }
    }
    
    static async addTopic(requesting, requested, topic) {
        try {
            return await axios.put('http://localhost:8080/topics/add/' + requesting + '/' + requested + '/' + topic);
        } catch (error) {
            console.error(error)
        }
    }

    static async deleteTopic(requesting, requested, topic) {
        try {
            return await axios.delete('http://localhost:8080/topics/delete/' + requesting + '/' + requested + '/' + topic);
        } catch (error) {
            console.error(error)
        }
    }

    static async editTopic(requesting, requested, prevTopic, newTopic) {
        try {
            return await axios.post('http://localhost:8080/topics/edit/' + requesting + '/' + requested + '/' + 
                    prevTopic + '/' + newTopic);
        } catch (error) {
            console.error(error)
        }
    }

}

export default TopicsService;