package users;

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

import roles.Role;
import server.Result;
import users.Topic;
import users.User;
import users.UserService;

public abstract class TestAbstractUserService {

	private UserService userService;
	protected User adminUser;
	protected User regularUser;

	@Before
	public void init() {
		adminUser = new User("adam", Collections.<Topic>emptySet(),
				new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR)));
		regularUser = new User("roy", Collections.<Topic>emptySet(), new HashSet<>(Arrays.asList(Role.REGULAR)));
		userService = getUserService();
	}
	
	protected abstract UserService getUserService();

	@Test
	public void testAddToSelf() {
		assertTrue(userService.addTopic(adminUser.getName(), "topic").isStatus());
		assertTrue(userService.addTopic(regularUser.getName(), "topic").isStatus());
	}

	@Test
	public void testAddAlreadyExisting() {
		assertTrue(userService.addTopic(adminUser.getName(), "topic").isStatus());
		assertFalse(userService.addTopic(adminUser.getName(), "topic").isStatus());
	}

	@Test
	public void testDeleteFromSelf() {
		testAddToSelf();
		assertTrue(userService.deleteTopic(adminUser.getName(), "topic").isStatus());
		assertTrue(userService.deleteTopic(regularUser.getName(), "topic").isStatus());
	}

	@Test
	public void testDeleteNonExisting() {
		assertFalse(userService.deleteTopic(adminUser.getName(), "topic").isStatus());
		assertFalse(userService.deleteTopic(regularUser.getName(), "topic").isStatus());
	}

	@Test
	public void testDeleteAfterDelete() {
		testDeleteFromSelf();
		testDeleteNonExisting();
	}

	@Test
	public void testEdit() {
		testAddToSelf();
		assertTrue(userService.editTopics(adminUser.getName(), "topic", "topic2").isStatus());
		assertTrue(userService.editTopics(regularUser.getName(), "topic", "topic2").isStatus());
		assertFalse(userService.editTopics(adminUser.getName(), "topic", "topic3").isStatus());
		assertFalse(userService.editTopics(adminUser.getName(), "non-existing", "topic2").isStatus());
	}

	@Test
	public void testView() {
		userService.addTopic(adminUser.getName(), "topic");
		userService.addTopic(regularUser.getName(), "topic2");
		assertEquals(getExpected("topic"), userService.getTopics(adminUser.getName()));
		assertEquals(getExpected("topic2"), userService.getTopics(regularUser.getName()));
		assertFalse(userService.getTopics("non-existing").isStatus());
	}

	private Result getExpected(String string) {
		return new Result(true, new HashSet<>(Collections.singleton(new Topic(string))).toString());
	}

}
