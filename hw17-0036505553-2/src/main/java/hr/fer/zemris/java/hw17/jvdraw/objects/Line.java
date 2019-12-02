package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.SaveVisitor;

/**
 * Extends {@link GeometricalObject}.
 * Represents a line. Line is defined by its starting and ending point, and its color.
 * 
 * @author Patrik Okanovic
 *
 */
public class Line extends GeometricalObject {

	/**
	 * The starting point
	 */
	private Point startPoint;
	
	/**
	 * The ending point
	 */
	private Point endPoint;
	
	/**
	 * Color of the line
	 */
	private Color color;
	
	
	
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
		return new LineEditor(this);
	}

	/**
	 * Constructor of the class.
	 * 
	 * @param startPoint
	 * @param endPoint
	 * @param color
	 */
	public Line(Point startPoint, Point endPoint, Color color) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}

	/**
	 * @return the startPoint
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * @return the endPoint
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Line ");
		sb.append("("+startPoint.x+","+startPoint.y+")-");
		sb.append("("+endPoint.x+","+endPoint.y+")");
		
		return sb.toString();
	}

	
}
