package activities.simple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import activities.Activity;
import activities.ActivityService;
import roles.Role;

public class SimpleActivityService implements ActivityService {

	@Override
	public Activity getActivity(String id) {
		return new Activity(id, new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR)));
	}

	@Override
	public Set<Role> getRoles(String id) {
		return new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR));
	}

}
