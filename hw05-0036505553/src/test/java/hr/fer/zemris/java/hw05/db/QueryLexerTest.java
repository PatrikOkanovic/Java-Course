package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;




class QueryLexerTest {

	
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new QueryLexer(null));
	}
	
	@Test
	public void testEmpty() {
		QueryLexer lexer = new QueryLexer("");

		assertEquals(QueryTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	void test() {
		QueryLexer lexer = new QueryLexer("jmbag=\"0123456789\" and lastName>\"J\" AND firstName!=\"a\" and jmbag LIKE \"b\"");
		
		QueryToken token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.FIELD, "jmbag"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.OPERATOR, "="));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.STRING, "0123456789"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.AND, "AND"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.FIELD, "lastName"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.OPERATOR, ">"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.STRING, "J"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.AND, "AND"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.FIELD, "firstName"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.OPERATOR, "!="));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.STRING, "a"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.AND, "AND"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.FIELD, "jmbag"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.OPERATOR, "LIKE"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.STRING, "b"));
		
		token = lexer.nextToken();
		checkToken(token, new QueryToken(QueryTokenType.EOF, null));
		
	
	}
	
	
	private void checkToken(QueryToken actual, QueryToken expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
	}

}
