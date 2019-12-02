package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JList;

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	@Test
	void testSize() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
		model.next();
		model.next();
		assertEquals(3, model.getSize());
	}
	
	@Test
	void testElementAt() {
		PrimListModel model = new PrimListModel();
		model.next();
		model.next();
		assertEquals(1, model.getElementAt(0));
		assertEquals(2, model.getElementAt(1));
		assertEquals(3, model.getElementAt(2));
	}
	
	@Test
	void testListeners() {
		PrimListModel model = new PrimListModel();
		JList<Integer> list = new JList<>(model);
		model.next();
		model.next();
		assertEquals(3, list.getModel().getSize());
		assertEquals(2, list.getModel().getElementAt(1));
	}

}
