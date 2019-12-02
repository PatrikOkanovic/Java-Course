package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}
	
	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testTwoWords() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");
		Token token;
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TEXT,"  Štefanija\r\n\t Automobil   "), token);
	}
	
	@Test
	public void testTagAndEcho() {
		Lexer lexer = new Lexer("{$=1$}") ;
		Token token;
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"="), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.INTEGER,1), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
		
	}
	
	@Test
	public void testFunctionVariableString() {
		Lexer lexer = new Lexer("{$=1 @sin i_2 \"56\"$}") ;
		Token token;
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"="), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.INTEGER,1), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.FUNCTION,"@sin"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.VARIABLE,"i_2"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.STRING,"\"56\""), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
		
	}
	
	@Test
	public void testFOR() {
		Lexer lexer = new Lexer("{$for  @sin i_2 \"5f c6\"$}") ;
		Token token;
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"FOR"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.FUNCTION,"@sin"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.VARIABLE,"i_2"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.STRING,"\"5f c6\""), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
		
	}
	
	@Test
	public void testFOR2() {
		Lexer lexer = new Lexer("{$ FOR i-1.35bbb\"1\" $}") ;
		Token token;
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"FOR"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.VARIABLE,"i"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.DOUBLE,-1.35), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.VARIABLE,"bbb"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.STRING,"\"1\""), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
		
	}
	
	@Test
	public void longTest() {
		Lexer lexer = new Lexer("This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n") ;
		Token token;
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TEXT,"This is sample text.\r\n"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"FOR"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.VARIABLE,"i"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.INTEGER,1), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.INTEGER,10), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.INTEGER,1), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		lexer.setState(LexerState.TEXT);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TEXT,"\r\n This is "), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"="), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.VARIABLE,"i"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		lexer.setState(LexerState.TEXT);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TEXT,"-th time this message is generated.\r\n"), token);
	
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAGNAME,"END"), token);
		
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TAG,"$"), token);
		lexer.setState(LexerState.TEXT);

		token = lexer.nextToken();
		checkToken(new Token(TokenType.TEXT,"\r\n"), token);
		
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
		
	}
	
	@Test
	void testOtherValidTagNames() {
		Lexer lexer = new Lexer("{$ IFO $}") ;
		Token token;
		
		token = lexer.nextToken();
		lexer.setState(LexerState.TAG);
		token = lexer.nextToken();
		
		assertEquals(TokenType.TAGNAME, token.getType());
		assertEquals("IFO", token.getValue());
	}
	
	
	@Test
	public void testSingleSentence() {
		// Lets check for several words...
		Lexer lexer = new Lexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}");
		Token token;
		token = lexer.nextToken();
		checkToken(new Token(TokenType.TEXT,"Example { bla } blu {$=1$}. Nothing interesting {=here}"), token);
	}
	
	@Test
	void testFail1() {
		try {
			Lexer lexer = new Lexer("{$ FOR ; $}") ;
			@SuppressWarnings("unused")
			Token token;
			token = lexer.nextToken();
			lexer.setState(LexerState.TAG);
			token = lexer.nextToken();
			token = lexer.nextToken();
			fail("Must fail");
		} catch(LexerException exc) {
			assertTrue(true);
		}
		
	}
	
	@Test
	void testFail2() {
		try {
			Lexer lexer = new Lexer("{$ FOR @2a $}") ;
			@SuppressWarnings("unused")
			Token token;
			token = lexer.nextToken();
			lexer.setState(LexerState.TAG);
			token = lexer.nextToken();
			token = lexer.nextToken();
			fail("Must fail");
		} catch(LexerException exc) {
			assertTrue(true);
		}
		
	}
	
	@Test
	void testFail3() {
		try {
			Lexer lexer = new Lexer("{$ FOR 1.3. $}") ;
			@SuppressWarnings("unused")
			Token token;
			token = lexer.nextToken();
			lexer.setState(LexerState.TAG);
			token = lexer.nextToken();
			token = lexer.nextToken();
			token = lexer.nextToken();
			fail("Must fail");
		} catch(LexerException exc) {
			assertTrue(true);
		}
		
	}
	
	@Test
	void testFail4() {
		try {
			Lexer lexer = new Lexer("{$ FOR {$ $}");
			@SuppressWarnings("unused")
			Token token;
			token = lexer.nextToken();
			lexer.setState(LexerState.TAG);
			token = lexer.nextToken();
			token = lexer.nextToken();
			fail("Must fail");
		} catch(LexerException exc) {
			assertTrue(true);
		}
		
	}
	
	
	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
}

}
