package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {
	
	public static Dictionary<String, Integer> dictionary;
	
	@BeforeEach
	void createData() {
		dictionary = new Dictionary<>();
	}

	@Test
	void testIsEmpty() {
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	void isNotEmpty() {
		dictionary.put("Miso", 5);
		assertFalse(dictionary.isEmpty());
	}

	@Test
	void testSize() {
		assertEquals(0, dictionary.size());
		dictionary.put("Miso", 5);
		assertEquals(1, dictionary.size());
	}

	@Test
	void testClear() {
		dictionary.put("Miso", 5);
		dictionary.put("Drazen", 5);
		dictionary.clear();
		assertTrue(dictionary.isEmpty());
	}

	@Test
	void testPut() {
		dictionary.put("Miso", 5);
		assertEquals(5, dictionary.get("Miso"));
		dictionary.put("Miso", 4);
		assertEquals(1, dictionary.size());
		assertEquals(4, dictionary.get("Miso"));
		assertDoesNotThrow(()->dictionary.put("Ive", null));
	}
	
	@Test
	void testPutNull() {
		assertThrows(NullPointerException.class,()-> dictionary.put(null, 2));
	}

	@Test
	void testGet() {
		dictionary.put("Miso",5);
		assertEquals(5, dictionary.get("Miso"));
	}
	
	@Test
	void testGetNoSuchElement() {
		assertThrows(NoSuchElementException.class, () -> dictionary.get("Miso"));
	}

}
