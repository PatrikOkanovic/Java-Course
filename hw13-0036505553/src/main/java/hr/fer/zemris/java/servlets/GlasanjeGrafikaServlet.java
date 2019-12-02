package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

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
 * {@link HttpServlet} used to create a pie chart based on the 
 * current voting results.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns={"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!GlasanjeGlasajServlet.hasVotingHappened()) {
			req.setAttribute("errorMessage", "Voting has not happened");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		resp.setContentType("image/png");
		
		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart(req,resp);
		if(chart == null) {
			req.setAttribute("errorMessage", "No voting has been done");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		int width = 400;
		int height = 400;
		
		
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
		
	}
	
	/**
	 * Creates a {@link JFreeChart}.
	 * 
	 * @return {@link JFreeChart}
	 */
	private JFreeChart getChart(HttpServletRequest req, HttpServletResponse resp) {
		DefaultPieDataset dataset = new DefaultPieDataset();
//		Map<String, String[]> results = (Map<String, String[]>)req.getSession().getAttribute("resultsSorted");
		Map<String, String[]> results = GlasanjeRezultatiServlet.readSortedResults(req, resp);
		if(results == null) {
			return null;
		}
		int totalSum = 0;
		
		for(Entry<String,String[]> entry : results.entrySet()) {
			totalSum += Integer.parseInt(entry.getValue()[0]);
		}
		
		
		for(Entry<String,String[]> entry : results.entrySet()) {
			dataset.setValue(entry.getValue()[1], Integer.parseInt(entry.getValue()[0]) / (double)totalSum * 100);
		}
		
		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Bendovi", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
