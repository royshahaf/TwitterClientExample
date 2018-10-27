package activities;

import roles.RolesService;

public interface ActivityService extends RolesService {

	Activity getActivity(String id);

}
