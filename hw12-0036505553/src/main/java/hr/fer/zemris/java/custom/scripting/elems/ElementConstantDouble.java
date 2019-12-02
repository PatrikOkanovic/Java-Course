package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Specialization of Element used to store double.
 * @author Patrik Okanovic
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * The value
	 */
	private double value;
	/**
	 * Creates an instance of ElementConstantDouble
	 * @param value
	 */
	public ElementConstantDouble(double value) {
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
	public double getValue() {
		return this.value;
	}
}
