package authentication.simple;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import authentication.RolesFetcherStub;
import authentication.simple.SimpleAuthenticator;
import roles.Role;
import roles.RolesService;

public class TestSimpleAuthenticator {

	private static final RolesService rolesFetcher = new RolesFetcherStub();

	private static final SimpleAuthenticator authenticator = new SimpleAuthenticator(rolesFetcher, rolesFetcher);

	String[] ids = new String[] { "both", "adminOnly", "regularOnly", "empty", "null" };

	@Test
	public void verifyOriginalAuthenticationWorks() {
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < ids.length; j++) {
				boolean expected;
				for (int k = 0; k < ids.length; k++) {
					List<Role> requestingRoles = rolesFetcher.getRoles(ids[j]);
					List<Role> activityRoles = rolesFetcher.getRoles(ids[i]);
					if (requestingRoles == null || activityRoles == null) {
						expected = false;
					} else if (ids[k].equals(ids[j])) {
						expected =  !Collections.disjoint(activityRoles, requestingRoles);
					} else {
						expected =  requestingRoles.contains(Role.ADMIN);
					}
					assertEquals(expected, authenticator.authenticate(ids[i], ids[j], ids[k]));
				}
			}
		}
	}

}
