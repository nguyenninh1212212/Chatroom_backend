package app.exception;

public class NotExisting extends RuntimeException {
	public NotExisting(String message) {
		super(message);
	}
}
