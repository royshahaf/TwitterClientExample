package users;

import java.util.ArrayList;
import java.util.List;

import roles.Role;

public class User {
	private final String name;
	private final List<Topic> topics;
	private final List<Role> roles;
	
	public User(String name, List<Topic> topics, List<Role> roles) {
		super();
		this.name = name;
		this.topics = topics;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public List<Topic> getTopics() {
		return new ArrayList<>(topics);
	}

	public List<Role> getRoles() {
		return new ArrayList<>(roles);
	}
	
	
}
