package server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import roles.Role;
import users.Topic;
import users.User;
import users.UserService;

public class UserServiceStub implements UserService {

	Map<String, User> users;

	public UserServiceStub(Map<String, User> testUsers) {
		users = new HashMap<>(testUsers);
	}

	@Override
	public Set<Role> getRoles(String id) {
		if (users.get(id) == null) {
			return Collections.emptySet();
		} else {
			return users.get(id).getRoles();
		}
	}

	@Override
	public User getUser(String id) {
		return users.get(id);
	}

	@Override
	public Result addTopic(String requestedUsername, String topicName) {
		User user = users.get(requestedUsername);
		if (user == null) {
			return new Result(false, "Can't add topic to non-existing user");
		} else {
			Set<Topic> topics = user.getTopics();
			Topic topic = new Topic(topicName);
			if (topics.contains(topic)) {
				return new Result(false, "Topic already exists");
			} else {
				topics.add(topic);
				User newUser = new User(user.getName(), topics, user.getRoles());
				users.put(requestedUsername, newUser);
				return new Result(true, "Topic added");
			}
		}
	}

	@Override
	public Result deleteTopic(String requestedUsername, String topicName) {
		User user = users.get(requestedUsername);
		if (user == null) {
			return new Result(false, "Can't delete topic from non-existing user");
		} else {
			Set<Topic> topics = user.getTopics();
			Topic topic = new Topic(topicName);
			if (!topics.contains(topic)) {
				return new Result(false, "Topic doesn't exists");
			} else {
				topics.remove(topic);
				User newUser = new User(user.getName(), topics, user.getRoles());
				users.put(requestedUsername, newUser);
				return new Result(true, "Topic delete");
			}
		}
	}

	@Override
	public Result editTopics(String requestedUsername, String previousTopic, String newTopic) {
		User user = users.get(requestedUsername);
		if (user == null) {
			return new Result(false, "Can't edit topic for non-existing user");
		} else {
			Set<Topic> topics = user.getTopics();
			Topic topic = new Topic(previousTopic);
			if (!topics.contains(topic)) {
				return new Result(false, "Topic to edit doesn't exists");
			} else {
				topics.remove(topic);
				topics.add(new Topic(newTopic));
				User newUser = new User(user.getName(), topics, user.getRoles());
				users.put(requestedUsername, newUser);
				return new Result(true, "Topic delete");
			}
		}
	}

	@Override
	public Result getTopics(String requestedUsername) {
		User user = users.get(requestedUsername);
		if (user == null) {
			return new Result(false, "Can't get topics from non-existing user");
		} else {
			Set<Topic> topics = user.getTopics();
			return new Result(true, topics.toString());
		}
	}

}
