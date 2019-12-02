package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Specialization of Element used to store operators for the parser.
 * @author Patrik Okanovic
 *
 */
public class ElementOperator extends Element{

	/**
	 * The symbol
	 */
	private String symbol;
	/**
	 * Creates an instance of ElementOperator
	 * @param value
	 * @throws NullPointerException
	 */
	public ElementOperator(String symbol) {
		Objects.requireNonNull(symbol);
		this.symbol = symbol;
	}
	
	/**
	 * Return the name of the ElementVariable.
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
}
