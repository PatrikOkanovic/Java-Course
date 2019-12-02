package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
/**
 * Implementation of {@link DrawingModel}, represents
 * a collection for {@link GeometricalObject}, also implements {@link GeometricalObjectListener}
 * so that it could track changes on the {@link GeometricalObject} stored inside
 * 
 * @author Patrik Okanovic
 *
 */
public class DrawingModelImplementation implements DrawingModel, GeometricalObjectListener{

	/**
	 * List of {@link GeometricalObject}
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	
	/**
	 * List of {@link DrawingModelListener}
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	/**
	 * Used to check if model has been modified
	 */
	private boolean modified = false;
	
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);
		listeners.forEach(l -> l.objectsAdded(this, objects.size()-1, objects.size()));
		modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		objects.remove(object);
		object.removeGeometricalObjectListener(this);
		listeners.forEach(l -> l.objectsRemoved(this, 0, objects.size()));
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int oldIndex = indexOf(object);
		int newIndex = (oldIndex + offset) % objects.size();
		if(newIndex < 0) {
			newIndex += objects.size();
		}
		Collections.swap(objects, oldIndex, newIndex);
		listeners.forEach(l -> l.objectsChanged(this, indexOf(object), indexOf(object) - offset));
		modified = true;
		
		
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		Iterator<GeometricalObject> iter = objects.iterator();
		while(iter.hasNext()) {
			GeometricalObject object = iter.next();
			iter.remove();
			object.removeGeometricalObjectListener(this);
			listeners.forEach(l -> l.objectsRemoved(this, 0, objects.size()));
		}
		
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
		
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(! listeners.contains(l)) {
			listeners.add(l);
		}
		
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		if(listeners.contains(l)) {
			listeners.remove(l);
		}
		
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		listeners.forEach(l -> l.objectsChanged(this, indexOf(o), indexOf(o)));
	}

}
