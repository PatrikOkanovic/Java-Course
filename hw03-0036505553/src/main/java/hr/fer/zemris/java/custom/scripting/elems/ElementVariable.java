package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Extends Node, used for implementing the Parser and saving variables.
 * @author Patrik OKanovic
 *
 */
public class ElementVariable extends Element{

	/**
	 * The name
	 */
	private String name;
	/**
	 * Creates an instance of ElementString
	 * @param value
	 * @throws NullPointerException
	 */
	public ElementVariable(String name) {
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
