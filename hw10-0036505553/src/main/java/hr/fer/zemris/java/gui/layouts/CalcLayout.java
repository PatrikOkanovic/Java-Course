package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Implements {@link LayoutManager2}.
 * Used as a layout for the calculator written in the homework.
 * Contains a maximum of 31 elements.
 * 
 * @author Patrik Okanovic
 *
 */
public class CalcLayout implements LayoutManager2{
	/**
	 * Number of rows
	 */
	private static final int NUM_OF_ROWS = 5;
	/**
	 * Number of columns
	 */
	private static final int NUM_OF_COLUMNS = 7;

	/**
	 * Gap between components.
	 */
	private int gap;
	
	/**
	 * Storage of the current components
	 */
	private Component [][] components;
	
	
	/**
	 * Constructor of the class.
	 * 
	 * @param gap between each component
	 */
	public CalcLayout(int gap) {
		if(gap < 0) {
			throw new CalcLayoutException("Gap between rows and columns cannot be less than zero.");
		}
		
		this.gap = gap;
		components = new Component[NUM_OF_ROWS][NUM_OF_COLUMNS];
	}
	
	/**
	 * Constructor of the class with the gap 0.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Method addLayoutComponent(String name, Component comp)"
				+ "must not be called.");
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for(int i = 0; i < NUM_OF_ROWS; i++) {
			for(int j = 0; j < NUM_OF_COLUMNS; j++) {
				if(components[i][j] == null) {
					continue;
				}
				if(components[i][j].equals(comp)) {
					components[i][j] = null;
					return;
				}
			}
		}
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateSize(parent, Component::getMinimumSize);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		
		int totalWidth = parent.getWidth() - insets.left - insets.right;
		int totalHeight = parent.getHeight() - insets.top - insets.bottom;
		
		int height = (int)Math.ceil((totalHeight - gap*(NUM_OF_ROWS-1)) / (double)NUM_OF_ROWS);// - gap);
		int width = (int) Math.ceil((totalWidth - gap*(NUM_OF_COLUMNS-1)) / (double)NUM_OF_COLUMNS);// - gap);
		
		int height2 = height;
		int width2 = width;
		
		int switchingConstantWidth =-1, switchingConstantHeight = -1;
		boolean switchingHeight = false;
		boolean switchingWidth = false;
		
		if(totalHeight % NUM_OF_ROWS != 0) {
			switchingHeight = true;
		}
		
		if(totalWidth % NUM_OF_COLUMNS != 0) {
			switchingWidth = true;
		}
		
		for(int i = 0; i < NUM_OF_ROWS; i++) {
			for(int j = 0; j < NUM_OF_COLUMNS; j++) {
				if(i == 0 && (j > 0  && j < 5)) {
					continue;
				}
				if(components[i][j] == null) {
					continue;
				}
				
				int x = insets.left + j*width2 + j*gap;
				int y = insets.top + i*height2 + i*gap;
				if(i == 0 && j == 0) {
					components[i][j].setBounds(x, y, width * 5 + 4*gap, height);
				}
				else {
//					if(i == NUM_OF_ROWS - 1 || j == NUM_OF_COLUMNS -1 ) {
//						if(i == NUM_OF_ROWS - 1) {
//							components[i][j].setBounds(x, y, width, height+gap);
//						} else if(j == NUM_OF_COLUMNS - 1) {
//							components[i][j].setBounds(x, y, width+gap, height);
//						} else {
//							components[i][j].setBounds(x, y, width+gap, height+gap);
//						}
//						
//					} else {
						components[i][j].setBounds(x, y, width, height);
//					}
					
				}
				if(switchingHeight) {
					height += switchingConstantHeight;
					switchingConstantHeight *= -1;
				}
				if(switchingWidth) {
					width += switchingConstantWidth;
					switchingConstantWidth *= -1;
				}
			}
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		if(checkInvalidConstraints(constraints)) {
			throw new CalcLayoutException("Invalid constraints.");
		}
		
		if(constraints instanceof String) {
			String[] data = ((String)constraints).split(",");
			constraints = new RCPosition(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
		}
		
		RCPosition position = (RCPosition) constraints;
		int row = position.getRow();
		int column = position.getColumn();
		
		
		components[row-1][column-1] = comp;
		
	}

	/**
	 * Checks if the given constraints is valid. Row can be between 1 and 5, column can be
	 * be 1 to 7. If row is one column can be 1, 6 or 7. Cannot add the component to the already
	 * existing place
	 * 
	 * @param constraints {@link RCPosition} or {@link String}
	 * @return true if invalid
	 */
	private boolean checkInvalidConstraints(Object constraints) {
		if(!((constraints instanceof RCPosition) || (constraints instanceof String))) {
			return true;
		}
		
		if(constraints instanceof String) {
			try {
				String[] data = ((String)constraints).split(",");
				constraints = new RCPosition(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
				
			} catch(NumberFormatException exc) {
				return true;
			}
		}
		
		RCPosition position = (RCPosition) constraints;
		int row = position.getRow();
		int column = position.getColumn();
		
		if(row < 1 || row > 5 || column < 1 || column > 7) {
			return true;
		}
		if(row == 1 && (column > 1 && column < 6)) {
			return true;
		}
		
		if(components[row-1][column-1] != null) {
			return true;
		}
		
		
		return false;
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateSize(target, Component::getMaximumSize);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		
	}
	
	/**
	 * Calculates the minimum, maximum or preferred size of the components in thContainer parent.
	 * 
	 * @param parent Container 
	 * @param getter gets the maximum, minimum or preferred size
	 * @return maximum of the wanted size to be calculated
	 */
	private Dimension calculateSize(Container parent, DimensionGetter getter) {
		int width = 0;
		int height = 0;
		
		
		for(int i = 0; i < NUM_OF_ROWS; i++) {
			for(int j = 0 ; j < NUM_OF_COLUMNS; j++) {
				if(components[i][j] == null) {
					continue;
				}
				if(getter.getDimension(components[i][j]) == null) {
					continue;
				}
				if(i == 0 && j == 0) {
					width = (int) Math.max(width, (getter.getDimension(components[i][j]).getWidth() - 4*gap)/5);
				} else {
					width = (int) Math.max(width, getter.getDimension(components[i][j]).getWidth());
				}
				height = (int) Math.max(height, getter.getDimension(components[i][j]).getHeight());
			}
		}
		Insets insets = parent.getInsets();
		
		return new Dimension(width * NUM_OF_COLUMNS + gap * (NUM_OF_COLUMNS-1) + insets.left + insets.right
							, height * NUM_OF_ROWS + gap * (NUM_OF_ROWS-1) + insets.top + insets.bottom);
		
	}
	
	/**
	 * Interface used for getting minimum, maximum or preferred size.
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	public interface DimensionGetter {
		
		/**
		 * Gets the minimum, maximum or preferred dimension.
		 * 
		 * @param comp which Dimension is returned
		 * @return dimension
		 */
		public Dimension getDimension(Component comp);
		
	}

}
