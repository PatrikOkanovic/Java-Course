package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Special case of {@link BinaryOpButton} because it
 * can be inverted. Represents a button which does the power operation, and inverted
 * x^n, x^(1/n)
 * 
 * @author Patrik Okanovic
 *
 */
public class PowButton extends BinaryOpButton{

private static final long serialVersionUID = 1L;
	/**
	 * Title of the button
	 */
	private String text;
	
	/**
	 * Title in the inverted mode
	 */
	private String otherText;
	
	/**
	 * Operation while in first mode
	 */
	private DoubleBinaryOperator op2;
	
	/**
	 * Operation while in second mode
	 */
	private DoubleBinaryOperator op1;
	
	/**
	 * Constructor of the class
	 * 
	* @param text initial title
	 * @param otherText inverted value
	 * @param op1  initial operation
	 * @param op2 inverted operation
	 */
	public PowButton(String text,String otherText, DoubleBinaryOperator op1,DoubleBinaryOperator op2) {
		super(text,op1);
		this.text = text;
		this.op1 = op1;
		this.otherText = otherText;
		this.op2 = op2;
	}

	/**
	 * Returns the operation.
	 * 
	 * @return
	 */
	public DoubleBinaryOperator getOp() {
		return op1;
	}
	
	/**
	 * Switch from one mode to another.
	 */
	public void reverse() {
		String tmp = text;
		text = otherText;
		otherText = tmp;
		setText(text);
		
		DoubleBinaryOperator tmp2 = op1;
		op1 = op2;
		op2 = tmp2;
	}
}
