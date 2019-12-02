package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.GridLayout;

import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
/**
 * Extends {@link GeometricalObjectEditor} called
 * when {@link FilledCircle} is being edited.
 * 
 * @author Patrik Okanovic
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * The {@link FilledCircle}
	 */
	private FilledCircle filledCircle;
	
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
	 * The {@link JColorChooser} for foreground color
	 */
	private JColorArea fgColorChooser;
	
	/**
	 * The {@link JColorChooser} for background color
	 */
	private JColorArea bgColorChooser;
	
	/**
	 * Height of the text field
	 */
	private static final int HEIGHT = 2;
	/**
	 * Width of the text field
	 */
	private static final int WIDTH = 20;
	
	/**
	 * Constructor of the class
	 * 
	 * @param filledCircle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		
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
		sx.setText(filledCircle.getCenter().x+"");
		first.add(sx);
		add(first);
		
		// second row
		JPanel second = new JPanel();
		second.add(new JLabel("Center y:"));
		sy.setSize(WIDTH, HEIGHT);
		sy.setText(filledCircle.getCenter().y + "");
		second.add(sy);
		add(second);
		
		// third row
		JPanel third = new JPanel();
		third.add(new JLabel("Radius:"));
		radius.setSize(WIDTH, HEIGHT);
		radius.setText(filledCircle.getRadius() + "");
		third.add(radius);
		add(third);
		
		// fourth row
		JPanel fourth = new JPanel();
		fourth.add(new JLabel("Foreground color:"));
		fgColorChooser = new JColorArea(filledCircle.getFgColor());
		fourth.add(fgColorChooser);
		add(fourth);
		

		// fifth row
		JPanel fifth = new JPanel();
		fifth.add(new JLabel("Background color:"));
		bgColorChooser = new JColorArea(filledCircle.getBgColor());
		fifth.add(bgColorChooser);
		add(fifth);
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
		filledCircle.setCenter(new Point(SX,SY));
		filledCircle.setRadius(R);
		filledCircle.setFgColor(fgColorChooser.getCurrentColor());
		filledCircle.setBgColor(bgColorChooser.getCurrentColor());

	}
	
}
