package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	private static ObjectMultistack multiStack;
	
	@BeforeEach
	void resetData() {
		multiStack = new ObjectMultistack();
	}
	
	@Test
	void testPush() {
		multiStack.push("year", new ValueWrapper(Integer.valueOf(5)));
		multiStack.push("month", new ValueWrapper(Integer.valueOf(6)));
		assertEquals(Integer.valueOf(5), multiStack.peek("year").getValue());
		assertEquals(Integer.valueOf(6), multiStack.peek("month").getValue());
	}

	@Test
	void testPop() {
		multiStack.push("year", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(Integer.valueOf(5), multiStack.pop("year").getValue());
		assertTrue(multiStack.isEmpty("year"));
	}
	
	@Test
	void testPopThrows() {
		assertThrows(EmptyStackException.class,()-> multiStack.pop("year"));
		multiStack.push("year", new ValueWrapper(Integer.valueOf(5)));
		multiStack.pop("year").getValue();
		assertThrows(EmptyStackException.class,()-> multiStack.pop("year"));
	}

	@Test
	void testPeek() {
		multiStack.push("year", new ValueWrapper(Integer.valueOf(2)));
		assertEquals(Integer.valueOf(2), multiStack.peek("year").getValue());
		multiStack.push("year", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(Integer.valueOf(5), multiStack.peek("year").getValue());
	}
	
	@Test
	void testPeekThrows() {
		assertThrows(EmptyStackException.class,()-> multiStack.peek("year"));
		multiStack.push("year", new ValueWrapper(Integer.valueOf(2)));
		multiStack.pop("year");
		assertThrows(EmptyStackException.class,()-> multiStack.peek("year"));
	}
	@Test
	void testIsEmpty() {
		assertTrue(multiStack.isEmpty("year"));
		multiStack.push("year", new ValueWrapper(Integer.valueOf(2)));
		assertFalse(multiStack.isEmpty("year"));
		multiStack.pop("year");
		assertTrue(multiStack.isEmpty("year"));
		
	}

}
