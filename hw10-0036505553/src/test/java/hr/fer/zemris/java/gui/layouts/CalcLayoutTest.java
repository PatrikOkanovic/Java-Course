package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {
	
	CalcLayout layout = new CalcLayout();

	@Test
	void invalidAddLayoutComponent1() {
		JLabel label = new JLabel();
		assertThrows(CalcLayoutException.class, ()-> layout.addLayoutComponent(label, new RCPosition(-1, 2)));
		assertThrows(CalcLayoutException.class, ()-> layout.addLayoutComponent(label, new RCPosition(6, 2)));
		assertThrows(CalcLayoutException.class, ()-> layout.addLayoutComponent(label, new RCPosition(2, 0)));
		assertThrows(CalcLayoutException.class, ()-> layout.addLayoutComponent(label, new RCPosition(2, 8)));

	}
	
	@Test
	void invalidAddLayoutComponent2() {
		JLabel label = new JLabel();
		assertThrows(CalcLayoutException.class, ()-> layout.addLayoutComponent(label, new RCPosition(1, 2)));

	}
	
	@Test
	void invalidAddLayoutComponent3() {
		JLabel label = new JLabel();
		layout.addLayoutComponent(label, new RCPosition(2, 1));
		assertThrows(CalcLayoutException.class, ()-> layout.addLayoutComponent(label, new RCPosition(2, 1)));

	}
	
	@Test
	void testLayout1() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), new RCPosition(1,1));
		p.add(new JLabel("y"), new RCPosition(2,3));
		p.add(new JLabel("z"), new RCPosition(2,7));
		p.add(new JLabel("w"), new RCPosition(4,2));
		p.add(new JLabel("a"), new RCPosition(4,5));
		p.add(new JLabel("b"), new RCPosition(4,7));

	}
	
	@Test
	void testPrefferedSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.getWidth());
		assertEquals(158, dim.getHeight());
		
		
	}
	
	@Test
	void testPrefferedSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.getWidth());
		assertEquals(158, dim.getHeight());
	}
}
