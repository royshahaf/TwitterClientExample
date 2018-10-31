package server;

import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import access.AccessChecker;
import access.simple.SimpleAccessChecker;
import activities.ActivityService;
import activities.simple.SimpleActivityService;
import users.User;
import users.UserService;
import users.simple.SimpleUserService;

public class MyModule extends AbstractModule {

	private final Map<String, User> users;

	public MyModule(Map<String, User> users) {
		this.users = users;
	}

	@Override
	protected void configure() {
		bind(AccessChecker.class).to(SimpleAccessChecker.class).in(Scopes.SINGLETON);
		bind(ActivityService.class).to(SimpleActivityService.class).in(Scopes.SINGLETON);
		bind(UserService.class).toInstance(getUserService());
	}

	private UserService getUserService() {
		return new SimpleUserService(getMap());
	}

	private Map<String, User> getMap() {
		return users;
	}
}