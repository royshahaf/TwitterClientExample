package app;

import authentication.ActivityAuthenticator;
import tags.TagService;
import twitter.TwitterService;

public class ClientApi {

	private final ActivityAuthenticator authenticator;

	public ClientApi(ActivityAuthenticator authenticator, TwitterService twitterService, TagService tagService) {
		this.authenticator = authenticator;
	}

	public Result receive(String requestingUsername, String requestedUsername) {
		if (authenticator.authenticate("receive", requestingUsername, requestedUsername)) {
			return new Result(true, "authenticated");
		} else {
			return new Result(false, "not authenticated");
		}
	}

	public Result add(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("add", requestingUsername, requestedUsername)) {
			return new Result(true, "authenticated");
		} else {
			return new Result(false, "not authenticated");
		}
	}

	public Result remove(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("remove", requestingUsername, requestedUsername)) {
			return new Result(true, "authenticated");
		} else {
			return new Result(false, "not authenticated");
		}
	}
	
	public Result edit(String requestingUsername, String requestedUsername, String topic) {
		if (authenticator.authenticate("edit", requestingUsername, requestedUsername)) {
			return new Result(true, "authenticated");
		} else {
			return new Result(false, "not authenticated");
		}
	}

	public Result view(String requestingUsername, String requestedUsername) {
		if (authenticator.authenticate("view", requestingUsername, requestedUsername)) {
			return new Result(true, "authenticated");
		} else {
			return new Result(false, "not authenticated");
		}
	}
}
