package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a parser which is used in the queries to create a list of {@link ConditionalExpression}
 * @author Patrik Okanovic
 *
 */
public class QueryParser {

	/**
	 * Storage of the {@link ConditionalExpression}
	 */
	private List<ConditionalExpression> list;
	/**
	 * Lexer for the parser
	 */
	private QueryLexer lexer;
	/**
	 * Current token
	 */
	private QueryToken token;
	/**
	 * Constructor which gets the text to be parsed.
	 * @param text
	 */
	public QueryParser(String text) {
		list = new ArrayList<ConditionalExpression>();
		lexer = new QueryLexer(text);
		
		parse();
	}
	/**
	 * Parses the text.
	 * @throws QueryParserException if the text cannot be parsed or masks the {@link QueryLexerException}
	 */
	private void parse() {
		try {
				
			addTheQuery();
			
			while(true) {
				token = lexer.nextToken();
				
				if(token.getType() == QueryTokenType.EOF) {
					break;
				}
				
				if(token.getType() != QueryTokenType.AND) {
					throw new QueryParserException("AND must be between conditional expressions.");
				}
				
				addTheQuery();
				
			}
			
		} catch(QueryLexerException exc) {
			throw new QueryParserException("Exception in the lexer.");
		}
		
	}
	/**
	 * Adds the {@link ConditionalExpression} of our query, it must consist of
	 * {@link IFieldValueGetter}, {@link IComparisonOperator} and stringLiteral
	 */
	private void addTheQuery() {
		
		token = lexer.nextToken();
		if(token.getType() != QueryTokenType.FIELD) {
			throw new QueryParserException("Field must be on the left side of the operator");
		}
		
		IFieldValueGetter fieldGetter = determineFieldGetter();
		
		token = lexer.nextToken();
		if(token.getType() != QueryTokenType.OPERATOR) {
			throw new QueryParserException("Operator must be between field and string literal");
		}
		
		IComparisonOperator comparisonOperator = determineComparisonOperator();
		
		token = lexer.nextToken();
		if(token.getType() != QueryTokenType.STRING) {
			throw new QueryParserException("String literal must be on the right side");
		}
		
		String stringLiteral = token.getValue();
		
		
		ConditionalExpression expr = new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
		list.add(expr);
		
	}
	/**
	 * Determines the {@link ComparisonOperators} based on the token.
	 * @return
	 */
	private IComparisonOperator determineComparisonOperator() {
		switch(token.getValue()) {
		
		case "<" :
			return ComparisonOperators.LESS;
			
		case "<=" :
			return  ComparisonOperators.LESS_OR_EQUALS;
		
		case ">" : 
			return  ComparisonOperators.GREATER;
			
		case ">=" :
			return ComparisonOperators.GREATER_OR_EQUALS;
			
		case "=" :
			return ComparisonOperators.EQUALS;
			
		case "!=" :
			return ComparisonOperators.NOT_EQUALS;
			
		case "LIKE" : 
			return ComparisonOperators.LIKE;
		
		default :
			throw new QueryLexerException("No such operator");
		}
	}
	/**
	 * Determines the {@link FieldValueGetters} based on the token.
	 * @return
	 */
	private IFieldValueGetter determineFieldGetter() {
		
		if(token.getValue().equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
			
		} else if(token.getValue().equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
			
		} else {
			return FieldValueGetters.JMBAG;
			
		}
	}
	/**
	 *  Method must return true if query was of of the form jmbag="xxx" (i.e. it must have only one
	 *	comparison, on attribute jmbag, and operator must be equals). 
	 * @return
	 */
	public boolean isDirectQuery() {
		if(list.size() != 1) {
			return false;
		}
		ConditionalExpression expr = list.get(0);
		return expr.getComparisonOperator() == ComparisonOperators.EQUALS 
				&& expr.getFieldGetter() == FieldValueGetters.JMBAG;
		
	}
	
	/**
	 * Must return the string of jmbag which was given in equality comparison in
	 * direct query. If the query was not a direct one, method must throw IllegalStateException.
	 * @return
	 * @throws IllegalStateException
	 */
	public String getQueriedJMBAG() {
		
		if(! isDirectQuery()) {
			throw new IllegalStateException("Query is not a direct query.");
		}
		
		ConditionalExpression expr = list.get(0);
		return expr.getStringLiteral();
	}
	
	/**
	 *  For all queries, this method must return a list of conditional expressions from query; for
	 *	direct queries this list will have only one element.
	 * @return
	 */
	public List<ConditionalExpression> getQuery() {
		
		return list;
	}
}
