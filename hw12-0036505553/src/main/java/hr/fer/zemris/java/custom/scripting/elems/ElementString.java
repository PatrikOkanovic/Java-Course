package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Specialization of Element used to store strings for the parser.
 * @author Patrik Okanovic
 *
 */
public class ElementString extends Element {

	/**
	 * The value
	 */
	private String value;
	/**
	 * Creates an instance of ElementString
	 * @param value
	 * @throws NullPointerException
	 */
	public ElementString(String value) {
		Objects.requireNonNull(value);
		this.value = value;
	}
	
	/**
	 * Return the value as String interpretation.
	 */
	@Override
	public String asText() {
		return value;
	}
	
	@Override
	public String toString() {
		String s = "";
		char[] data = value.toCharArray();
		for(int i = 0, length = data.length; i < length; i++) {
			if(data[i] == '\\') {
				s+="\\\\";
			} else if(i != 0 && i != length-1 && data[i] == '\"') {
				s += "\\\"";
			} else {
				s += data[i];
			}
		}
		return s.substring(1, s.length()-1);
	}
	
}
