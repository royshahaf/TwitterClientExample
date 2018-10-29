package access.simple;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import access.RolesFetcherStub;
import access.simple.SimpleAccessChecker;
import activities.ActivityService;
import roles.Role;
import roles.RolesService;
import users.UserService;

public class TestSimpleAccessChecker {

	private static final RolesService rolesFetcher = new RolesFetcherStub();

	private static final SimpleAccessChecker accessChecker = new SimpleAccessChecker((ActivityService) rolesFetcher,
			(UserService) rolesFetcher);

	String[] ids = new String[] { "both", "adminOnly", "regularOnly", "empty", "null" };

	@Test
	public void verifyOriginalAuthenticationWorks() {
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < ids.length; j++) {
				boolean expected;
				for (int k = 0; k < ids.length; k++) {
					Set<Role> requestingRoles = rolesFetcher.getRoles(ids[j]);
					Set<Role> activityRoles = rolesFetcher.getRoles(ids[i]);
					if (requestingRoles == null || activityRoles == null) {
						expected = false;
					} else if (ids[k].equals(ids[j])) {
						expected = !Collections.disjoint(activityRoles, requestingRoles);
					} else {
						expected = requestingRoles.contains(Role.ADMIN);
					}
					assertEquals(expected, accessChecker.checkAccess(ids[i], ids[j], ids[k]));
				}
			}
		}
	}

}
