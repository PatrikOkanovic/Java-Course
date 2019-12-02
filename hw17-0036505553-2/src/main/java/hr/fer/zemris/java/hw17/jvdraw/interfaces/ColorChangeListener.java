package hr.fer.zemris.java.hw17.jvdraw.interfaces;

import java.awt.Color;

/**
 * Interface used to implement Observer Pattern.
 * Represents a listener for every time a color has been changed.
 * 
 * @author Patrik Okanovic
 *
 */
public interface ColorChangeListener {

	/**
	 * Called each time a color has been changed
	 * 
	 * @param source {@link IColorProvider}
	 * @param oldColor 
	 * @param newColor
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
