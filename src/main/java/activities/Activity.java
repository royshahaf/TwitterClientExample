package activities;

import java.util.ArrayList;
import java.util.List;

import roles.Role;

public class Activity {
	private final String name;
	private final List<Role> roles;

	public Activity(String name, List<Role> roles) {
		super();
		this.name = name;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public List<Role> getRoles() {
		return new ArrayList<>(roles);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
}
