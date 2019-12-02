package hr.fer.zemris.java.hw06.shell.massrename.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderComposite;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderGroup;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderGroupWithPadding;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderText;

/**
 * Simple parser used for parsing arguments from {@link MassrenameCommand} based on regular expressions.
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderParser {

	/**
	 * Storage of {@link NameBuilder}
	 */
	private List<NameBuilder> list = new ArrayList<>();
	/**
	 * The lexer
	 */
	private NameBuilderLexer lexer;
	/**
	 * Current token
	 */
	private NameBuilderToken token;
	/**
	 * Constructor of {@link NameBuilderParser}
	 * 
	 * @param expression
	 */
	public NameBuilderParser(String expression) {
		
		lexer = new NameBuilderLexer(expression);
		
		parse();
		
	}
	/**
	 * Does the parse until EOF is returned
	 */
	private void parse() {
		try {
			while(true) {
				token = lexer.nextToken();
				if(token.getType() == NameBuilderTokenType.EOF) {
					break;
				}
				
				if(token.getType() == NameBuilderTokenType.TAG) {
					lexer.setState(NameBuilderLexerState.TAG);
					getDataWithinTags();
				} else {
					NameBuilderText textBuilder = new NameBuilderText(token.getValue());
					list.add(textBuilder);
				}
				
			}
			
		} catch(NameBuilderLexerException exc) {
			throw new NameBuilderParserException(exc.getMessage());
		}
		
	}

	/**
	 * Handles with regular expressions withing tags
	 */
	private void getDataWithinTags() {
		token = lexer.nextToken();
		
		String firstArgument = token.getValue();
		
		token = lexer.nextToken();
		
		if(token.getType() == NameBuilderTokenType.ENDTAG) {
			lexer.setState(NameBuilderLexerState.TEXT);
			NameBuilderGroup groupBuilder = new NameBuilderGroup(Integer.parseInt(firstArgument));
			list.add(groupBuilder);
			return;
		}
		
		if(token.getType() != NameBuilderTokenType.COMMA) {
			throw new NameBuilderParserException("Comma must divide numbers inside tags");
		}
		
		token = lexer.nextToken();
		String secondArgument = token.getValue();
		
		int minWidth;
		char padding;
		
		if(secondArgument.startsWith("0")) {
			if(secondArgument.length() == 1) {
				minWidth = 0;
				padding = ' ';
			} else {
				padding = '0';
				minWidth = Integer.parseInt(secondArgument.substring(1));
			}
		} else {
			padding = ' ';
			minWidth = Integer.parseInt(secondArgument);
		}
		
		NameBuilderGroupWithPadding groupWithPadding = new NameBuilderGroupWithPadding(Integer.parseInt(firstArgument), padding, minWidth);
		list.add(groupWithPadding);
		
		token = lexer.nextToken();
		if(token.getType() != NameBuilderTokenType.ENDTAG) {
			throw new NameBuilderParserException("There must be an end tag");
		}
		
		lexer.setState(NameBuilderLexerState.TEXT);
		
	}

	/**
	 * Returns the {@link NameBuilderComposite}
	 * 
	 * @return nameBuilder with every nameBuilder from the parsed text
	 */
	public NameBuilder getNameBuilder() {
		return new NameBuilderComposite(list);
	}
}
