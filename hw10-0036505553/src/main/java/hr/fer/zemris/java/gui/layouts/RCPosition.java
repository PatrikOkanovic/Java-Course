package hr.fer.zemris.java.gui.layouts;

/**
 * Encapsulates two read-only variables. The row and the column. Used 
 * within the {@link CalcLayout}
 * 
 * @author Patrik Okanovic
 *
 */
public class RCPosition {

	/**
	 * The ordinal number of the row
	 */
	private int row;
	/**
	 * The ordinal number of the column.
	 */
	private int column;
	
	/**
	 * Returns the row
	 * 
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Constructor of the class.
	 * 
	 * @param row index of the row
	 * @param column index of the column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the column.
	 * 
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	
}
