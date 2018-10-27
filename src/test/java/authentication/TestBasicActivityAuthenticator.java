package authentication;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import entities.EntityKind;
import entities.Role;
import roles.RolesFetcher;

public class TestBasicActivityAuthenticator {

	private static final RolesFetcher rolesFetcher = new RolesFetcherStub();

	private static final BasicActivityAuthenticator authenticator = new BasicActivityAuthenticator(rolesFetcher);

	String[] ids = new String[] { "both", "adminOnly", "regularOnly", "empty", "null" };

	@Test
	public void verifyOriginalAuthenticationWorks() {
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < ids.length; j++) {
				boolean expected;
				for (int k = 0; k < ids.length; k++) {
					List<Role> requestingRoles = rolesFetcher.fetchRoles(EntityKind.USER, ids[j]);
					List<Role> activityRoles = rolesFetcher.fetchRoles(EntityKind.ACTIVITY, ids[i]);
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
