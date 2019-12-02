package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class FactorialTest {

	@Test
	void testFor5() {
		long result = Factorial.calculateFactorial(5);
		assertEquals(120L, result);
	}
	
	@Test
	void testFor0() {
		long result = Factorial.calculateFactorial(0);
		assertEquals(1L, result);
	}
	
	@Test
	void testForLowerBound() {
		try {
			Factorial.calculateFactorial(-1);
			fail("Must fail!");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testForUpperBound() {
		try {
			Factorial.calculateFactorial(21);
			fail("Must fail!");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
}
	

