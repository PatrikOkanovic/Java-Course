package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
/**
 * Used for implementing {@link Calculator}.
 * Represents binary operations, +,-,/,*
 * 
 * @author Patrik Okanovic
 *
 */
public class BinaryOpButton extends JButton{


	private static final long serialVersionUID = 1L;
	/**
	 * Operation of the button
	 */
	private DoubleBinaryOperator op;
	
	/**
	 * Constructor
	 * 
	 * @param text title of the button
	 * @param op {@link DoubleBinaryOperator}
	 */
	public BinaryOpButton(String text, DoubleBinaryOperator op) {
		super(text);
		this.op = op;
		
	}

	/**
	 * Returns the operator.
	 * 
	 * @return operator
	 */
	public DoubleBinaryOperator getOp() {
		return op;
	}

}
