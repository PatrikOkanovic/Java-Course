package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Specialization of Element used to store functions for the parser.
 * @author Patrik Okanovic
 *
 */
public class ElementFunction extends Element {

	/**
	 * The name
	 */
	private String name;
	/**
	 * Creates an instance of ElementFunction
	 * @param value
	 * @throws NullPointerException
	 */
	public ElementFunction(String name) {
		Objects.requireNonNull(name);
		this.name = name;
	}
	
	/**
	 * Return the name of the ElementVariable.
	 */
	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
