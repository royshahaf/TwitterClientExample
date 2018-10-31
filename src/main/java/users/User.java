package users;

import java.util.HashSet;
import java.util.Set;

import roles.Role;

public class User {
	private String name;
	private Set<Topic> topics;
	private Set<Role> roles;

	public User() {
		super();
	}

	public User(String name, Set<Topic> topics, Set<Role> roles) {
		super();
		this.name = name;
		this.topics = topics;
		this.roles = roles;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = new HashSet<>(topics);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = new HashSet<>(roles);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (roles == null) {
			if (other.roles != null) {
				return false;
			}
		} else if (!roles.equals(other.roles)) {
			return false;
		}
		if (topics == null) {
			if (other.topics != null) {
				return false;
			}
		} else {
			return topics.equals(other.topics);
		}
		return true;
	}
	
	

}
