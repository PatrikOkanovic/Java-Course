package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	static SimpleHashtable<String, Integer> examMarks;
	
	@BeforeEach
	void createData() {
		examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
	}

	@Test
	void testGet() {
		assertEquals(2, examMarks.get("Jasna"));
	}

	@Test
	void testSize() {
		assertEquals(4, examMarks.size());
	}

	@Test
	void testContainsKey() {
		assertTrue(examMarks.containsKey("Ivana"));
	}

	@Test
	void testContainsValue() {
		assertTrue(examMarks.containsValue(5));
	}

	@Test
	void testRemove() {
		assertTrue(examMarks.containsKey("Jasna"));
		examMarks.remove("Jasna");
		assertFalse(examMarks.containsKey("Jasna"));
	}

	@Test
	void testIsEmpty() {
		assertFalse(examMarks.isEmpty());
	}

	@Test
	void testClear() {
		examMarks.clear();
		assertTrue(examMarks.isEmpty());
	}

}
