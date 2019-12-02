package hr.fer.zemris.java.hw05.db;

/**
 * An exception which extends RunTimeExceptions
 * @author Patrik Okanovic
 *
 */
public class QueryLexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public QueryLexerException(String message) {
		super(message);
	}
	
	/**
	 * Creates the exception
	 */
	public QueryLexerException() {
		super();
	}

}
