package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	private static ValueWrapper value = new ValueWrapper(Integer.valueOf(5));
	
	@Test
	void testGetValue() {
		assertEquals(5, value.getValue());
	}

	@Test
	void testSetValue() {
		value.setValue(Double.valueOf(6.5));
		assertEquals(6.5, value.getValue());
	}

	@Test
	void testAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); 
		assertEquals(0, v1.getValue());
	}
	
	@Test
	void testAdd2() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); 
		assertEquals(Integer.valueOf(13), v5.getValue());
	}
	
	@Test
	void testAddThrows() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(true);
		assertThrows(RuntimeException.class, () -> v1.add(v2.getValue())); 
	}
	
	@Test
	void testAddThrows2() {
		ValueWrapper v1 = new ValueWrapper(true);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(5.5));
		assertThrows(RuntimeException.class, () -> v1.add(v2.getValue())); 
	}
	
	
	@Test
	void testAddThrows3() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () ->v7.add(v8.getValue()));
	}
	

	@Test
	void testSubtract() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(1));
		v1.subtract(v2.getValue()); 
		assertEquals(Double.valueOf(11), v1.getValue());
	}

	@Test
	void testMultiply() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(1));
		v1.multiply(v2.getValue()); 
		assertEquals(Double.valueOf(5), v1.getValue());
	}

	@Test
	void testDivide() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper("2");
		v1.divide(v2.getValue()); 
		assertEquals(Integer.valueOf(2), v1.getValue());
	}
	
	@Test
	void testDivideThrows() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(null);
		assertThrows(IllegalArgumentException.class, () -> v1.divide(v2.getValue())); 
	}

	@Test
	void testNumCompare() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	void testNumCompare2() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(null);
		assertTrue(v1.numCompare(v2.getValue()) > 0);
	}
	
	@Test
	void testNumCompare3() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(5.5));
		ValueWrapper v2 = new ValueWrapper("1.5E2");
		assertTrue(v1.numCompare(v2.getValue()) < 0);
	}

}
