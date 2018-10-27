package authentication;

public interface Authenticator {
	public boolean authenticate(String activityId, String requestingUsername, String requestedUsername);
}
