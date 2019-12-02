package hr.fer.zemris.servlets;

import java.awt.BasicStroke;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;
/**
 * {@link HttpServlet} used to create a pie chart based on the 
 * current voting results.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns={"/servleti/glasanje-grafika"})
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
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> results = DAOProvider.getDao().getSorted(pollID);
		if(results == null) {
			return null;
		}
		long totalSum = 0;
		
		for(PollOption pollOption : results) {
			totalSum += pollOption.getVotesCount();
		}
		
		
		
		for(PollOption pollOption : results) {
			dataset.setValue(pollOption.getOptionTitle(), pollOption.getVotesCount() / (double)totalSum * 100);
		}
		
		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("PollOptions", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
