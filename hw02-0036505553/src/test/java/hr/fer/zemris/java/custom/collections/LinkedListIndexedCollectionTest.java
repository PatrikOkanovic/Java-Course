package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	public static LinkedListIndexedCollection collection;
	@BeforeEach
	void createData() {
		collection = new LinkedListIndexedCollection();
	}
	
	@Test
	void testSizeEmpty() {
		assertEquals(0, collection.size());	
	}
	
	@Test
	void testSizeFull() {
		collection.add(5);
		assertEquals(1, collection.size());
	}

	@Test
	void testAdd() {
		assertFalse(collection.contains(5));
		collection.add("str");
		assertTrue(collection.contains("str"));
	}
	
	@Test
	void testAddWithNull() {
		try {
			collection.add(null);
			fail("Must fail!");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testContainsNull() {
		assertFalse(collection.contains(null));
	}
	
	@Test
	void testContains() {
		collection.add(5);
		assertTrue(collection.contains(5));
	}

	@Test
	void testRemoveObject() {
		collection.add("str");
		collection.remove("str");
		assertFalse(collection.contains("str"));
		assertEquals(collection.size(),0);
	}
	
	@Test
	void testRemoveObjectNull() {
		assertFalse(collection.remove(null));
	}

	@Test
	void testToArray() {
		collection.add("a");
		collection.add("b");
		Object[] array = collection.toArray();
		assertEquals(array[0],"a");
		assertEquals(array[1],"b");
	}

	@Test
	void testForEach() {
		class ProcessorCounter extends Processor {
			int counter = 0;
			@Override
			public void process(Object value) {
				counter++;
			}
		}
		collection.add("a");
		collection.add("b");
		ProcessorCounter processor = new ProcessorCounter();
		collection.forEach(processor);
		assertEquals(2, processor.counter);
	}

	@Test
	void testClear() {
		collection.add("a");
		collection.add("b");
		collection.clear();
		assertFalse(collection.contains("a"));
		assertEquals(0, collection.size());
	}

	@Test
	void testLinkedListIndexedCollection() {
		assertEquals(0, collection.size());
	}

	@Test
	void testLinkedListIndexedCollectionCollection() {
		collection.add("a");
		collection.add("b");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(collection);
		assertTrue(col2.contains("a"));
		assertTrue(col2.contains("b"));
		assertEquals(2, col2.size());
	}
	
	@Test
	void testListIndexedCollectionCollectionWithNull() {
		try {
			collection = new LinkedListIndexedCollection(null);
			fail("Must fail");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testGet() {
		collection.add("a");
		assertEquals(collection.get(0), "a");
	}
	
	@Test
	void testGetLowerBound() {
		collection.add("a");
		try {
			assertEquals(collection.get(-1), "a");
			fail("Must fail!");
		} catch(IndexOutOfBoundsException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testGetUpperBound() {
		collection.add("a");
		try {
			collection.get(1);
			fail("Must fail!");
		} catch(IndexOutOfBoundsException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testIndexOfIn() {
		collection.add("a");
		assertEquals(collection.indexOf("a"),0);
	}
	
	@Test
	void testIndexOfOut() {
		collection.add("a");
		assertEquals(collection.indexOf("b"),-1);
	}

	@Test
	void testInsertNull() {
		try {
			collection.insert(null, 0);
			fail("Must fail!");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testInsertLowerBound() {
		try {
			collection.insert("a", -1);
			fail("Must fail!");
		} catch(IndexOutOfBoundsException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testInsertUpperBound() {
		try {
			collection.insert("a", 1);
			fail("Must fail!");
		} catch(IndexOutOfBoundsException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testInsert() {
		collection.add("a");
		collection.add("b");
		collection.insert("c", 1);
		assertEquals("c", collection.get(1));
		assertEquals(3,collection.size());
	}

	@Test
	void testRemoveInt() {
		collection.add("a");
		collection.add("b");
		collection.add("c");
		collection.remove(1);
		assertFalse(collection.contains("b"));
	}
	
	@Test
	void testRemoveIntLowerBound() {
		collection.add("a");
		try {
			collection.remove(-1);
			fail("Must fail.");
		} catch(IndexOutOfBoundsException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testRemoveIntUpperBound() {
		collection.add("a");
		try {
			collection.remove(1);
			fail("Must fail.");
		} catch(IndexOutOfBoundsException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testIsEmpty() {
		assertTrue(collection.isEmpty());
		collection.add("a");
		assertFalse(collection.isEmpty());
	}

	@Test
	void testAddAll() {
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		col2.add("a");
		col2.add("b");
		collection.addAll(col2);
		assertTrue(collection.contains("a"));
		assertTrue(collection.contains("b"));
	}

}
