package server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import activities.simple.SimpleActivityService;
import authentication.Authenticator;
import authentication.simple.SimpleAuthenticator;
import roles.Role;
import users.Topic;
import users.User;
import users.UserService;

public class TestServerApi {

	private UserService userService;
	private Authenticator authenticator;
	private ServerApi api;
	private User adminUser;
	private User regularUser;

	@Before
	public void init() {
		adminUser = new User("adam", Collections.<Topic>emptySet(),
				new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR)));
		regularUser = new User("roy", Collections.<Topic>emptySet(), new HashSet<>(Arrays.asList(Role.REGULAR)));
		userService = new UserServiceStub(getTestUsers());
		authenticator = new SimpleAuthenticator(new SimpleActivityService(), userService);
		api = new ServerApi(authenticator, userService);
	}

	private Map<String, User> getTestUsers() {
		Map<String, User> testUsers = new HashMap<>();
		testUsers.put(adminUser.getName(), adminUser);
		testUsers.put(regularUser.getName(), regularUser);
		return testUsers;
	}

	@Test
	public void testAddToSelf() {
		assertTrue(api.add(adminUser.getName(), adminUser.getName(), "topic").isStatus());
		assertTrue(api.add(regularUser.getName(), regularUser.getName(), "topic").isStatus());
	}
	
	@Test
	public void testAddToOther() {
		assertTrue(api.add(adminUser.getName(), regularUser.getName(), "topic").isStatus());
		assertFalse(api.add(regularUser.getName(), adminUser.getName(), "topic").isStatus());
	}
	
	@Test
	public void testAddAlreadyExisting() {
		assertTrue(api.add(adminUser.getName(), regularUser.getName(), "topic").isStatus());
		assertFalse(api.add(adminUser.getName(), regularUser.getName(), "topic").isStatus());
	}
	
	@Test
	public void testDeleteFromSelf() {
		testAddToSelf();
		assertTrue(api.delete(adminUser.getName(), adminUser.getName(), "topic").isStatus());
		assertTrue(api.delete(regularUser.getName(), regularUser.getName(), "topic").isStatus());
	}
	
	@Test
	public void testDeleteFromOther() {
		testAddToSelf();
		assertTrue(api.delete(adminUser.getName(), regularUser.getName(), "topic").isStatus());
		assertFalse(api.delete(regularUser.getName(), adminUser.getName(), "topic").isStatus());
	}
	
	@Test
	public void testDeleteNonExisting() {
		assertFalse(api.delete(adminUser.getName(), adminUser.getName(), "topic").isStatus());
		assertFalse(api.delete(regularUser.getName(), regularUser.getName(), "topic").isStatus());
		assertFalse(api.delete(adminUser.getName(), adminUser.getName(), "topic").isStatus());
		assertFalse(api.delete(regularUser.getName(), regularUser.getName(), "topic").isStatus());
	}
	
	@Test
	public void testDeleteAfterDelete() {
		testDeleteFromSelf();
		testDeleteNonExisting();		
	}
	
	
	
	

}
