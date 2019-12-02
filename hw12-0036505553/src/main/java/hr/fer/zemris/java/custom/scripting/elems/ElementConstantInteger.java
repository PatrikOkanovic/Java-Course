package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Specialization of Element used to store integers.
 * @author Patrik Okanovic
 *
 */
public class ElementConstantInteger extends Element{
	/**
	 * The value
	 */
	private int value;
	/**
	 * Creates an instance of ElementConstantInteger
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Return the value as String interpretation.
	 */
	@Override
	public String asText() {
		return value+"";
	}
	
	@Override
	public String toString() {
		return value+"";
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}

}
