package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.GridLayout;

import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
/**
 * Extends {@link GeometricalObjectEditor} called
 * when {@link Line} is being edited.
 * 
 * @author Patrik Okanovic
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link Line}
	 */
	private Line line;
	
	/**
	 * x coordinate of begining point field
	 */
	private JTextField x1 = new JTextField();
	
	/**
	 * y coordinate of begining point field
	 */
	private JTextField y1 = new JTextField();
	
	/**
	 * x coordinate of ending point field
	 */
	private JTextField x2 = new JTextField();
	
	/**
	 * y coordinate of ending point field
	 */
	private JTextField y2 = new JTextField();
	
	/**
	 * x coordinate of begining point 
	 */
	private int X1;
	/**
	 * y coordinate of begining point 
	 */
	private int Y1;
	
	/**
	 * x coordinate of ending point field
	 */
	private int X2;
	/**
	 * y coordinate of ending point field
	 */
	private int Y2;
	/**
	 * Height of the text field
	 */
	private static final int HEIGHT = 2;
	/**
	 * Width of the text field
	 */
	private static final int WIDTH = 20;
	
	/**
	 * The {@link JColorChooser}
	 */
	private JColorArea colorChooser;	
	
	/**
	 * Constructor of the class
	 * 
	 * @param line
	 */
	public LineEditor(Line line) {
		this.line = line;
		
		initGUI();
		
		setVisible(true);
		
	}
	/**
	 * Creates the graphic interface of the {@link GeometricalObjectEditor}
	 */
	private void initGUI() {
		setLayout(new GridLayout(0,1));
		//first row
		JPanel first = new JPanel();
		first.add(new JLabel("x1:"));
		x1.setSize(WIDTH, HEIGHT);
		x1.setText(line.getStartPoint().x+"");
		first.add(x1);
		add(first);
		
		//second row
		JPanel second = new JPanel();
		second.add(new JLabel("y1:"));
		y1.setSize(WIDTH, HEIGHT);
		y1.setText(line.getStartPoint().y+"");
		second.add(y1);
		add(second);
		
		// third row
		JPanel third = new JPanel();
		third.add(new JLabel("x2:"));
		x2.setSize(WIDTH, HEIGHT);
		x2.setText(line.getEndPoint().x+"");
		third.add(x2);
		add(third);
		
		// fourth row
		JPanel fourth = new JPanel();
		fourth.add(new JLabel("y2:"));
		y2.setSize(WIDTH, HEIGHT);
		y2.setText(line.getEndPoint().y+"");
		fourth.add(y2);
		add(fourth);
		
		//fifth row
		JPanel fifth = new JPanel();
		fifth.add(new JLabel("Color:"));
		colorChooser = new JColorArea(line.getColor());
		fifth.add(colorChooser);
		add(fifth);
		
		

	}
	@Override
	public void checkEditing() {
		try {
			X1 = Integer.parseInt(x1.getText());
			Y1 = Integer.parseInt(y1.getText());
			
			X2 = Integer.parseInt(x2.getText());
			Y2 = Integer.parseInt(y2.getText());
				
					
		} catch(Exception ex) {
			throw new RuntimeException();
		}

	}



	@Override
	public void acceptEditing() {
		
		line.setStartPoint(new Point(X1,Y1));
		line.setEndPoint(new Point(X2,Y2));
		line.setColor(colorChooser.getCurrentColor());
	}

}
