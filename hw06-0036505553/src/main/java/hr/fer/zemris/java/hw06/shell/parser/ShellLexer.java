package hr.fer.zemris.java.hw06.shell.parser;

import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.MyShell;
/**
 * ShellLexer used for implementing the {@link MyShell}
 * @author Patrik Okanovic
 *
 */

public class ShellLexer {

	/**
	 * Storage of string as an field of characters
	 */
	private char[] data;
	
	/**
	 * The current QueryToken of the lexer
	 */
	private ShellToken token; 
	
	/**
	 * Current index of the lexer in the field of data.
	 */
	private int currentIndex;
	
	/**
	 * Creates a new instance of QueryLexer.
	 * @param text
	 * @throws NullPointerException if text is null
	 */
	public ShellLexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
		
	}
	
	
	/**
	 * Generates a new token from the text.
	 * @return token
	 * @throws LexerException if the rules of the lexer are not followed
	 */
	public ShellToken nextToken() {
		
		if(currentIndex > data.length) {
			throw new ShellLexerException();
		}
		if(currentIndex == data.length) {
			token = new ShellToken(ShellTokenType.EOF, null);
			currentIndex++;
			return token;
		}
		
		return determineNextToken();
	}

	/**
	 * Used for determining the next token of the lexer until the EOF is found.
	 * @return
	 * @throws ShellLexerException if the token is not valid
	 */
	private ShellToken determineNextToken() {
		
		skipBlankSpaces();
		
		if(currentIndex == data.length) {
			token = new ShellToken(ShellTokenType.EOF, null);
			currentIndex++;
			return token;
		}
		
		if(data[currentIndex] == '"') {
			currentIndex++;
			String s = collectStringPath();
			return new ShellToken(ShellTokenType.TEXT,s);
			
		} else {
			String s = collectPath();
			return new ShellToken(ShellTokenType.TEXT,s);
		}
	}
	
	/**
	 * Collects everything until finds a blank space
	 * 
	 * @return
	 */
	private String collectPath() {
		StringBuilder sb = new StringBuilder();
		while(true) {
			if(currentIndex == data.length) {
				break;
			}
			if(Character.isSpaceChar(data[currentIndex])) {
				break;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return sb.toString();
	}


	/**
	 * Collects the field name from the query
	 * @return
	 */
	private String collectStringPath() {
		StringBuilder sb = new StringBuilder();
		boolean foundClosingTag = false;
		while(true) {
			if(currentIndex == data.length) {
				break;
			}
			if(data[currentIndex] == '\\') {
				if(currentIndex+1 < data.length && data[currentIndex+1]=='\\' ) {
					sb.append('\\');
					currentIndex += 2;
				} else if(currentIndex+1 < data.length && data[currentIndex+1]=='"') {
					sb.append('"');
					currentIndex += 2;
				}
			}
			if(data[currentIndex] == '"') {
				currentIndex++;
				foundClosingTag = true;
				if(currentIndex+1 < data.length && data[currentIndex+1] != ' ') {
					throw new ShellLexerException("After path in a string must be an empty space");
				}
				break;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		if(foundClosingTag) {
			return sb.toString();
		} else {
			throw new ShellLexerException("Did not found a closing \"");
		}
		
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
}
