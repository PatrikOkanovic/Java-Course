package hr.fer.zemris.java.hw17.jvdraw.interfaces;

import java.awt.Color;

/**
 * Interface used for getting information about current color and changes with the color.
 * 
 * @author Patrik Okanovic
 *
 */
public interface IColorProvider {

	/**
	 * Returns the current color
	 * 
	 * @return {@link Color}
	 */
	public Color getCurrentColor();

	/**
	 * Used for implementing observer pattern, adds the {@link ColorChangeListener}
	 * 
	 * @param l {@link ColorChangeListener}
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the {@link ColorChangeListener}
	 * 
	 * @param l {@link ColorChangeListener}
	 */
	public void removeColorChangeListener(ColorChangeListener l);

}
