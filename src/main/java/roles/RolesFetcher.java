package roles;

import java.util.List;

import entities.EntityKind;
import entities.Role;

public interface RolesFetcher {
	public List<Role> fetchRoles(EntityKind entityKind, String id);
}
