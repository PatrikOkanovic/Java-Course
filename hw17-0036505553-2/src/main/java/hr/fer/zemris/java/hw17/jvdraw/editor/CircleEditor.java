package hr.fer.zemris.java.hw17.jvdraw.editor;


import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
/**
 * Extends {@link GeometricalObjectEditor} called
 * when {@link Circle} is being edited.
 * 
 * @author Patrik Okanovic
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Field for x of the center
	 */
	private JTextField sx = new JTextField();
	
	/**
	 * Field for y of the center
	 */
	private JTextField sy = new JTextField();
	
	/**
	 * Field for radius
	 */
	private JTextField radius = new JTextField();

	/**
	 * x coordinate of the center
	 */
	private int SX;
	
	/**
	 * y coordinate of the center
	 */
	private int SY;
	
	/**
	 * The radius
	 */
	private int R;
	
	/**
	 * The {@link JColorChooser}
	 */
	private JColorArea colorChooser;
	
	/**
	 * Height of the text field
	 */
	private static final int HEIGHT = 2;
	
	/**
	 * Width of the text field
	 */
	private static final int WIDTH = 20;
	
	/**
	 * The {@link Circle}
	 */
	private Circle circle;
	
	/**
	 * Constructor of the class
	 * 
	 * @param circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		
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
		first.add(new JLabel("Center x:"));
		sx.setSize(WIDTH, HEIGHT);
		sx.setText(circle.getCenter().x+"");
		first.add(sx);
		add(first);
		
		// second row
		JPanel second = new JPanel();
		second.add(new JLabel("Center y:"));
		sy.setSize(WIDTH, HEIGHT);
		sy.setText(circle.getCenter().y + "");
		second.add(sy);
		add(second);
		
		// third row
		JPanel third = new JPanel();
		third.add(new JLabel("Radius:"));
		radius.setSize(WIDTH, HEIGHT);
		radius.setText(circle.getRadius()+"");
		third.add(radius);
		add(third);
		
		//fourth row
		JPanel fourth = new JPanel();
		fourth.add(new JLabel("Color:"));
		colorChooser = new JColorArea(circle.getColor());
		fourth.add(colorChooser);
		add(fourth);
	}

	@Override
	public void checkEditing() {
		try {
			SX = Integer.parseInt(sx.getText());
			SY = Integer.parseInt(sy.getText());
			R = Integer.parseInt(radius.getText());
			
			if(R <= 0) {
				throw new RuntimeException();
			}

		} catch (Exception ex) {
			throw new RuntimeException();
		}

		
	}
	
	@Override
	public void acceptEditing() {
		circle.setCenter(new Point(SX,SY));
		circle.setRadius(R);
		circle.setColor(colorChooser.getCurrentColor());

	}

}
