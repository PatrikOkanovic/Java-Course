package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents Token which we get after the lexical analyze of the input for our programming language.
 * @author Patrik Okanovic
 *
 */
public class Token {

	/**
	 * Type of the token.
	 */
	private TokenType type;
	/**
	 * Value of the token
	 */
	private Object value;
	/**
	 * Constructs the token with the given value and TokenType
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Gets the value of the token.
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * Gets the TokenType of the token.
	 * @return
	 */
	public TokenType getType() {
		return type;
	}

}
