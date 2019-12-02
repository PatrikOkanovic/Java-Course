package hr.fer.zemris.java.hw17.jvdraw.interfaces;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
/**
 * Represents model for drawing objects and sort of a collection.
 * In DrawingModel, graphical objects have its defined position and it is expected that the image rendering will
 * be created by drawing objects from first-one to last-one.
 * The drawing model has the responsibility to track drawing modification status. When any geometrical
 * object is added or removed from the model, or is changed
 * 
 * @author Patrik Okanovic
 *
 */
public interface DrawingModel {

	/**
	 * Returns the number of {@link GeometricalObject} in the model
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Returns {@link GeometricalObject} at the given index.
	 * 
	 * @param index
	 * @return
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the {@link GeometricalObject} to the model.
	 * 
	 * @param object
	 */
	public void add(GeometricalObject object);

	/**
	 * removes the {@link GeometricalObject} from the model
	 * 
	 * @param object
	 */
	public void remove(GeometricalObject object);

	/**
	 * Changes order of the object for the given offset
	 * 
	 * @param object
	 * @param offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns the index of the object.
	 * 
	 * @param object {@link GeometricalObject}
	 * @return
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clears the model.
	 */
	public void clear();

	/**
	 * Only way to set the modified flag false again
	 */
	public void clearModifiedFlag();

	/**
	 * Returns true if model has been modified
	 * 
	 * @return false if the model has not been modified
	 */
	public boolean isModified();

	/**
	 * Adds the {@link DrawingModelListener}
	 * 
	 * @param l {@link DrawingModelListener}
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the {@link DrawingModelListener}
	 * 
	 * @param l {@link DrawingModelListener}
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
