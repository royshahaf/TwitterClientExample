package users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.jetty.client.HttpClient;
import org.junit.Before;
import org.junit.Test;

import roles.Role;
import server.Result;

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
		assertFalse(userService.addTopic("non-existing", "topic").isStatus());
		assertTrue(userService.addTopic(adminUser.getName(), "topic").isStatus());
		assertTrue(userService.addTopic(regularUser.getName(), "topic").isStatus());
	}

	@Test
	public void testGetUser() {
		assertEquals(adminUser, userService.getUser(adminUser.getName()));
	}

	@Test
	public void testAddAlreadyExisting() {
		assertTrue(userService.addTopic(adminUser.getName(), "topic").isStatus());
		assertFalse(userService.addTopic(adminUser.getName(), "topic").isStatus());
	}

	@Test
	public void testDeleteFromSelf() {
		testAddToSelf();
		assertFalse(userService.deleteTopic("non-existing", "topic").isStatus());
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
		assertFalse(userService.editTopics("non-existing", "topic", "topic2").isStatus());
		assertTrue(userService.editTopics(adminUser.getName(), "topic", "topic2").isStatus());
		assertTrue(userService.editTopics(regularUser.getName(), "topic", "topic2").isStatus());
		assertFalse(userService.editTopics(adminUser.getName(), "topic", "topic3").isStatus());
		assertFalse(userService.editTopics(adminUser.getName(), "non-existing", "topic2").isStatus());
	}

	@Test
	public void testView() {
		userService.addTopic(adminUser.getName(), "topic");
		userService.addTopic(regularUser.getName(), "topic2");
		assertEquals(getExpected("topic").toString(), userService.getTopics(adminUser.getName()).toString());
		assertEquals(getExpected("topic2").toString(), userService.getTopics(regularUser.getName()).toString());
		assertFalse(userService.getTopics("non-existing").isStatus());
	}

	private Result getExpected(String string) {
		return new Result(true, new HashSet<>(Collections.singleton(new Topic(string))).toString());
	}

}
