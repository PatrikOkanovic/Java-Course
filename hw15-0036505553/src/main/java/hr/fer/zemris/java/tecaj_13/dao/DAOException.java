package hr.fer.zemris.java.tecaj_13.dao;
/**
 * A {@link RuntimeException}
 * 
 * @author Patrik Okanovic
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the exception.
	 * 
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor of the exception.
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
}