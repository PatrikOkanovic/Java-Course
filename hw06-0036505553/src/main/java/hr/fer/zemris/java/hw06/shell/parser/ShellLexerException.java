package hr.fer.zemris.java.hw06.shell.parser;
/**
 * An exception which extends RunTimeExceptions
 * @author Patrik Okanovic
 *
 */
public class ShellLexerException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public ShellLexerException(String message) {
		super(message);
	}
	
	/**
	 * Creates the exception
	 */
	public ShellLexerException() {
		super();
	}
}
