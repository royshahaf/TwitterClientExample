package server;

public class Result {
	private final boolean status;
	private final Object payload;

	public Result(boolean status, Object payload) {
		super();
		this.status = status;
		this.payload = payload;
	}

	public boolean isStatus() {
		return status;
	}

	public Object getMessage() {
		return payload;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", payload=" + payload + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		Result other = (Result) obj;
		if (payload == null) {
			if (other.payload != null) {
				return false;
			}
		} else if (!payload.equals(other.payload)) {
			return false;
		}
		return status == other.status;
	}

}
