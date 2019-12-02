package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
/**
 * Used for implementing {@link Calculator}.
 * Represents unary operations, such as sin,cos,tan..
 * Can be inverted, then it becomes arcsin,arccos..
 * 
 * @author Patrik Okanovic
 *
 */
public class UnaryOpButton extends JButton{

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
	private DoubleUnaryOperator op2;
	/**
	 * Operation while in second mode
	 */
	private DoubleUnaryOperator op1;
	/**
	 * Constructor of the class
	 * 
	 * @param text initial title
	 * @param otherText inverted value
	 * @param op1  initial operation
	 * @param op2 inverted operation
	 */
	public UnaryOpButton(String text,String otherText, DoubleUnaryOperator op1,DoubleUnaryOperator op2) {
		super(text);
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
	public DoubleUnaryOperator getOp() {
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
		
		DoubleUnaryOperator tmp2 = op1;
		op1 = op2;
		op2 = tmp2;
	}
	
}
