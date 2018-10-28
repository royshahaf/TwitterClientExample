package server;

import authentication.Authenticator;
import users.UserService;

public class ServerApi {

	public static final String AUTHENTICATION_FAILURE = "Authentication failed";
	private final Authenticator authenticator;
	private UserService userService;

	public ServerApi(Authenticator authenticator, UserService userService) {
		this.authenticator = authenticator;
		this.userService = userService;
	}

	public Result add(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("add", requestingUsername, requestedUsername)) {
			return userService.addTopic(requestedUsername, topic);
		} else {
			return new Result(false, AUTHENTICATION_FAILURE);
		}
	}

	public Result delete(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("remove", requestingUsername, requestedUsername)) {
			return userService.deleteTopic(requestedUsername, topic);
		} else {
			return new Result(false, AUTHENTICATION_FAILURE);
		}
	}

	public Result edit(String requestingUsername, String requestedUsername, String previousTopic, String newTopic) {
		if (authenticator.authenticate("edit", requestingUsername, requestedUsername)) {
			return userService.editTopics(requestedUsername, previousTopic, newTopic);
		} else {
			return new Result(false, AUTHENTICATION_FAILURE);
		}
	}

	public Result view(String requestingUsername, String requestedUsername) {
		if (authenticator.authenticate("view", requestingUsername, requestedUsername)) {
			return userService.getTopics(requestedUsername);
		} else {
			return new Result(false, AUTHENTICATION_FAILURE);
		}
	}
}
