package hr.fer.zemris.java.hw06.shell.parser;

import java.util.ArrayList;
import java.util.List;


import hr.fer.zemris.java.hw06.shell.MyShell;
/**
 * Simple parser used for parsing paths from {@link MyShell}.
 * 
 * @author Patrik Okanovic
 *
 */
public class ShellParser {

	/**
	 * Storage of paths as strings in a list.
	 */
	public List<String> paths = new ArrayList<>();
	
	/**
	 * Lexer for the parser
	 */
	private ShellLexer lexer;
	
	/**
	 * Current token
	 */
	private ShellToken token;
	
	public ShellParser(String text) {
		lexer = new ShellLexer(text);
		
		parse();
	}

	/**
	 * Does the parse of the shell input.
	 */
	private void parse() {
		try {
			while(true) {
				token = lexer.nextToken();
				if(token.getType() == ShellTokenType.EOF) {
					break;
				}
				paths.add(token.getValue());
			}
		} catch(ShellLexerException exc) {
			throw new ShellParserException(exc.getMessage());
		}
		
	}
	
	/**
	 * Returns the list with paths as string after parsing the input from {@link MyShell}
	 * @return
	 */
	public List<String> getPaths() {
		return paths;
	}
	
}
