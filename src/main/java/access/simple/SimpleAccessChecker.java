package access.simple;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import access.AccessChecker;
import activities.ActivityService;
import roles.Role;
import roles.RolesService;
import users.UserService;

public class SimpleAccessChecker implements AccessChecker {

	private final RolesService actvitiyRolesFetcher;
	private final RolesService userRolesFetcher;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	public SimpleAccessChecker(ActivityService activityRolesFetcher, UserService userRolesFetcher) {
		this.actvitiyRolesFetcher = activityRolesFetcher;
		this.userRolesFetcher = userRolesFetcher;
	}

	public boolean checkAccess(String activityName, String requestingUsername, String requestedUsername) {
		Set<Role> activityRoles = actvitiyRolesFetcher.getRoles(activityName);
		Set<Role> requestingRoles = userRolesFetcher.getRoles(requestingUsername);
		if (requestingRoles == null || activityRoles == null) {
			logger.warn("No roles exist for either requesting user or requested activity");
			return false;
		} else if (requestedUsername.equals(requestingUsername)) {
			boolean sharingRoles = !Collections.disjoint(activityRoles, requestingRoles);
			if (!sharingRoles) {
				logger.warn("Activity roles and user roles do not intersect");
			}
			return sharingRoles;
		} else {
			boolean isAdmin = requestingRoles.contains(Role.ADMIN);
			if (!isAdmin) {
				logger.warn("User is not admin");
			}
			return isAdmin;
		}
	}

}
