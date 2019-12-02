package hr.fer.zemris.java.hw06.shell.massrename.parser;

/**
 * Enumeration used to seperate types of tokens.
 * @author Patrik Okanovic
 *
 */
public enum NameBuilderTokenType {

	/**
	 * Represents a string
	 */
	TEXT, 
	
	/**
	 * The end of file
	 */
	EOF,
	
	/**
	 * The comma
	 */
	COMMA,
	/**
	 * Tag which is ${ for opening, and } for closing
	 */
	TAG,
	/**
	 * The closing tag
	 */
	ENDTAG,
	/**
	 * Represents the number inside of tags
	 */
	NUMBER
}
