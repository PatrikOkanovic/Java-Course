package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;
/**
 * Used to move and colour in the space of Cartesian coordinates. X goes [0,1], and Y also [0,1]
 * point (0,0) is in the bottom left corner. 
 * @author Patrik Okanovic
 *
 */
public class TurtleState {

	/**
	 * Position of the turtle
	 */
	private Vector2D position;
	/**
	 * Direction of the turtle
	 */
	private Vector2D direction;
	/**
	 * Colour of the turtle
	 */
	private Color color;
	/**
	 * Effective shift of the turtle
	 */
	private double effectiveShift;
	/**
	 * Creates a turtle state.
	 * @param position
	 * @param direction
	 * @param color
	 * @param effectiveShift
	 * @throws NullPointerException if anything is null
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveShift ) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(direction);
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveShift = effectiveShift;
	}
	
	/**
	 * Creates a new instance of the turtlestate and returns it.
	 * @return
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), new Color(color.getRGB()), effectiveShift);
	}

	/**
	 * Gets the position.
	 * @return the position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 * @param position the position to set
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Gets the direction.
	 * @return the direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets the direction.
	 * @param direction the direction to set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Gets the color.
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the shift.
	 * @return the effectiveShift
	 */
	public double getEffectiveShift() {
		return effectiveShift;
	}

	/**
	 * Sets the shift
	 * @param effectiveShift the effectiveShift to set
	 */
	public void setEffectiveShift(double effectiveShift) {
		this.effectiveShift = effectiveShift;
	}
	
	
}
