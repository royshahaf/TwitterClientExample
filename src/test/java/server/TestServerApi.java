package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import access.AccessChecker;
import access.simple.SimpleAccessChecker;
import activities.simple.SimpleActivityService;
import roles.Role;
import users.Topic;
import users.User;
import users.UserService;
import users.simple.SimpleUserService;

public class TestServerApi {

	private UserService userService;
	private AccessChecker accessChecker;
	private ServerApi api;
	private User adminUser;
	private User regularUser;

	@Before
	public void init() {
		adminUser = new User("adam", Collections.<Topic>emptySet(),
				new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR)));
		regularUser = new User("roy", Collections.<Topic>emptySet(), new HashSet<>(Arrays.asList(Role.REGULAR)));
		userService = new SimpleUserService(getTestUsers());
		accessChecker = new SimpleAccessChecker(new SimpleActivityService(), userService);
		api = new ServerApi(accessChecker, userService);
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

	@Test
	public void testEdit() {
		testAddToSelf();
		assertTrue(api.edit(adminUser.getName(), adminUser.getName(), "topic", "topic2").isStatus());
		assertTrue(api.edit(regularUser.getName(), regularUser.getName(), "topic", "topic2").isStatus());
		assertTrue(api.edit(adminUser.getName(), regularUser.getName(), "topic2", "topic3").isStatus());
		assertFalse(api.edit(regularUser.getName(), adminUser.getName(), "topic2", "topic3").isStatus());
		assertFalse(api.edit(adminUser.getName(), adminUser.getName(), "topic", "topic3").isStatus());
		assertFalse(api.edit(adminUser.getName(), adminUser.getName(), "non-existing", "topic3").isStatus());
	}

	@Test
	public void testView() {
		api.add(adminUser.getName(), adminUser.getName(), "topic");
		api.add(regularUser.getName(), regularUser.getName(), "topic2");
		assertEquals(getExpected("topic"), api.view(adminUser.getName(), adminUser.getName()));
		assertEquals(getExpected("topic2"), api.view(regularUser.getName(), regularUser.getName()));
		assertEquals(getExpected("topic2"), api.view(adminUser.getName(), regularUser.getName()));
		assertEquals(getExpectedFailure(), api.view(regularUser.getName(), adminUser.getName()));
		assertFalse(api.view("non-existing", "non-existing").isStatus());
		assertFalse(api.view(adminUser.getName(), "non-existing").isStatus());
	}

	private Result getExpected(String string) {
		return new Result(true, new HashSet<>(Collections.singleton(new Topic(string))).toString());
	}
	
	private Result getExpectedFailure() {
		return new Result(false, ServerApi.ACCESS_DENIED);
	}

}
