package hr.fer.zemris.java.hw06.shell.massrename.parser;

/**
 * Represents Token which we get after the lexical analyze of the input for our massrename command.
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderToken {

	/**
	 * Type of the token.
	 */
	private NameBuilderTokenType type;
	/**
	 * Value of the token
	 */
	private String value;
	/**
	 * Constructs the token with the given value and TokenType
	 * @param type
	 * @param value
	 */
	public NameBuilderToken(NameBuilderTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Gets the value of the token.
	 * @return
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Gets the TokenType of the token.
	 * @return
	 */
	public NameBuilderTokenType getType() {
		return type;
	}
}
