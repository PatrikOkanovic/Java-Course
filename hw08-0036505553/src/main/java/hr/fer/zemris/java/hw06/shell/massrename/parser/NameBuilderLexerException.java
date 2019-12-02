package hr.fer.zemris.java.hw06.shell.massrename.parser;
/**
 * An exception which extends RunTimeExceptions
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public NameBuilderLexerException(String message) {
		super(message);
	}
	
	/**
	 * Creates the exception
	 */
	public NameBuilderLexerException() {
		super();
	}
}
