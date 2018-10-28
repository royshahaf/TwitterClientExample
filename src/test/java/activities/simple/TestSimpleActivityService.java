package activities.simple;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import activities.Activity;
import roles.Role;

public class TestSimpleActivityService {

	@Test
	public void test() {
		String id = "test";
		Set<Role> expectedRoles = new HashSet<>(Arrays.asList(new Role[] { Role.ADMIN, Role.REGULAR}));
		Activity expected = new Activity(id, expectedRoles);
		assertEquals(expected, new SimpleActivityService().getActivity(id));
	}

}
