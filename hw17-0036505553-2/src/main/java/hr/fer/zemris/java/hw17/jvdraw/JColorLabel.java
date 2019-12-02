package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;


import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.IColorProvider;

/**
 * Extends  a {@link JLabel}, used to display the current selected foreground and background color
 * in the {@link JVDraw}
 * 
 * @author Patrik Okanovic
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener{

	private static final long serialVersionUID = 1L;

	/**
	 * {@link IColorProvider} for foreground color
	 */
	private IColorProvider fgColor;
	
	/**
	 * {@link IColorProvider} for background color
	 */
	private IColorProvider bgColor;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param fgColor
	 * @param bgColor
	 */
	public JColorLabel(IColorProvider fgColor, IColorProvider bgColor) {
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		
		this.fgColor.addColorChangeListener(this);
		this.bgColor.addColorChangeListener(this);
		
		setText("Foreground colour: " + fgColor.toString() + 
				"   Background color: " + bgColor.toString());
	}
	
	
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setText("Foreground colour: " + fgColor.toString() + 
				"Background color: " + bgColor.toString());
		
	}

}
