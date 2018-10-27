package activities.simple;

import java.util.Arrays;
import java.util.List;

import activities.Activity;
import activities.ActivityService;
import roles.Role;

public class SimpleActivityService implements ActivityService {

	@Override
	public Activity getActivity(String id) {
		return new Activity(id, Arrays.asList(Role.ADMIN, Role.REGULAR));
	}

	@Override
	public List<Role> getRoles(String id) {
		return Arrays.asList(Role.ADMIN, Role.REGULAR);
	}

}
