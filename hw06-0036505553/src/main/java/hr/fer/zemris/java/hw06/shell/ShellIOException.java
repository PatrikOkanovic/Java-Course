package hr.fer.zemris.java.hw06.shell;

/**
 * An exception which extends RunTimeExceptions
 * @author Patrik Okanovic
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public ShellIOException(String message) {
		super(message);
	}
	/**
	 * Creates the exception
	 */
	public ShellIOException() {
		
	}
}
