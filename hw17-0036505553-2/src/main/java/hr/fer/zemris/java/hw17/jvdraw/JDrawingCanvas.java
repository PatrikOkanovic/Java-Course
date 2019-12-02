package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.Tool;

import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
/**
 * A {@link JComponent} used as a canvas to draw on it and display all {@link GeometricalObjectPainter}
 * from the {@link DrawingModel}.
 * Implements {@link DrawingModelListener} to establish Observer pattern.
 * 
 * @author Patrik Okanovic
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener  {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The {@link DrawingModel}
	 */
	private DrawingModel model;
	
	/**
	 * The {@link GeometricalObjectPainter}
	 */
	private GeometricalObjectPainter visitor;

	/**
	 * {@link Supplier} of the current tool selected in {@link JVDraw}
	 */
	private Supplier<Tool> supplier;
	
	/**
	 * Constructor of the class.
	 * "this" is listener on the model.
	 * Implements  {@link MouseMotionListener} using supplier and {@link MouseListener}
	 * 
	 * @param model
	 * @param supplier
	 */
	public JDrawingCanvas(DrawingModel model, Supplier<Tool> supplier) {
		this.model = model;
		this.supplier = supplier;
		
		model.addDrawingModelListener(this);
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				JDrawingCanvas.this.supplier.get().mouseMoved(e);
				repaint();
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				JDrawingCanvas.this.supplier.get().mouseDragged(e);
				repaint();
			}
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				JDrawingCanvas.this.supplier.get().mouseReleased(e);
				repaint();
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				JDrawingCanvas.this.supplier.get().mousePressed(e);
				repaint();
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// not implementing this
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// not implementing this
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JDrawingCanvas.this.supplier.get().mouseClicked(e);
				repaint();
			}
		});
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		visitor = new GeometricalObjectPainter(g2d);
		
		for(int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(visitor);
		}
		
		supplier.get().paint(g2d);
	}
	


}
