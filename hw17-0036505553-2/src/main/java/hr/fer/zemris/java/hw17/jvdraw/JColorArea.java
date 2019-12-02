package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.interfaces.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.interfaces.IColorProvider;

/**
 * Extends {@link JColorArea} represents a component used to choose the current color.
 * Implements {@link IColorProvider} so that it could return information about the
 * current color.
 * 
 * @author Patrik Okanovic
 *
 */
public class JColorArea extends JComponent implements IColorProvider{

	private static final long serialVersionUID = 1L;
	
	/**
	 * The selected color.
	 */
	private Color selectedColor;
	
	/**
	 * List of {@link ColorChangeListener}.
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor of the class.
	 * 
	 * @param selectedColor
	 */
	public JColorArea(Color selectedColor) {
		super();
		this.selectedColor = selectedColor;
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, 
								"Choose your color", JColorArea.this.selectedColor);
				if(newColor == null) {
					return;
				}
				Color old = getCurrentColor();
				setSelectedColor(newColor);
				repaint();
				listeners.forEach(l -> l.newColorSelected(JColorArea.this, old, newColor));
				
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/**
	 * @return the selectedColor
	 */
	public Color getSelectedColor() {
		return selectedColor;
	}

	/**
	 * @param selectedColor the selectedColor to set
	 */
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if(! listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		if(listeners.contains(l)) {
			listeners.remove(l);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Dimension dim = getPreferredSize();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selectedColor);
		g2d.fillRect(0, 0, dim.width, dim.height);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("("+selectedColor.getRed() + ", ");
		sb.append(selectedColor.getGreen() + ", ");
		sb.append(selectedColor.getBlue()+")");
		return sb.toString();
	
	}

}
