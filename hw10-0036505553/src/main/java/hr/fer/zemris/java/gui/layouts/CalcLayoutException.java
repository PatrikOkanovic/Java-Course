package hr.fer.zemris.java.gui.layouts;
/**
 * Class extends {@link RuntimeException}. Exception is used while
 * implementing {@link CalcLayout}.
 * 
 * @author Patrik Okanovic
 *
 */
public class CalcLayoutException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with the message which represents the cause of the exception.
	 * 
	 * @param message forwards to {@link RuntimeException}
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	
	/**
	 * Default constructor of the class.
	 */
	public CalcLayoutException() {
		super();
	}

}
