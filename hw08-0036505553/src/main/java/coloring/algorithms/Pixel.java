package coloring.algorithms;

import java.util.Objects;

import marcupic.opjj.statespace.coloring.Picture;
/**
 * Represents a pixel in {@link Picture} with its coordinates
 * 
 * @author Patrik Okanovic
 *
 */
public class Pixel {

	/**
	 * X coordinate
	 */
	public int x;
	/**
	 * Y coordinate
	 */
	public int y;
	/**
	 * Constructor of the class
	 * 
	 * @param x
	 * @param y
	 */
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
}
