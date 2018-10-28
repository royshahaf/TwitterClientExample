package authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import roles.Role;
import roles.RolesService;

public class RolesFetcherStub implements RolesService {

	Map<String, Set<Role>> roles;

	public RolesFetcherStub() {
		populateTestMap();
	}

	private void populateTestMap() {
		roles = new HashMap<>();
		roles.put("both", new HashSet<>(Arrays.asList(new Role[] { Role.ADMIN, Role.REGULAR })));
		roles.put("regularOnly", new HashSet<>(Arrays.asList(new Role[] { Role.REGULAR })));
		roles.put("adminOnly", new HashSet<>(Arrays.asList(new Role[] { Role.ADMIN })));
		roles.put("empty", new HashSet<>(Arrays.asList(new Role[] {})));
	}

	@Override
	public Set<Role> getRoles(String id) {
		return roles.get(id);
	}

}
