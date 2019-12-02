package hr.fer.zemris.java.gui.charts;

import java.util.List;
/**
 * This class encapsulates data for representing a bar chart
 * 
 * @author Patrik Okanovic
 *
 */
public class BarChart {

	/**
	 * Storage for {@link XYValue}
	 */
	private List<XYValue> values;
	
	/**
	 * Title of x axis
	 */
	private String xDescription;
	/**
	 * Title of y axis
	 */
	private String yDescription;
	/**
	 * Minimum y value
	 */
	private int minY;
	/**
	 * Maximum y value
	 */
	private int maxY;
	/**
	 * Difference between two y values
	 */
	private int deltaY;

	/**
	 * Constructor of the class.
	 * MinY cannot be smaller than 0, must be smaller than maxY.
	 * 
	 * @param values list of {@link XYValue}
	 * @param xDescription Title of x axis
	 * @param yDescription Title of y axis
	 * @param minY Minimum y value
	 * @param maxY Maximum y value
	 * @param deltaY Difference between two y values
	 * @throws IllegalArgumentException if minY is smaller than 0, and minY is not smaller than maxY
	 * 									and if some of the y values from the list is smaller than minY
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int deltaY) {
		super();
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		
		for(XYValue value : values) {
			if(!(minY <= value.getY())) {
				throw new IllegalArgumentException("Y value cannot be smaller than min y.");
			}
		}
		
		if(minY < 0) {
			throw new IllegalArgumentException("Min y cannot be negative.");
		}
		
		if(!(minY < maxY)) {
			throw new IllegalArgumentException("Max y must be bigger than min y.");
		}
		this.minY = minY;
		this.maxY = maxY;
		this.deltaY = deltaY;
	}

	/**
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return the xDescription
	 */
	public String getXDescription() {
		return xDescription;
	}

	/**
	 * @return the yDescription
	 */
	public String getYDescription() {
		return yDescription;
	}

	/**
	 * @return the minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @return the deltaY
	 */
	public int getDeltaY() {
		return deltaY;
	}

	
	
	
}
