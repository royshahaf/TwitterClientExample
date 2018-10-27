package authentication;

import java.util.Collections;
import java.util.List;

import entities.EntityKind;
import entities.Role;
import roles.RolesFetcher;

public class BasicActivityAuthenticator implements ActivityAuthenticator {

	private final RolesFetcher rolesFetcher;

	public BasicActivityAuthenticator(RolesFetcher rolesFetecher) {
		this.rolesFetcher = rolesFetecher;
	}

	public boolean authenticate(String activityName, String requestingUsername, String requestedUsername) {
		List<Role> requestingRoles = rolesFetcher.fetchRoles(EntityKind.USER, requestingUsername);
		List<Role> activityRoles = rolesFetcher.fetchRoles(EntityKind.ACTIVITY, activityName);
		if (requestingRoles == null || activityRoles == null) {
			return false;
		} else if (requestedUsername.equals(requestingUsername)) {
			return !Collections.disjoint(activityRoles, requestingRoles);
		} else {
			return requestingRoles.contains(Role.ADMIN);
		}
	}

}
