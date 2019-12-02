package hr.fer.zemris.java.hw17.jvdraw.interfaces;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
/**
 * Interface used for implementing Visitor Pattern, offers method visit
 * for each {@link GeometricalObject}
 * 
 * @author Patrik Okanovic
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Visit the {@link Line}
	 * 
	 * @param line
	 */
	public abstract void visit(Line line);

	/**
	 * Visit the {@link Circle}
	 * 
	 * @param circle
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visit the {@link FilledCircle}
	 * 
	 * @param filledCircle
	 */
	public abstract void visit(FilledCircle filledCircle);
}
