package hr.fer.zemris.java.hw17.jvdraw.states;

import java.awt.Graphics2D;

import java.awt.Point;
import java.awt.event.MouseEvent;


import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
/**
 * Implements {@link Tool} represents the State design pattern.
 * When {@link JVDraw} is in the state of drawing {@link Line}
 * 
 * @author Patrik Okanovic
 */
public class LineTool implements Tool{
	/**
	 * The start {@link Point}
	 */
	private Point startPoint;
	
	/**
	 * The end {@link Point}
	 */
	private Point endPoint;
	
	/**
	 * The current point while line is following the mouse
	 */
	private Point currentPoint;
	
	/**
	 * The {@link IColorProvider}
	 */
	private IColorProvider fgColor;

	/**
	 * The {@link DrawingModel}
	 */
	private DrawingModel model;
	
	public LineTool(DrawingModel model, IColorProvider fgColor) {
		this.model = model;
		this.fgColor = fgColor;

	}

	@Override
	public void mousePressed(MouseEvent e) {
				
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(startPoint == null) {
			startPoint = e.getPoint();
		} else {
			endPoint = e.getPoint();
			Line newLine = new Line(startPoint, endPoint, fgColor.getCurrentColor());
			model.add(newLine);
			startPoint = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentPoint = e.getPoint();
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(startPoint != null) {
			GeometricalObjectPainter visitor = new GeometricalObjectPainter(g2d);
			Line currentLine = new Line(startPoint, currentPoint, fgColor.getCurrentColor());
			currentLine.accept(visitor);
		}
		
	}

}
