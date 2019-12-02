package hr.fer.zemris.java.hw17.jvdraw.editor;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
/**
 * Abstract editor of the {@link GeometricalObject}
 * 
 * @author Patrik OKanovic
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Checks the editing of the {@link GeometricalObject}, every measure must be integer
	 */
	public abstract void checkEditing();

	/**
	 * Accepts the editing and changes the {@link GeometricalObject}
	 */
	public abstract void acceptEditing();

}
