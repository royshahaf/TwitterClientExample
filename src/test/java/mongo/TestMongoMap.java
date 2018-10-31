package mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import users.User;

public class TestMongoMap {

	private MongoMap map = new MongoMap("test", "test");

	@Before
	public void before() {
		map.clear();
		assertEquals(0, map.size());
	}

	@Test
	public void testMapApi() {
		assertTrue(map.isEmpty());
		User user1 = new User("testName", Collections.emptySet(), Collections.emptySet());
		Map<String, User> users = new HashMap<>();
		users.put("testName", user1);
		map.put("testName", user1);
		assertEquals(1, map.size());
		assertEquals(user1, map.get("testName"));
		assertTrue(map.containsKey("testName"));
		assertTrue(map.containsValue(user1));
		assertFalse(map.isEmpty());
		assertTrue(users.values().containsAll(map.values()));
		assertTrue(map.values().containsAll(users.values()));
		assertEquals(users.keySet(), map.keySet());
		assertEquals(user1, map.remove("testName"));
		User user2 = new User("testName2", Collections.emptySet(), Collections.emptySet());
		users.put("testName2", user2);
		assertTrue(map.isEmpty());
		map.putAll(users);
		assertFalse(map.isEmpty());
		assertTrue(users.values().containsAll(map.values()));
		assertTrue(map.values().containsAll(users.values()));
		assertEquals(users.keySet(), map.keySet());
		map.clear();
		assertEquals(0, map.size());
	}
}
