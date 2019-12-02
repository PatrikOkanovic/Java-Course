package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {

	@Test
	void testAddNode() {
		TreeNode glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		
		assertEquals(glava.value, 42);
		assertEquals(glava.left.value,21);
		assertEquals(glava.left.right.value, 35);
		assertEquals(glava.right.value, 76);
	}
	
	@Test
	void testTreeSize() {
		TreeNode glava = null;
		int size = UniqueNumbers.treeSize(glava);
		assertEquals(size, 0);
		
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
		size = UniqueNumbers.treeSize(glava);
		assertEquals(size, 4);
		
	}
	
	@Test
	void testContainsValue() {
		TreeNode glava = null;
		assertFalse(UniqueNumbers.containsValue(glava, 42));
		glava = UniqueNumbers.addNode(glava, 42);
		assertTrue(UniqueNumbers.containsValue(glava, 42));
	}

}
