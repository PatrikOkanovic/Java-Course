package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	private static StudentRecord student = new StudentRecord("Mirko", "Filipovic", "5", "00000005");
	
	@Test
	void testFirstNameGetter() {
		IFieldValueGetter getter = FieldValueGetters.FIRST_NAME;
		assertEquals("Mirko", getter.get(student));
	}
	
	@Test
	void testLasttNameGetter() {
		IFieldValueGetter getter = FieldValueGetters.LAST_NAME;
		assertEquals("Filipovic", getter.get(student));
	}
	
	@Test
	void testJMBAGGetter() {
		IFieldValueGetter getter = FieldValueGetters.JMBAG;
		assertEquals("00000005", getter.get(student));
	}

}
