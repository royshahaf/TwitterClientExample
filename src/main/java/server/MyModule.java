package server;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import activities.ActivityService;
import activities.simple.SimpleActivityService;
import authentication.Authenticator;
import authentication.simple.SimpleAuthenticator;
import roles.Role;
import users.Topic;
import users.User;
import users.UserService;
import users.simple.SimpleUserService;

public class MyModule extends AbstractModule {

		private User adminUser = new User("adam", Collections.<Topic>emptySet(),
				new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR)));
		private User regularUser = new User("roy", Collections.<Topic>emptySet(),
				new HashSet<>(Arrays.asList(Role.REGULAR)));

		@Override
		protected void configure() {
			bind(Authenticator.class).to(SimpleAuthenticator.class).in(Scopes.SINGLETON);
			bind(ActivityService.class).to(SimpleActivityService.class).in(Scopes.SINGLETON);
			bind(UserService.class).toInstance(getUserService());
		}

		private UserService getUserService() {
			return new SimpleUserService(getMap());
		}

		private Map<String, User> getMap() {
			Map<String, User> testUsers = new HashMap<>();
			testUsers.put(adminUser.getName(), adminUser);
			testUsers.put(regularUser.getName(), regularUser);
			return testUsers;
		}
	}