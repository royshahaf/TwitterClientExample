package access;

public interface AccessChecker {
	public boolean checkAccess(String activityId, String requestingUsername, String requestedUsername);
}
