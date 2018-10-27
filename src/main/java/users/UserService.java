package users;

import roles.RolesService;
import server.Result;

public interface UserService extends RolesService {

	public User getUser(String id);

	public Result add(String requestedUsername, String topic);

	public Result delete(String requestedUsername, String topic);

	public Result edit(String requestedUsername, String previousTopic, String newTopic);

	public Result getTopics(String requestedUsername);

}
