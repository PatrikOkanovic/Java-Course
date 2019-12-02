package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	private static StudentRecord record = new StudentRecord("Mirko", "Filipovic", "5", "00000005");
	
	@Test
	void testFalse() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		
		
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), // returns lastName from given record
				 expr.getStringLiteral() // returns "Bos*"
				);
		
		assertFalse(recordSatisfies);
		
	}
	
	@Test
	void testTrue() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Fil*",
				 ComparisonOperators.LIKE
				);
		
		
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), // returns lastName from given record
				 expr.getStringLiteral() // returns "Bos*"
				);
		
		assertTrue(recordSatisfies);
	}

}
