package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;


import java.awt.Point;
import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw17.jvdraw.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.visitors.SaveVisitor;
/**
 * Extends {@link GeometricalObject}.
 * Represents a filled circle. Filled circle is defined by its center,
 * radius, line color and filling color.
 * 
 * @author Patrik Okanovic
 *
 */
public class FilledCircle extends GeometricalObject {
	
	/**
	 * The center
	 */
	private Point center;
	
	/**
	 * The radius
	 */
	private int radius;
	
	/**
	 * The filling color
	 */
	private Color bgColor;
	
	/**
	 * The line color
	 */
	private Color fgColor;
	

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
		return new FilledCircleEditor(this);
	}

	/**
	 * Constructor of the class.
	 * 
	 * @param center
	 * @param radius
	 * @param bgColor
	 * @param fgColor
	 */
	public FilledCircle(Point center, int radius, Color bgColor, Color fgColor) {
		super();
		this.center = center;
		this.radius = radius;
		this.bgColor = bgColor;
		this.fgColor = fgColor;
	}

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
	 * @return the fillColor
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * @param fillColor the fillColor to set
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * @return the lineColor
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * @param lineColor the lineColor to set
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Filled circle (");
		sb.append(center.x + ","+center.y+"),");
		sb.append(radius+",");
		sb.append(String.format("#%02x%02x%02x", bgColor.getRed(),bgColor.getGreen(),bgColor.getBlue()));
		
		
		return sb.toString();
	}

}
