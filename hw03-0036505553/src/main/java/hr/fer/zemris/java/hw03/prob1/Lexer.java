package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;
/**
 * Implementation of Lexer from second problem of homework. Lexer has two
 * valid states, BASIC and ESCAPE. This class is NOT used with the Parser.
 * @author Patrik Okanovic
 *
 */
public class Lexer {

	/**
	 * State of the Lexer. Initially BASIC, user changes the state of the lexer.
	 */
	private LexerState state = LexerState.BASIC;
	/**
	 * ESCAPE sequence used for escaping in strings accepted by lexer
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
	 * Creates a new instance of Lexer.
	 * @param text
	 * @throws NullPointerException if text is null
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
	}
	/**
	 * Generates a new token from the text. Based on the following rules:
	 * @return
	 * @throws LexerException 
	 */
	public Token nextToken() {
		if(currentIndex > data.length) {
			throw new LexerException("The input has been consumed");
		}
		while(currentIndex < data.length && (data[currentIndex] == ' ' 
				|| data[currentIndex] == '\r'
				|| data[currentIndex] == '\t'
				|| data[currentIndex] == '\n')) {
			currentIndex++;
		}
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF,null);
			currentIndex++;
			return token;
		}
		if(state == LexerState.BASIC) {
			token = nextTokenBasic();
		} else {
			token = nextTokenExtended();
		}
		return token;
	}
	/**
	 * Used internally when lexer is in state EXTENDED. Returns the next token.
	 * @return token
	 */
	private Token nextTokenExtended() {
		String s = "";
		while(currentIndex < data.length &&  !(data[currentIndex] == ' ' 
				|| data[currentIndex] == '\r'
				|| data[currentIndex] == '\t'
				|| data[currentIndex] == '\n')) {
			if(data[currentIndex] == '#') {
				if(s.equals("")) {
					token = new Token(TokenType.SYMBOL,'#');
					currentIndex++;
				}
				break;
			}
			s += data[currentIndex++];
		}
		if(!s.equals("")) {
			token = new Token(TokenType.WORD, s); //if s="" the token will be #
		}
		return token;
	}
	
	/**
	 * Used internally when lexer is in state BASIC. Returns the next token form the given state.
	 * @return token
	 */
	private Token nextTokenBasic() {
		String s = "";
		if(Character.isLetter(data[currentIndex]) || data[currentIndex] == ESCAPE) {
			while(currentIndex < data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == ESCAPE)) {
				if(data[currentIndex] == ESCAPE) {
					if(currentIndex+1 == data.length || Character.isLetter(data[currentIndex+1])) {
						throw new LexerException();
					}
					s+=data[++currentIndex];
				} else {
					s += data[currentIndex];
				}
				currentIndex++;
			}
			token = new Token(TokenType.WORD,s);
		} else if(Character.isDigit(data[currentIndex])) {
			while(currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				s += data[currentIndex];
				currentIndex++;
			}
			long number;
			try {
				number = Long.parseLong(s);
				token = new Token(TokenType.NUMBER, number);
			} catch(NumberFormatException exc) {
				throw new LexerException();
			}
		} else {
//			if(data[currentIndex] == '#') {
//				setState(LexerState.EXTENDED);
//			}
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
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
}
