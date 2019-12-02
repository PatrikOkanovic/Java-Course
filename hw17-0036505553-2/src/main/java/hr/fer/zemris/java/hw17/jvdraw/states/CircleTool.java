package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;

import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
/**
 * Implements {@link Tool} represents the State design pattern.
 * When {@link JVDraw} is in the state of drawing {@link Circle}
 * 
 * @author Patrik Okanovic
 *
 */
public class CircleTool implements Tool{
	
	/**
	 * The center
	 */
	private Point center;
	
	/**
	 * The {@link IColorProvider}
	 */
	private IColorProvider fgColor;
	
	/**
	 * The current radius when object is following the mouse
	 */
	private int currentRadius;
	
	/**
	 * Radius when {@link Circle} is added to the {@link DrawingModel}
	 */
	private int radius;
	
	/**
	 * The {@link DrawingModel}
	 */
	private DrawingModel model;
	
	/**
	 * Constructor of the class
	 * 
	 * @param model
	 * @param fgColor
	 */
	public CircleTool(DrawingModel model, IColorProvider fgColor) {
		this.model = model;
		this.fgColor = fgColor;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	/**
	 * Calculates the current radius based on two {@link Point}
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	private int calculateDistance(Point point1, Point point2) {
		if(point1 == null || point2 == null) {
			return 0;
		}
		int distance = (int) Math.sqrt(Math.pow(point1.x-point2.x, 2) + 
									   Math.pow(point1.y-point2.y, 2));
		return distance;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(center == null) {
			center = e.getPoint();
		} else {
			radius = calculateDistance(center, e.getPoint());
			Circle circle = new Circle(center, fgColor.getCurrentColor(), radius);
			model.add(circle);
			center = null;
			currentRadius = 0;
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentRadius = calculateDistance(center, e.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void paint(Graphics2D g2d) {
		if(center != null) {
			Circle currentCircle = new Circle(center,fgColor.getCurrentColor(), currentRadius);
			GeometricalObjectPainter visitor = new GeometricalObjectPainter(g2d);
			currentCircle.accept(visitor);
		}
		
	}

}
