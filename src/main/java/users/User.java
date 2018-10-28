package users;

import java.util.HashSet;
import java.util.Set;

import roles.Role;

public class User {
	private final String name;
	private final Set<Topic> topics;
	private final Set<Role> roles;
	
	public User(String name, Set<Topic> topics, Set<Role> roles) {
		super();
		this.name = name;
		this.topics = topics;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public Set<Topic> getTopics() {
		return new HashSet<>(topics);
	}

	public Set<Role> getRoles() {
		return new HashSet<>(roles);
	}
	
	
}
