package hr.fer.zemris.java.hw17.jvdraw.objects;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.SaveVisitor;
/**
 * An abstract geometrical object, which in our scenario can be {@link Line},
 * {@link Circle} or {@link FilledCircle}
 * 
 * @author Patrik Okanovic
 *
 */
public abstract class GeometricalObject {

	/**
	 * Method used for implementing Visitor Pattern.
	 * 
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Method used for implementing saving, used as a Visitor pattern with {@link SaveVisitor}
	 * 
	 * @param v {@link SaveVisitor}
	 * @param path
	 */
	public abstract void acceptSaveVisitor(SaveVisitor v, Path path) throws IOException;

	/**
	 * List of listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	
	/**
	 * Used to create {@link GeometricalObjectEditor}
	 * 
	 * @return {@link GeometricalObjectEditor}
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Used to add {@link GeometricalObjectListener}
	 * 
	 * @param l {@link GeometricalObjectListener}
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	/**
	 * Used to remove {@link GeometricalObjectListener}
	 * 
	 * @param l {@link GeometricalObjectListener}
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		if(listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	
}
