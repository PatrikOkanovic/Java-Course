package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
/**
 * Extends {@link JComponent}. Draws a bar chart on its component based on the model
 * it gets from {@link BarChart}.
 * 
 * @author Patrik Okanović
 *
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constant used for drawing
	 */
	private static final int CONST_GAP = 10;
	
	/**
	 * {@link BarChart} with information
	 */
	private BarChart chart;
	/**
	 * Constructor of the class.
	 * 
	 * @param chart
	 */
	public BarChartComponent(BarChart chart) {
		Objects.requireNonNull(chart);
		this.chart = chart;
		this.setBorder(BorderFactory.createLineBorder(Color.white));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Insets ins = getInsets();
		Dimension dim = getSize();
		
		// setting dimensions
		FontMetrics fm = graphics.getFontMetrics();
		
		
		int stringHeight = fm.getAscent();
		
		int leftGap = fm.stringWidth(chart.getMaxY()+"") + 3*CONST_GAP + stringHeight; //not fixable, depends on the biggest number from data
		int bottomGap = stringHeight*2 + 3*CONST_GAP;
		
		int actualHeight = dim.height - ins.top - ins.bottom - bottomGap;
		
		int actualWidth = dim.width - ins.left-ins.right - leftGap;
		
		int numOfRows =(int) Math.ceil(((chart.getMaxY() - chart.getMinY()) / (double) chart.getDeltaY() )) ;
		int numOfColumns = chart.getValues().size();
		
		int columnWidth = actualWidth / numOfColumns;
		int rowHeight = actualHeight / numOfRows;
		
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, dim.width, dim.height);
		
		//create x title 
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Helvetica", Font.BOLD, 15));
		String xTitle = chart.getXDescription();
		graphics.drawString(xTitle, leftGap + actualWidth/2- fm.stringWidth(xTitle)/2, actualHeight + bottomGap - 5);
		
		//create y title
		Graphics2D graphics2 = (Graphics2D) graphics.create();
		graphics2.setFont(new Font("Helvetica", Font.BOLD, 15));
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		graphics2.setTransform(at);
		graphics2.setColor(Color.black);
		String yTitle = chart.getYDescription();
		graphics2.drawString(yTitle, -actualHeight / 2 - bottomGap- fm.stringWidth(yTitle) , 20);
		
		//write numbers on x axis and create vertical grids
		for(int i = 1; i <= numOfColumns; i ++) {
			graphics.setColor(Color.black);
			String number = chart.getValues().get(i-1).getX()+"";
			int x = leftGap + (i-1)*columnWidth + columnWidth/2 -number.length()/2;
			int y = actualHeight + CONST_GAP + 5;
			
			graphics.drawString(number, x, y);
			
			graphics.setColor(Color.orange);
			graphics.drawLine(leftGap+i*columnWidth,0 ,leftGap+i*columnWidth ,actualHeight );
		}
		
		//write numbers on y axis and  create horizontal grids
		for(int j = 0; j <= numOfRows; j++) {
			graphics.setColor(Color.black);
			String number = (chart.getMinY() + j * chart.getDeltaY()) + "";
//			int numLength = number.length() - 1;
			int oneLetter = fm.stringWidth(number);
			int x = leftGap - CONST_GAP - oneLetter - CONST_GAP/2; // right alignment on y axis
			int y = actualHeight - j* rowHeight + fm.getAscent()/2;
			graphics.drawString(number, x - 5, y);
			
			graphics.setColor(Color.orange);
			graphics.drawLine(leftGap,actualHeight - j*rowHeight ,leftGap+actualWidth,actualHeight - j*rowHeight );
//			graphics.setColor(Color.gray);
//			graphics.setStroke(new BasicStroke(3));
//			graphics.drawLine(leftGap-5,actualHeight - j*rowHeight ,leftGap,actualHeight - j*rowHeight );

		}
		
		//draw bars
		for(int i = 0,currentGap = leftGap+1; i < numOfColumns; currentGap+=columnWidth,i++) {
			int currentY = chart.getValues().get(i).getY();
			if(currentY > chart.getMaxY()) {
				currentY = chart.getMaxY();
			}
			int height = (int)((currentY - chart.getMinY()) / (double)chart.getDeltaY() * rowHeight);
			graphics.setColor(Color.orange);
			graphics.fillRect(currentGap, actualHeight  - height, columnWidth, height);
			
			graphics.setColor(Color.black);
		}
		
		//separate bars with white lines
		for(int i = 0,currentGap = leftGap+1; i < numOfColumns; currentGap+=columnWidth,i++) {
			int currentY = chart.getValues().get(i).getY();
			if(currentY > chart.getMaxY()) {
				currentY = chart.getMaxY();
			}
			graphics.setColor(Color.white);
			graphics.drawLine(currentGap + columnWidth, actualHeight, currentGap + columnWidth, 0);
		}
		
		//draw small lines on y axis
		for(int j = 0; j <= numOfRows; j++) {
			graphics.setColor(Color.gray);
			graphics.setStroke(new BasicStroke(3));
			graphics.drawLine(leftGap-5,actualHeight - j*rowHeight ,leftGap,actualHeight - j*rowHeight );

		}
		//draw  x and y axis
		graphics.setStroke(new BasicStroke(3));
		graphics.setColor(Color.gray);
		graphics.drawLine(leftGap, actualHeight, leftGap, CONST_GAP);
		graphics.drawLine(leftGap,actualHeight , leftGap + actualWidth -CONST_GAP/2,actualHeight);
		
		//draw y arrow
		Polygon arrow1 = new Polygon();
		arrow1.addPoint(leftGap,0);
		arrow1.addPoint(leftGap-CONST_GAP/2, CONST_GAP);
		arrow1.addPoint(leftGap+CONST_GAP/2, CONST_GAP);
		graphics.fillPolygon(arrow1);
		
		//draw x arrow
		Polygon arrow2 = new Polygon();
		arrow2.addPoint(dim.width, actualHeight);
		arrow2.addPoint(dim.width - CONST_GAP, actualHeight + CONST_GAP/2);
		arrow2.addPoint(dim.width - CONST_GAP, actualHeight - CONST_GAP/2);
		graphics.fillPolygon(arrow2);

	}

}

