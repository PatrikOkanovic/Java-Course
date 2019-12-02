package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
/**
 * Implementation of {@link GeometricalObjectVisitor},
 * used to calculate the minimum box of the current image.
 * Used for exporting files.
 * 
 * @author Patrik Okanovic
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor{

	/**
	 * Minimum x
	 */
	private Integer minX;
	
	/**
	 * Maximum x
	 */
	private Integer maxX;
	
	/**
	 * Minimum y
	 */
	private Integer minY;
	
	/**
	 * Maximum y
	 */
	private Integer maxY;
	
	/**
	 * Returns the {@link Rectangle} of the current drawing
	 * 
	 * @return {@link Rectangle}
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
	
	@Override
	public void visit(Line line) {
		int x1 = line.getStartPoint().x;
		int x2 = line.getEndPoint().x;
		int y1 = line.getStartPoint().y;
		int y2 = line.getEndPoint().y;
		
		testAllLimits(x1, x2, y1, y2);
		
				
	}

	

	@Override
	public void visit(Circle circle) {
		int x1 = circle.getCenter().x - circle.getRadius();
		int x2 = circle.getCenter().x + circle.getRadius();
		int y1 = circle.getCenter().y - circle.getRadius();
		int y2 = circle.getCenter().y + circle.getRadius();
		
		testAllLimits(x1, x2, y1, y2);		
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int x1 = filledCircle.getCenter().x - filledCircle.getRadius();
		int x2 = filledCircle.getCenter().x + filledCircle.getRadius();
		int y1 = filledCircle.getCenter().y - filledCircle.getRadius();
		int y2 = filledCircle.getCenter().y + filledCircle.getRadius();
		
		testAllLimits(x1, x2, y1, y2);	
		
	}
	
	/**
	 * Used to test the four points of the {@link Line}, {@link Circle} and {@link FilledCircle}
	 * for finding minimums and maximums.
	 * 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 */
	private void testAllLimits(int x1, int x2, int y1, int y2) {
		if(minX == null || maxX == null) {
			initializeX(x1);
		}
		if(minY == null || maxY == null) {
			initializeY(y1);
		}
		
		swapForMinX(x1);
		swapForMaxX(x1);
		
		swapForMinX(x2);
		swapForMaxX(x2);
		
		swapForMinY(y1);
		swapForMaxY(y1);
		
		swapForMinY(y2);
		swapForMaxY(y2);
	}
	
	/**
	 * Checks for maximum y, and saves if new maximum is found
	 * 
	 * @param y
	 */
	private void swapForMaxY(int y) {
		if(y > maxY) {
			maxY = y;
		}
		
	}

	/**
	 * Checks for minimum y, and saves if new minimum is found
	 * 
	 * @param y
	 */
	private void swapForMinY(int y) {
		if(y < minY) {
			minY = y;
		}
		
	}

	/**
	 * Checks for maximum x, and saves if new maximum is found
	 * 
	 * @param x
	 */
	private void swapForMaxX(int x) {
		if(x > maxX) {
			maxX = x;
		}
		
	}

	/**
	 * Checks for minimum x, and saves if new minimum is found
	 * 
	 * @param x
	 */
	private void swapForMinX(int x) {
		if(x < minX) {
			minX = x;
		}
		
	}

	/**
	 * Initialize the minY and maxY based on the first visit.
	 * 
	 * @param y1
	 */
	private void initializeY(int y) {
		minY = y;
		maxY = y;
		
	}
	
	/**
	 * Initialize the minX and maxX based on the first visit.
	 * @param x
	 */
	private void initializeX(int x) {
		minX = x;
		maxX = x;
		
	}

}
