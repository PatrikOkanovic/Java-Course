package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Represents an implementation of the 2D vector and basic operations on it.
 * @author Patrik Okanovic
 *
 */
public class Vector2D {

	private static final double DIFFERENCE = 10E-8;
	/**
	 * X component
	 */
	private double x;
	/**
	 * Y component
	 */
	private double y;
	/**
	 * Creates an instance of 2D Vector
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Returns the x
	 * @return
	 */
	public double getX() {
		return x;
	}
	/**
	 * Return the y
	 * @return
	 */
	public double getY() {
		return y;
	}
	/**
	 * Translates the current vector with the given offset
	 * @param offset
	 * @throws NullPointerException
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);
		x = x + offset.getX();
		y = y + offset.getY();
	}
	/**
	 * Return the new translated vector the current is not changed
	 * @param offset
	 * @return {@link Vector2D}
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}
	/**
	 * Rotates the current vector with the given angle
	 * @param angle
	 */
	public void rotate(double angle) {
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double currentAngle = Math.atan(y/x);
		if((x < 0 && y > 0) || (x < 0 && y < 0)) {
			currentAngle += Math.PI;
		}
		if(currentAngle < 0) {
			currentAngle += Math.PI * 2;
		}
		currentAngle += angle;
		
		x = magnitude * Math.cos(currentAngle);
		y = magnitude * Math.sin(currentAngle);
	}
	
	/**
	 * Return the new rotated vector the current is not changed
	 * @param offset
	 * @return {@link Vector2D}
	 */
	public Vector2D rotated(double angle) {
		Vector2D returnVector = copy();
		returnVector.rotate(angle);
		return returnVector;
	}
	
	/**
	 * Scales the current vector.
	 * @param scaler
	 */
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;
	}
	/**
	 * Return the new scaled vector the current is not changed
	 * @param offset
	 * @return {@link Vector2D}
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}
	/**
	 * Return the copy of the current vector.
	 * @return
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		return (x-other.getX()) < DIFFERENCE && (y-other.getY()) < DIFFERENCE;
	}

}
