package activities.simple;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import activities.Activity;
import activities.simple.SimpleActivityService;
import roles.Role;

public class TestSimpleActivityService {

	@Test
	public void test() {
		String id = "test";
		List<Role> expectedRoles = Arrays.asList(new Role[] { Role.ADMIN, Role.REGULAR});
		Activity expected = new Activity(id, expectedRoles);
		assertEquals(expected, new SimpleActivityService().getActivity(id));
	}

}
