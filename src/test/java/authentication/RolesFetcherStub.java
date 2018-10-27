package authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roles.Role;
import roles.RolesService;

public class RolesFetcherStub implements RolesService {

	Map<String, List<Role>> roles;

	public RolesFetcherStub() {
		populateTestMap();
	}

	private void populateTestMap() {
		roles = new HashMap<>();
		roles.put("both", Arrays.asList(new Role[] { Role.ADMIN, Role.REGULAR }));
		roles.put("regularOnly", Arrays.asList(new Role[] { Role.REGULAR }));
		roles.put("adminOnly", Arrays.asList(new Role[] { Role.ADMIN }));
		roles.put("empty", Arrays.asList(new Role[] {}));
	}

	@Override
	public List<Role> getRoles(String id) {
		return roles.get(id);
	}

}
