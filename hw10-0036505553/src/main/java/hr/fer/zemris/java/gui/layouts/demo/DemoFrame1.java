package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
/**
 * Demo program from homework
 * 
 * @author Patrik Okanovic
 *
 */
public class DemoFrame1 extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	public DemoFrame1() {
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		initGUI();
//		pack();
//	}
	
	/**
	 * Constructor of the class
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Initializes the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	/**
	 * Creates a yellow label with given text
	 * 
	 * @param text written on the label
	 * @return label
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * Main program of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}
	


}

