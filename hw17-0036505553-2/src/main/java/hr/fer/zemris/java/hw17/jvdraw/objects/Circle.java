package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;


import java.awt.Point;
import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw17.jvdraw.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.SaveVisitor;
/**
 * Extends {@link GeometricalObject}.
 * Represents an empty circle. Circle is defined by its center,
 * radius and line color.
 * 
 * @author Patrik Okanovic
 *
 */
public class Circle extends GeometricalObject {
	/**
	 * The center
	 */
	private Point center;
	
	/**
	 * The line color
	 */
	private Color color;
	
	/**
	 * The radius
	 */
	private int radius;
	
	
	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Constructor of the class.
	 * 
	 * @param center
	 * @param color
	 * @param radius
	 */
	public Circle(Point center, Color color, int radius) {
		super();
		this.center = center;
		this.color = color;
		this.radius = radius;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public void acceptSaveVisitor(SaveVisitor v, Path path) throws IOException {
		v.visit(this, path);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Circle (");
		sb.append(center.x + ","+center.y+"),");
		sb.append(radius);
		
		return sb.toString();
	}

	

	

}
