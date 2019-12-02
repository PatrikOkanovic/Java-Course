package hr.fer.zemris.java.hw06.shell.massrename.parser;
/**
 * An exception which extends RunTimeExceptions
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public NameBuilderParserException(String message) {
		super(message);
	}
	/**
	 * Creates the exception
	 */
	public NameBuilderParserException() {
		
	}
}
