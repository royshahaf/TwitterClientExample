package users;

import roles.RolesService;
import server.Result;

public interface UserService extends RolesService {

	public User getUser(String id);

	public Result addTopic(String requestedUsername, String topic);

	public Result deleteTopic(String requestedUsername, String topic);

	public Result editTopics(String requestedUsername, String previousTopic, String newTopic);

	public Result getTopics(String requestedUsername);

}
