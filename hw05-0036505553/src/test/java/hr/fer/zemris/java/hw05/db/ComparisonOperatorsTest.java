package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void testLIKE() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertTrue(oper.satisfied("ABA", "ABA"));
		assertFalse(oper.satisfied("AbA", "ABA"));
		
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		
		assertTrue(oper.satisfied("Neda", "*da"));
		assertTrue(oper.satisfied("Neda", "Ne*"));
	}
	
	@Test
	void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Branka"));
	}
	
	@Test
	void testLessOrEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Branka"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertTrue(oper.satisfied("Branka","Ana"));
	}
	
	@Test
	void testGreaterOrEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Branka","Ana"));
		assertTrue(oper.satisfied("Branka","Branka"));
	}
	
	@Test
	void testEqual() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("Branka","Branka"));
	}
	
	@Test
	void testNotEqual() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Branka","Ana"));
	}
	

}
