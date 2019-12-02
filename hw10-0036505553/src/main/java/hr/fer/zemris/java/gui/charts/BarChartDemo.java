package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 * Extends {@link JFrame}, creates a window with {@link BarChartComponent} in the center and a label
 * in the top left corner where the path from where data has been read is written.
 * 
 * @author Patrik Okanovic
 *
 */
public class BarChartDemo extends JFrame{
	

	private static final long serialVersionUID = 1L;
	/**
	 * The bar chart component
	 */
	private BarChartComponent chartComponent;
	/**
	 * Location of the source file
	 */
	private Path title;
	
	/**
	 * Constructor of the class
	 * 
	 * @param chartComponent
	 * @param title
	 */
	public BarChartDemo(BarChartComponent chartComponent, Path title) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar charts");
		setLocation(20, 20);
		setSize(550, 550);
		this.chartComponent = chartComponent;
		this.title = title;
		
		initGUI();
		
	}
	
	/**
	 * Initializes the graphic interface
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		JLabel label = new JLabel(title.toAbsolutePath().toString());
		add(label,BorderLayout.NORTH);
		
		getContentPane().add((Component)chartComponent, BorderLayout.CENTER);
		
		
		
	}


	/**
	 * Main program of the class.
	 * Reads data from a file and creates a bar chart based on it.
	 * 
	 * @param args path of source file
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("You must enter source path");
			return;
		}
		Path path = Paths.get(args[0]);
		BarChart chart;
		try {
			List<String> lines = Files.readAllLines(path);
			String xDescription = lines.get(0);
			String yDescription = lines.get(1);
			String[] pairs = lines.get(2).split("\\s+");
			List<XYValue> values = new ArrayList<>();
			for(String pairCombined : pairs) {
				String[] pair = pairCombined.split(",");
				int x = Integer.parseInt(pair[0]);
				int y = Integer.parseInt(pair[1]);
				values.add(new XYValue(x, y));
			}
			int minY = Integer.parseInt(lines.get(3));
			int maxY = Integer.parseInt(lines.get(4));
			int deltaY = Integer.parseInt(lines.get(5));
			chart = new BarChart(values, xDescription, yDescription, minY, maxY, deltaY);
		} catch(IOException exc) {
			System.out.println("Invalid path, please try again");
			return;
		} catch(NumberFormatException e) {
			System.out.println("Something is not parseable to int");
			return;
		}
		
		BarChartComponent chartComponent = new BarChartComponent(chart);
		
		SwingUtilities.invokeLater(() -> {
			BarChartDemo demo = new BarChartDemo(chartComponent, path);
			demo.setVisible(true);
		});
		
	}

}
