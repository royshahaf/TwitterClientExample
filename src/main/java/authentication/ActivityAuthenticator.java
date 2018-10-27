package authentication;

public interface ActivityAuthenticator {
	public boolean authenticate(String activityId, String requestingUsername, String requestedUsername);
}
