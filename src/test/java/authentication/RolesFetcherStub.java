package authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.EntityKind;
import entities.Role;
import roles.RolesFetcher;

public class RolesFetcherStub implements RolesFetcher {

	Map<EntityKind, Map<String, List<Role>>> roles;

	public RolesFetcherStub() {
		populateTestMap();
	}

	private void populateTestMap() {
		roles = new HashMap<>();
		roles.put(EntityKind.ACTIVITY, new HashMap<String, List<Role>>());
		roles.get(EntityKind.ACTIVITY).put("both", Arrays.asList(new Role[] { Role.ADMIN, Role.REGULAR }));
		roles.get(EntityKind.ACTIVITY).put("regularOnly", Arrays.asList(new Role[] { Role.REGULAR }));
		roles.get(EntityKind.ACTIVITY).put("adminOnly", Arrays.asList(new Role[] { Role.ADMIN }));
		roles.get(EntityKind.ACTIVITY).put("empty", Arrays.asList(new Role[] {}));
		roles.put(EntityKind.USER, new HashMap<String, List<Role>>());
		roles.get(EntityKind.USER).put("both", Arrays.asList(new Role[] { Role.ADMIN, Role.REGULAR }));
		roles.get(EntityKind.USER).put("regularOnly", Arrays.asList(new Role[] { Role.REGULAR }));
		roles.get(EntityKind.USER).put("adminOnly", Arrays.asList(new Role[] { Role.ADMIN }));
		roles.get(EntityKind.USER).put("empty", Arrays.asList(new Role[] {}));

	}

	@Override
	public List<Role> fetchRoles(EntityKind entityKind, String id) {
		return roles.get(entityKind).get(id);
	}

}
