package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
/**
 * {@link HttpServlet} used to dynamically generate a pie chart when called.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="report", urlPatterns={"/reportImage"})
public class ReportServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		
		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		
		
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
		
		
	}

	/**
	 * Creates a {@link JFreeChart}.
	 * 
	 * @return {@link JFreeChart}
	 */
	private JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("BMW", 45.6);
		dataset.setValue("Chevy", 10.2);
		dataset.setValue("Mercedes", 44.2);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Cars", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
