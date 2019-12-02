package hr.fer.zemris.java.hw05.db;


/**
 * Represents Token which we get after the lexical analyze of the input for our query input.
 * @author Patrik Okanovic
 *
 */
public class QueryToken {
	
	/**
	 * Type of the token.
	 */
	private QueryTokenType type;
	/**
	 * Value of the token
	 */
	private String value;
	/**
	 * Constructs the token with the given value and TokenType
	 * @param type
	 * @param value
	 */
	public QueryToken(QueryTokenType type, String value) {
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
	public QueryTokenType getType() {
		return type;
	}

}
