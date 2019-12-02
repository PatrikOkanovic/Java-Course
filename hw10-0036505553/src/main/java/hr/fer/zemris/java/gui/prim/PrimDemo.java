package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 * Implements {@link JFrame}, used to show two lists of prime numbers.
 * Next number is generated by clicking on button "Sljedeći".
 * 
 * @author Patrik Okanovic
 *
 */
public class PrimDemo extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * Construcotr of the class
	 */
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Moj prvi prozor!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Sets the graphic interface, consists of button for generating prime numbers and
	 * two lists.
	 */
	public void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		JButton next = new JButton("Sljedeći");
		
		add(next, BorderLayout.SOUTH);
		
		PrimListModel primModel = new PrimListModel();
		
		next.addActionListener(l-> primModel.next());
		
		JList<Integer> leftList = new JList<>(primModel);
		JList<Integer> rightList = new JList<>(primModel);
		
		JPanel central = new JPanel(new GridLayout(1,0));
		central.add(new JScrollPane(leftList));
		central.add(new JScrollPane(rightList));
		
		add(central,BorderLayout.CENTER);
	}
	
	/**
	 * Main method of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->{
			PrimDemo demo = new PrimDemo();
			demo.setVisible(true);
		});
	}
}
