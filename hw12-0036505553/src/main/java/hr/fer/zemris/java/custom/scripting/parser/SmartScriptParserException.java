package hr.fer.zemris.java.custom.scripting.parser;
/**
 * An exception which extends RunTimeExceptions
 * @author Patrik Okanovic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	/**
	 * Creates the exception
	 */
	public SmartScriptParserException() {
		
	}
}
