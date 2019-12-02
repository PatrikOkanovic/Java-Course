package hr.fer.zemris.java.hw06.shell.parser;

/**
 * An exception which extends RunTimeExceptions
 * @author Patrik Okanovic
 *
 */
public class ShellParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public ShellParserException(String message) {
		super(message);
	}
	/**
	 * Creates the exception
	 */
	public ShellParserException() {
		
	}
}
