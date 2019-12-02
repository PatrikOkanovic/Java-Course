package hr.fer.zemris.java.hw17.jvdraw.interfaces;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
/**
 * Interface used for implementing Observer Pattern, represents
 * a listener for {@link GeometricalObject}
 * 
 * @author Patrik Okanovic
 *
 */
public interface GeometricalObjectListener {

	/**
	 * Called each time a {@link GeometricalObject} has been changed.
	 * 
	 * @param o {@link GeometricalObject}
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
