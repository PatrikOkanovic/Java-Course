package hr.fer.zemris.java.hw06.shell.massrename.parser;

import java.util.Objects;


import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
/**
 * ShellLexer used for implementing the {@link MassrenameCommand}
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderLexer {

	/**
	 * State of the Lexer. Initially TEXT, user changes the state of the lexer.
	 */
	private NameBuilderLexerState state = NameBuilderLexerState.TEXT;
	/**
	 * Storage of string as an field of characters
	 */
	private char[] data;
	
	/**
	 * The current QueryToken of the lexer
	 */
	private NameBuilderToken token; 
	
	/**
	 * Current index of the lexer in the field of data.
	 */
	private int currentIndex;
	
	/**
	 * Creates a new instance of {@link NameBuilderLexer}.
	 * 
	 * @param text
	 * @throws NullPointerException if text is null
	 */
	public NameBuilderLexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
		
	}
	
	/**
	 * Generates a new token from the text.
	 * 
	 * @return token
	 * @throws NumberBuilderLexerException if the rules of the lexer are not followed
	 */
	public NameBuilderToken nextToken() {
		
		if(currentIndex > data.length) {
			throw new NameBuilderLexerException("Cannot generate tokens after EOF");
		}
		
		if(currentIndex == data.length) {
			token = new NameBuilderToken(NameBuilderTokenType.EOF, null);
			currentIndex++;
			return token;
		}
		if(state == NameBuilderLexerState.TEXT) {
			return nextTokenText();
		} else {
			return nextTokenTag();
		}
	}

	
	/**
	 * Returns the next token in the state of TAG
	 * 
	 * @return nextToken
	 */
	private NameBuilderToken nextTokenTag() {
		
		skipBlankSpaces();
		
		if(data[currentIndex] == '}') {
			currentIndex++;
			return new NameBuilderToken(NameBuilderTokenType.ENDTAG,"}");
		} else if(data[currentIndex] == ',') {
			currentIndex++;
			return new NameBuilderToken(NameBuilderTokenType.COMMA, ",");
		} else {
			String number = collectNumber();
			return new NameBuilderToken(NameBuilderTokenType.NUMBER, number);
		}
		
	}

	/**
	 * Collects the number until it finds tag or an empty whitespace.
	 * 
	 * @return
	 */
	private String collectNumber() {
		StringBuilder sb = new StringBuilder();
		while(true) {
			if(Character.isWhitespace(data[currentIndex])) {
				break;
			}
			if(currentIndex == data.length) {
				break;
			}
			if(data[currentIndex] == '}') {
				break;
			}
			if(data[currentIndex] == ',') {
				break;
			}
			if(! Character.isDigit(data[currentIndex])) {
				throw new NameBuilderLexerException("Only numbers can be inside of tags");
			}
			sb.append(data[currentIndex]);
			currentIndex++;
			
		}
		return sb.toString();
	}

	/**
	 * Returns the next token in the state of text.
	 * 
	 * @return next token
	 */
	private NameBuilderToken nextTokenText() {
		if(checkIsTag()) {
			currentIndex += 2;
			return new NameBuilderToken(NameBuilderTokenType.TAG,"${");
		} else {
			String s = collectString();
			return new NameBuilderToken(NameBuilderTokenType.TEXT, s);
		}
	}

	/**
	 * Collects the string until it finds tag or end of data
	 * 
	 * @return string
	 */
	private String collectString() {
		StringBuilder sb = new StringBuilder();
		while(true) {
			if(checkIsTag()) {
				break;
			}
			if(currentIndex == data.length) {
				break;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return sb.toString();
	}

	/**
	 * Checks if it is ${
	 * @return
	 */
	private boolean checkIsTag() {
		return currentIndex+1 < data.length && data[currentIndex]=='$' && data[currentIndex+1] == '{';
	}

	/**
	 * Used to skip all blank spaces.
	 */
	private void skipBlankSpaces() {
		while(currentIndex < data.length && (data[currentIndex] == ' ' 
				|| data[currentIndex] == '\r'
				|| data[currentIndex] == '\t'
				|| data[currentIndex] == '\n')) {
			currentIndex++;
		}
	}
	
	/**
	 * Sets the state of the lexer.
	 * 
	 * @param state
	 */
	public void setState(NameBuilderLexerState state) {
		this.state = state;
	}
}
