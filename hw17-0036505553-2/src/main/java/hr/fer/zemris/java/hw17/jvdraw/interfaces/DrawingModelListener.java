package hr.fer.zemris.java.hw17.jvdraw.interfaces;
/**
 * Interface used for implementing Observer Pattern, represents
 * a listener for {@link DrawingModel}.
 * 
 * @author Patrik Okanovic
 *
 */
public interface DrawingModelListener {

	/**
	 * Called each time an object is added to {@link DrawingModel}
	 * 
	 * @param source {@link DrawingModel}
	 * @param index0
	 * @param index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called each time an object is removed from {@link DrawingModel}
	 * 
	 * @param source {@link DrawingModel}
	 * @param index0
	 * @param index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called each time an object is changed in {@link DrawingModel}
	 * 
	 * @param source {@link DrawingModel}
	 * @param index0
	 * @param index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
