package access;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import activities.Activity;
import activities.ActivityService;
import roles.Role;
import server.Result;
import users.User;
import users.UserService;

public class RolesFetcherStub implements UserService, ActivityService {

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

	@Override
	public Activity getActivity(String id) {
		return null;
	}

	@Override
	public User getUser(String id) {
		return null;
	}

	@Override
	public Result addTopic(String requestedUsername, String topic) {
		return null;
	}

	@Override
	public Result deleteTopic(String requestedUsername, String topic) {
		return null;
	}

	@Override
	public Result editTopics(String requestedUsername, String previousTopic, String newTopic) {
		return null;
	}

	@Override
	public Result getTopics(String requestedUsername) {
		return null;
	}

}
