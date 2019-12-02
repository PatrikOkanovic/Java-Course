package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An exception which extends RunTimeExceptions
 * @author Patrik Okanovic
 *
 */
public class LexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Creates the exception
	 */
	public LexerException() {
		super();
	}

}
