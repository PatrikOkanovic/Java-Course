package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.*;
/**
 * Implementation of the parser. Uses the lexer to get tokens and to check if the
 * program has been correctly written. If anything unplanned happens wraps the exception and
 * throws the {@link SmartScriptParserException}
 * @author Patrik Okanovic
 *
 */
public class SmartScriptParser {
	/**
	 * Current token given from the lexer
	 */
	private Token token;
	/**
	 * Text which is being parsed.
	 */
	private String documentBody;
	/**
	 * 
	 */
	private DocumentNode documentNode = new DocumentNode();
	/**
	 * The lexer
	 */
	private Lexer lexer;
	/**
	 * Creates the parser and the lexer the  delegates the work of parsing to other methods.
	 * @param documentBody
	 */
	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
		 lexer = new Lexer(this.documentBody);
		 doTheParsing();
	}
	
	/**
	 * Does the parsing by calling other methods for specific situations. After the parsing 
	 * after the parsing is done the DocumentNode is created.
	 */
	private void doTheParsing() {
		ObjectStack stack = new ObjectStack();
		stack.push(documentNode);
		try {
			token = lexer.nextToken();
			Node parent, child;
			
			while(token.getType() != TokenType.EOF) {
				
				if(token.getType() ==TokenType.TEXT) {
					parent = (Node)stack.pop();
					child = new TextNode((String) token.getValue()) ;
					parent.addChildNode(child);
					stack.push(parent);
					
				} else if(token.getType() == TokenType.TAG) {
					if(lexer.getState() == LexerState.TAG) { //shifting state when finding tag
						lexer.setState(LexerState.TEXT);
					} else {
						lexer.setState(LexerState.TAG);
					}
					
				} else if(token.getType() == TokenType.TAGNAME && token.getValue().equals("END")) {
					try {
						stack.pop();
					} catch(EmptyStackException exc) {
						throw new SmartScriptParserException();
					}
					
				} else if(token.getType() == TokenType.TAGNAME && token.getValue().equals("FOR")) {
					parent =(Node) stack.pop();
					child = doForToken(); //create FoorLoopNode in the method
					parent.addChildNode(child);
					stack.push(parent);
					stack.push(child);
						
				} else if(token.getType() == TokenType.TAGNAME && token.getValue().equals("=")) {
					parent = (Node)stack.pop();
					child = doEchoToken(); //create EchoNode in the method
					parent.addChildNode(child);
					stack.push(parent);
				} else {
					throw new SmartScriptParserException();
				}
				token = lexer.nextToken();
			}
			if(stack.size() != 1) {
				throw new SmartScriptParserException(); // eception if some for loop node is not closed with the end tag
			}
		} catch(LexerException exc) {
			throw new SmartScriptParserException();
		}
		
	}
	
	/**
	 * Does the parsing after the TAGNAME FOR is found.
	 * @return ForLoopNode
	 * @throws LexerException
	 */
	private ForLoopNode doForToken() throws LexerException{
		ElementVariable variable;
		Element startExpression, endExpression,stepExpression;
		token = lexer.nextToken();
		if(token.getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException();
		}
		variable = new ElementVariable((String) token.getValue());
		
		token = lexer.nextToken();
		if(token.getType() == TokenType.DOUBLE) {
			startExpression = new ElementConstantDouble((double)token.getValue());
		} else if(token.getType() == TokenType.INTEGER) {
			startExpression = new ElementConstantInteger((int)token.getValue());
		} else if(token.getType() == TokenType.VARIABLE) {
			startExpression = new ElementVariable((String)token.getValue());
		} else if(token.getType() == TokenType.STRING) {
			startExpression = new ElementString((String)token.getValue());
		} else {
			throw new SmartScriptParserException();
		}
		
		token = lexer.nextToken();
		if(token.getType() == TokenType.DOUBLE) {
			endExpression = new ElementConstantDouble((double)token.getValue());
		} else if(token.getType() == TokenType.INTEGER) {
			endExpression = new ElementConstantInteger((int)token.getValue());
		} else if(token.getType() == TokenType.VARIABLE) {
			endExpression = new ElementVariable((String)token.getValue());
		} else if(token.getType() == TokenType.STRING) {
			endExpression = new ElementString((String)token.getValue());
		} else {
			throw new SmartScriptParserException();
		}
		
		token = lexer.nextToken();
		if(token.getType() == TokenType.TAG) {
			lexer.setState(LexerState.TEXT);
			return new ForLoopNode(variable, startExpression, endExpression, null);
		} else if(token.getType() == TokenType.DOUBLE) {
			stepExpression = new ElementConstantDouble((double)token.getValue());
		} else if(token.getType() == TokenType.INTEGER) {
			stepExpression = new ElementConstantInteger((int)token.getValue());
		} else if(token.getType() == TokenType.VARIABLE) {
			stepExpression = new ElementVariable((String)token.getValue());
		} else if(token.getType() == TokenType.STRING) {
			stepExpression = new ElementString((String)token.getValue());
		} else {
			throw new SmartScriptParserException();
		}
		
		token = lexer.nextToken();
		if(token.getType() == TokenType.TAG) {
			lexer.setState(LexerState.TEXT);
			return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		} else {
			throw new SmartScriptParserException();
		}
		
		
	}
	
	/**
	 * Does the parsing after the TAGNAME = is found. Stays in this method creating
	 * the EchoNode until the TAG $ token is found or either the exception has been found.
	 * @return
	 * @throws LexerException
	 */
	private EchoNode doEchoToken() throws LexerException {
		EchoNode echoNode = null;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		while(true) {
			token = lexer.nextToken();
			if(token.getType() == TokenType.TAG) {
				lexer.setState(LexerState.TEXT);
				break;
			} else if(token.getType() == TokenType.DOUBLE) {
				collection.add( new ElementConstantDouble((double)token.getValue()));
			} else if(token.getType() == TokenType.INTEGER) {
				collection.add( new ElementConstantInteger((int)token.getValue()));
			} else if(token.getType() == TokenType.FUNCTION) {
				collection.add( new ElementFunction((String)token.getValue()));
			} else if(token.getType() == TokenType.OPERATOR) {
				collection.add( new ElementOperator((String) token.getValue()));
			} else if(token.getType() == TokenType.STRING) {
				collection.add( new ElementString((String) token.getValue()));
			} else if(token.getType() == TokenType.VARIABLE) {
				collection.add( new ElementVariable((String) token.getValue()));
			} else {
				throw new SmartScriptParserException();
			}
			
		}
		
		if(collection.isEmpty()) {
			throw new SmartScriptParserException(); //EchoNode must not be empty
		}
		Object[] elements = collection.toArray();
		Element[] argument = new Element[collection.size()];
		for(int i = 0; i < collection.size(); i++) {
			argument[i] = (Element)elements[i];
		}
		echoNode = new EchoNode(argument);
		return echoNode;
	}

	/**
	 * Returns the DocumentNode of the parsed text.
	 * @return DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
}
