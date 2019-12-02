package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Extends {@link AbstractListModel} so that it could store {@link GeometricalObject} in a list and
 * show it in our main program {@link JVDraw}.
 * Implements {@link DrawingModelListener} to track all {@link GeometricalObject} in the list model.
 * 
 * @author Patrik Okanovic
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener{


	private static final long serialVersionUID = 1L;
	
	/**
	 * Used as an adapter
	 */
	private DrawingModel model;
	
	

	/**
	 * Constructor of the class
	 * 
	 * @param model {@link DrawingModel}
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		this.model.addDrawingModelListener(this);
	}
	
	

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}



	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
		
	}



	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
		
	}



	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
		
	}


}
