package authentication.simple;

import java.util.Collections;
import java.util.Set;

import authentication.Authenticator;
import roles.Role;
import roles.RolesService;

public class SimpleAuthenticator implements Authenticator {

	private final RolesService actvitiyRolesFetcher;
	private final RolesService userRolesFetcher;

	public SimpleAuthenticator(RolesService activityRolesFetcher, RolesService userRolesFetcher) {
		this.actvitiyRolesFetcher = activityRolesFetcher;
		this.userRolesFetcher = userRolesFetcher;
	}

	public boolean authenticate(String activityName, String requestingUsername, String requestedUsername) {
		Set<Role> activityRoles = actvitiyRolesFetcher.getRoles(activityName);
		Set<Role> requestingRoles = userRolesFetcher.getRoles(requestingUsername);
		if (requestingRoles == null || activityRoles == null) {
			return false;
		} else if (requestedUsername.equals(requestingUsername)) {
			return !Collections.disjoint(activityRoles, requestingRoles);
		} else {
			return requestingRoles.contains(Role.ADMIN);
		}
	}

}
