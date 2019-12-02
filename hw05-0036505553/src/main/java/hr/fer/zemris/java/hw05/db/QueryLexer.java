package hr.fer.zemris.java.hw05.db;

import java.util.Objects;


/**
 * QueryLexer used for implementing the database
 * @author Patrik Okanovic
 *
 */
public class QueryLexer {

	/**
	 * Storage of string as an field of characters
	 */
	private char[] data;
	
	/**
	 * The current QueryToken of the lexer
	 */
	private QueryToken token; 
	
	/**
	 * Current index of the lexer in the field of data.
	 */
	private int currentIndex;
	
	/**
	 * Creates a new instance of QueryLexer.
	 * @param text
	 * @throws NullPointerException if text is null
	 */
	public QueryLexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
		
	}
	
	/**
	 * Generates a new token from the text.
	 * @return token
	 * @throws LexerException if the rules of the lexer are not followed
	 */
	public QueryToken nextToken() {
		
		if(currentIndex > data.length) {
			throw new QueryLexerException();
		}
		if(currentIndex == data.length) {
			token = new QueryToken(QueryTokenType.EOF, null);
			currentIndex++;
			return token;
		}
		
		return determineNextToken();
	}
	
	/**
	 * Used for determining the next token of the lexer until the EOF is found.
	 * @return
	 * @throws QueryLexerException if the token is not valid
	 */
	private QueryToken determineNextToken() {
		
		skipBlankSpaces();
		
		if(currentIndex == data.length) {
			token = new QueryToken(QueryTokenType.EOF, null);
			currentIndex++;
			return token;
		}
		
		
		if(data[currentIndex] == '=') {
			currentIndex++;
			return new QueryToken(QueryTokenType.OPERATOR, "=");
			
		} else if(data[currentIndex] == '>') {
			
			if(currentIndex+1 < data.length && data[currentIndex+1] == '=') {
				currentIndex += 2;
				return new QueryToken(QueryTokenType.OPERATOR, ">=");
			} else {
				currentIndex++;
				return new QueryToken(QueryTokenType.OPERATOR,">");
			}
			
		} else if(data[currentIndex] == '<') {
			
			if(currentIndex+1 < data.length && data[currentIndex+1] == '=') {
				currentIndex += 2;
				return new QueryToken(QueryTokenType.OPERATOR, "<=");
			} else {
				currentIndex++;
				return new QueryToken(QueryTokenType.OPERATOR,"<");
			}
			
		} else if(data[currentIndex] == '!') {
			
			if(currentIndex+1 < data.length && data[currentIndex+1] == '=') {
				currentIndex += 2;
				return new QueryToken(QueryTokenType.OPERATOR, "!=");
			} else {
				throw new QueryLexerException("! cannot stand without =");
				
			}
			
		} else if(checkIsLike()) {
			
			currentIndex += 4;
			return new QueryToken(QueryTokenType.OPERATOR, "LIKE");
			
		} else if(checkIsAnd()) {
			
			currentIndex += 3;
			return new QueryToken(QueryTokenType.AND,"AND");
			
		} else if(data[currentIndex] == '"') {
			
			currentIndex++;
			String s = collectBetweenLiterals();
			return new QueryToken(QueryTokenType.STRING, s);
			
		} else if(Character.isLetter(data[currentIndex])) {
			String str = collectString();
			if(str.equals("firstName") || str.equals("lastName") || str.equals("jmbag")) {
				return new QueryToken(QueryTokenType.FIELD, str);
			} else {
				throw new QueryLexerException("Invalid field type");		
			}
		} else {
			throw new QueryLexerException("Invalid character");
		}
	}

	/**
	 * Collects the field name from the query
	 * @return
	 */
	private String collectString() {
		StringBuilder sb = new StringBuilder();
		while(true) {
			if(! Character.isLetter(data[currentIndex])) {
				break;
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		return sb.toString();
	}

	/**
	 * Checks if there is a LIKE operator.
	 * @return
	 */
	private boolean checkIsLike() {	
		return currentIndex + 3 < data.length 
				&& data[currentIndex]=='L'
				&& data[currentIndex+1]=='I'
				&& data[currentIndex+2]=='K'
				&& data[currentIndex+3]=='E';
	}

	/**
	 * Collects the string literal from the query.
	 * @return
	 */
	private String collectBetweenLiterals() {
		StringBuilder sb = new StringBuilder();
		while(true) {
			if(data[currentIndex] == '"') {
				currentIndex++;
				break;
			} else {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}
		
		return sb.toString();
	}

	/**
	 * Checks if there is AND between expressions
	 * @return
	 */
	private boolean checkIsAnd() {
		return currentIndex + 2 < data.length 
				&& (data[currentIndex]=='a' || data[currentIndex]=='A')
				&& (data[currentIndex+1]=='n' || data[currentIndex+1]=='N')
				&& (data[currentIndex+2]=='d' || data[currentIndex+2]=='D');
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
