package access.simple;

import java.util.Collections;
import java.util.Set;

import com.google.inject.Inject;

import access.AccessChecker;
import activities.ActivityService;
import roles.Role;
import roles.RolesService;
import users.UserService;

public class SimpleAccessChecker implements AccessChecker {

	private final RolesService actvitiyRolesFetcher;
	private final RolesService userRolesFetcher;

	@Inject
	public SimpleAccessChecker(ActivityService activityRolesFetcher, UserService userRolesFetcher) {
		this.actvitiyRolesFetcher = activityRolesFetcher;
		this.userRolesFetcher = userRolesFetcher;
	}

	public boolean checkAccess(String activityName, String requestingUsername, String requestedUsername) {
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
