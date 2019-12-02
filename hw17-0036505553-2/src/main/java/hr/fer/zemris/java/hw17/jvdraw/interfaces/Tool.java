package hr.fer.zemris.java.hw17.jvdraw.interfaces;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
/**
 * Interface representing State Pattern. Used for drawing and creating {@link GeometricalObject}
 * depending on the current selected tool
 * in the {@link JVDraw}
 * 
 * @author Patrik Okanovic
 *
 */
public interface Tool {

	/**
	 * Implements action when mouse is being pressed
	 * 
	 * @param e {@link MouseEvent}
	 */
	public void mousePressed(MouseEvent e);
	/**
	 * Implements action when mouse is being released
	 * 
	 * @param e {@link MouseEvent}
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Implements action when mouse is clicked
	 * 
	 * @param e {@link MouseEvent}
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Implements action when mouse is being moved
	 * 
	 * @param e {@link MouseEvent}
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Implements action when mouse is being dragged
	 * 
	 * @param e {@link MouseEvent}
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * Paints the current state.
	 * 
	 * @param g2d {@link Graphics2D}
	 */
	public void paint(Graphics2D g2d);
}
