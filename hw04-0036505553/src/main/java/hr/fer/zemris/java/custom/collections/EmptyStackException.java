package hr.fer.zemris.java.custom.collections;

/**
 * This is a RuntimeException which is thrown when an user tries to pop an empty stack.
 * @author Patrik Okanovic
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor which forwards the message.
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
