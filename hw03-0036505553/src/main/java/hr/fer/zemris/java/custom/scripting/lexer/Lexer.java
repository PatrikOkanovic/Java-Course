package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/** 
 * Implementation of Lexer from third problem of homework. Lexer has two
 * valid states, TAG and EXTENDED. This class is  used with the SmartScriptParser
 * @author Patrik OKanovic
 *
 */
public class Lexer {
	/**
	 * State of the Lexer. Initially TEXT, user changes the state of the lexer.
	 */
	private LexerState state = LexerState.TEXT;
	/**
	 * ESCAPE sequence used for escaping in strings accepted by lexer in specific
	 * accepted situations.
	 */
	private static final char ESCAPE = '\\';
	/**
	 * Storage of string as an field of characters
	 */
	private char[] data; 
	/**
	 * The current Token of the lexer
	 */
	private Token token; 
	/**
	 * Current index of the lexer in the field of data.
	 */
	private int currentIndex;
	/**
	 * Used internally for validating if the TAGNAME has been found. Because 
	 * valid TAGNAME can also be a variable.
	 */
	private boolean firstTagInSequence = true;
	/**
	 * Creates a new instance of Lexer.
	 * @param text
	 * @throws NullPointerException if text is null
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
	}
	
	/**
	 * Generates a new token from the text.
	 * @return token
	 * @throws LexerException if the rules of the lexer are not followed
	 */
	public Token nextToken() {
		if(currentIndex > data.length) {
			throw new LexerException();
		}
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return token;
		}
		if(state == LexerState.TEXT) {
			return nextTokenTetx();
		} else {
			return nextTokenTag();
		}
		
	}
	/**
	 * Internally used method for returning the token when lexer is in state 
	 * of TAG
	 * @return token
	 * @throws LexerException
	 */
	private Token nextTokenTag() throws LexerException {
		skipBlankSpaces();
		String s;
		if(currentIndex+3 < data.length && (Character.toUpperCase(data[currentIndex])=='F' 
				&& Character.toUpperCase(data[currentIndex+1])=='O'
				&& Character.toUpperCase(data[currentIndex+2])=='R'
				&& data[currentIndex+3]==' ')) {
			firstTagInSequence = false;
			token = new Token(TokenType.TAGNAME,"FOR");
			currentIndex += 4;
		} else if(data[currentIndex] == '=') {
			firstTagInSequence = false;
			token = new Token(TokenType.TAGNAME,"=");
			currentIndex++;
		} else if(data[currentIndex]=='+' || data[currentIndex]=='*' 
				|| data[currentIndex]=='-' || data[currentIndex]=='/'
				|| data[currentIndex]=='^') {
			if(currentIndex+1 < data.length && data[currentIndex] == '-' && Character.isDigit(data[currentIndex+1])) {
				
				s = data[currentIndex++] + getNumber();
				return determinIntegerOrDouble(s);
				
			} else {
				token = new Token(TokenType.OPERATOR, data[currentIndex]+"");
				currentIndex++;
			}
		} else if(data[currentIndex] == '@') {
			currentIndex++;
			s ="@" + getVariableName();
			token = new Token(TokenType.FUNCTION, s);
			
		} else if(Character.isLetter(data[currentIndex])) {
			s = getVariableName();
			if(firstTagInSequence) {
				token = new Token(TokenType.TAGNAME, s);
			} else {
				token = new Token(TokenType.VARIABLE, s);
			}
		} else if(Character.isDigit(data[currentIndex])) {
			s = getNumber();
			return determinIntegerOrDouble(s);
		} else if(data[currentIndex] == '\"') {	
			s = data[currentIndex++] + getString();
			token = new Token(TokenType.STRING, s);
		} else if(currentIndex+3 < data.length && (Character.toUpperCase(data[currentIndex])=='E' 
				&& Character.toUpperCase(data[currentIndex+1])=='N'
				&& Character.toUpperCase(data[currentIndex+2])=='D'
				&& (data[currentIndex+3]==' ' || data[currentIndex+3]=='$')) ){
			token = new Token(TokenType.TAGNAME, "END" );
		} else if(currentIndex+1 < data.length && data[currentIndex] == '$' && data[currentIndex+1] =='}') {
			token = new Token(TokenType.TAG, "$");
			currentIndex +=2;
			firstTagInSequence = true;
		} else {
			throw new LexerException();
		}
		
		return token;
		
	}


	/**
	 * Returns the acceptable string for the lexer which will be inside of tags.
	 * @return string
	 * @throws LexerException 
	 */
	private String getString() {
		String s = "";
		while(currentIndex < data.length ) {
			if(data[currentIndex] == '\"') {
				s+=data[currentIndex++];
				break;
			} else if(data[currentIndex] == ESCAPE) {
				if(currentIndex+1 < data.length && data[currentIndex+1] == ESCAPE) {
					s+=ESCAPE;
					currentIndex +=2;
				} else if(currentIndex+1 < data.length && data[currentIndex+1] == '\"') {
					s+='\"';
					currentIndex += 2;
				} else if(currentIndex+1 < data.length && (data[currentIndex+1]=='r'
						|| data[currentIndex+1]=='t' || data[currentIndex+1]=='n')) {

					if(currentIndex+1 < data.length && data[currentIndex+1]=='r') {
						s+='\r';
					} else if(currentIndex+1 < data.length && data[currentIndex+1]=='t') {
						s+='\t';
					} else {
						s+='\n';
					}
					currentIndex+=2;
				} else {
					throw new LexerException();
				}
					
			} else {
				s+=data[currentIndex++];
			}
		}
		return s;
	}

	/**
	 * Returns the string of the number from inside of tags. Can be an integer or an
	 * double. Accepts the double number only with the dot.
	 * @return
	 */
	private String getNumber() {
		String s = "";
		boolean foundDot = false;
		while(true) {
			if(Character.isDigit(data[currentIndex])) {
				s+=data[currentIndex++];
			} else if(data[currentIndex] == '.' && !foundDot) {
				s+=data[currentIndex++];
			} else {
				break;
			}
			
		}
		return s;
	}

	/**
	 * Returns the valid variable name from inside of tags. Must start with the letter and can consist of 
	 * letters numbers or _
	 * @return variable name
	 */
	private String getVariableName() {
		if(!Character.isLetter(data[currentIndex])) {
			throw new LexerException();
		}
		String s = "";
		while(currentIndex < data.length) {
			if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '_'
					|| Character.isDigit(data[currentIndex])) {
				s+=data[currentIndex++];
			} else {
				break;
			}
		}
		return s;
	}

	/**
	 * Returns the token when lexer is in state of TEXT.
	 * @return token
	 * @throws LexerException
	 */
	private Token nextTokenTetx() {
		String s = "";
		while(true) {
			if(currentIndex == data.length) {
				break;
			}
//			if(data[currentIndex] == '$') {
//				token = new Token(TokenType.TAG,"$");
//				currentIndex++;
//				break;
			if(data[currentIndex] == ESCAPE) {
				if(currentIndex+2 < data.length && data[currentIndex+1] == '{' 
						&& data[currentIndex+2] == '$') {
					s += "{$";
					currentIndex+=2;
				} else if(currentIndex+1 < data.length && data[currentIndex+1] == ESCAPE) {
					s += ESCAPE;
					currentIndex++;
				} else {
					throw new LexerException();
				}
			} else if(currentIndex+1 < data.length && data[currentIndex] == '{' && data[currentIndex+1] == '$') {
				if(!s.equals("")) {
					break;
				} else {
					currentIndex +=2;
					return new Token(TokenType.TAG,"$");
				}
			} else {
				s+=data[currentIndex];
			}
			
			currentIndex++;
		}
		
		if(!s.equals("")) {
			token = new Token(TokenType.TEXT,s);
		}
		return token;
		
	}

	/**
	 * Returns the last generated token. Can be called multiple times. Does
	 * not start generating the next token.
	 * @return
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * Sets the state of the lexer.
	 * @param state
	 * @throws NullPointerException if state is null
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	/**
	 * Used in the state of TAG to skip all blank spaces.
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
	 * Determines from the string s if it can be parsable to integer or to
	 * double else throws an exception.
	 * @param s integer or double in string
	 * @return token with the given number
	 * @throws LexerException
	 */
	private Token determinIntegerOrDouble(String s) {
		try {
			int myInteger = Integer.parseInt(s);
			token = new Token(TokenType.INTEGER, myInteger);
			return token;
		} catch(NumberFormatException exc) {
			try {
				double myDouble = Double.parseDouble(s);
				token = new Token(TokenType.DOUBLE, myDouble);
				return token;
			} catch(NumberFormatException exc1) {
				throw new LexerException();
			}
		}
	}
	/**
	 * Returns the current state if the lexer.
	 * @return
	 */
	public LexerState getState() {
		return state;
	}
}
