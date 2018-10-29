package server;

import com.google.inject.Inject;

import access.AccessChecker;
import users.UserService;

public class ServerApi {

	public static final String ACCESS_DENIED = "Access denied";
	private final AccessChecker accessChecker;
	private UserService userService;

	@Inject
	public ServerApi(AccessChecker accessChecker, UserService userService) {
		this.accessChecker = accessChecker;
		this.userService = userService;
	}

	public Result add(String requestingUsername, String requestedUsername, String topic) {
		if (accessChecker.checkAccess("add", requestingUsername, requestedUsername)) {
			return userService.addTopic(requestedUsername, topic);
		} else {
			return new Result(false, ACCESS_DENIED);
		}
	}

	public Result delete(String requestingUsername, String requestedUsername, String topic) {
		if (accessChecker.checkAccess("remove", requestingUsername, requestedUsername)) {
			return userService.deleteTopic(requestedUsername, topic);
		} else {
			return new Result(false, ACCESS_DENIED);
		}
	}

	public Result edit(String requestingUsername, String requestedUsername, String previousTopic, String newTopic) {
		if (accessChecker.checkAccess("edit", requestingUsername, requestedUsername)) {
			return userService.editTopics(requestedUsername, previousTopic, newTopic);
		} else {
			return new Result(false, ACCESS_DENIED);
		}
	}

	public Result view(String requestingUsername, String requestedUsername) {
		if (accessChecker.checkAccess("view", requestingUsername, requestedUsername)) {
			return userService.getTopics(requestedUsername);
		} else {
			return new Result(false, ACCESS_DENIED);
		}
	}
}
