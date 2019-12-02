package hr.fer.zemris.java.hw06.shell.parser;


/**
 * Represents Token which we get after the lexical analyze of the input for our shell input.
 * @author Patrik Okanovic
 *
 */
public class ShellToken {
	
	/**
	 * Type of the token.
	 */
	private ShellTokenType type;
	/**
	 * Value of the token
	 */
	private String value;
	/**
	 * Constructs the token with the given value and TokenType
	 * @param type
	 * @param value
	 */
	public ShellToken(ShellTokenType type, String value) {
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
	public ShellTokenType getType() {
		return type;
	}

}
