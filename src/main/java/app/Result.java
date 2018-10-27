package app;

public class Result {
	private final boolean status;
	private final String message;
	
	public Result(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
}
