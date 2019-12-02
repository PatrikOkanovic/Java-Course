package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * Enumeration used to seperate types of tokens.
 * @author Patrik Okanovic
 *
 */
public enum TokenType {

	EOF, TEXT, TAG, TAGNAME, VARIABLE, FUNCTION, OPERATOR,
	STRING, DOUBLE, INTEGER;
}
