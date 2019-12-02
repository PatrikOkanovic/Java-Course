package hr.fer.zemris.java.gui.charts;
/**
 * Class encapsulating two values, x and y.
 * Both are integer values.
 * 
 * @author Patrik Okanovic
 *
 */
public class XYValue {

	/**
	 * X value
	 */
	private int x;
	/**
	 * Y value
	 */
	private int y;

	/**
	 * Constructor of the class.
	 * 
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	
}
