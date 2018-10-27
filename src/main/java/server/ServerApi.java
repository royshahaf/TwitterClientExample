package server;

import authentication.Authenticator;
import users.UserService;

public class ServerApi {

	private final Authenticator authenticator;
	private UserService topicService;

	public ServerApi(Authenticator authenticator, UserService topicService) {
		this.authenticator = authenticator;
		this.topicService = topicService;
	}

	public Result add(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("add", requestingUsername, requestedUsername)) {
			return topicService.add(requestedUsername, topic);
		} else {
			return new Result(false, "not authenticated");
		}
	}

	public Result delete(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("remove", requestingUsername, requestedUsername)) {
			return topicService.delete(requestedUsername, topic);
		} else {
			return new Result(false, "not authenticated");
		}
	}
	
	public Result edit(String requestingUsername, String requestedUsername, String previousTopic, String newTopic) {
		if (authenticator.authenticate("edit", requestingUsername, requestedUsername)) {
			return topicService.edit(requestedUsername, previousTopic, newTopic);
		} else {
			return new Result(false, "not authenticated");
		}
	}

	public Result view(String requestingUsername, String requestedUsername) {
		if (authenticator.authenticate("view", requestingUsername, requestedUsername)) {
			return topicService.getTopics(requestedUsername);
		} else {
			return new Result(false, "not authenticated");
		}
	}
}
