package users.simple;

import java.util.HashMap;
import java.util.Map;

import users.TestAbstractUserService;
import users.User;
import users.UserService;

public class TestSimpleUserService extends TestAbstractUserService {

	@Override
	protected UserService getUserService() {
		return new SimpleUserService(getTestUsers());
	}

	private Map<String, User> getTestUsers() {
		Map<String, User> testUsers = new HashMap<>();
		testUsers.put(adminUser.getName(), adminUser);
		testUsers.put(regularUser.getName(), regularUser);
		return testUsers;
	}
}
