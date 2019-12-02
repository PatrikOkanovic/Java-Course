package hr.fer.zemris.java.hw05.db;

import java.util.Objects;
/**
 * Implements a conditional expression used after parsing the query.
 * @author Patrik Okanovic
 *
 */
public class ConditionalExpression {
	/**
	 * The field getter
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * The string literal
	 */
	private String stringLiteral;
	/**
	 * The comparison operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * The constructor of the {@link ConditionalExpression}
	 * @param fieldGetter
	 * @param stringLiteral
	 * @param comparisonOperator
	 * @throws NullPointerException if any of given parameters is null
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		Objects.requireNonNull(fieldGetter);
		Objects.requireNonNull(stringLiteral);
		Objects.requireNonNull(comparisonOperator);
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns the field getter
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns the string literal
	 * @return the stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns the comparison operator
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	
	
}
