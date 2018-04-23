package hr.altima.test;

/**
 * Custom exception used in Altima test application
 */
public class AltimaException extends RuntimeException {
	
	public AltimaException() {	}
	
	public AltimaException(String message) {
		super(message);
	}
}
