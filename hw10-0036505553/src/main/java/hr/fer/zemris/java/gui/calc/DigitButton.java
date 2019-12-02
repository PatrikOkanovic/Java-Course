package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
/**
 * Used for implementing {@link Calculator}.
 * Represents the digits of the calculator.
 * 
 * @author Patrik Okanovic
 *
 */
public class DigitButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	/**
	 * The value
	 */
	private String value;

	/**
	 * The constructor
	 * 
	 * @param value
	 */
	public DigitButton(String value) {
		this.setText(value);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/**
	 * Returns the value
	 * @return integer value
	 */
	public int getValue() {
		return Integer.parseInt(value);
	}
}
