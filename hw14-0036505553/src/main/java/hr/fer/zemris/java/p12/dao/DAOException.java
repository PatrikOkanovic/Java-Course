package hr.fer.zemris.java.p12.dao;
/**
 * A {@link RuntimeException}
 * 
 * @author Patrik Okanovic
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException() {
	}

	/**
	 * Constructor of the exception
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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

	/**
	 * Constructor of the exception.
	 * 
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}