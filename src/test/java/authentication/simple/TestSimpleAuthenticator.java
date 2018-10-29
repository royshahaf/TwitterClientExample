package authentication.simple;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import activities.ActivityService;
import authentication.RolesFetcherStub;
import roles.Role;
import roles.RolesService;
import users.UserService;

public class TestSimpleAuthenticator {

	private static final RolesService rolesFetcher = new RolesFetcherStub();

	private static final SimpleAuthenticator authenticator = new SimpleAuthenticator((ActivityService) rolesFetcher,
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
					assertEquals(expected, authenticator.authenticate(ids[i], ids[j], ids[k]));
				}
			}
		}
	}

}
