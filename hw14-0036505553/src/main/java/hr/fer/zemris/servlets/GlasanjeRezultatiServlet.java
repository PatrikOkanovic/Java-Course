package hr.fer.zemris.servlets;

import java.io.IOException;


import java.util.ArrayList;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;
/**
 * {@link HttpServlet} used to analyze results of voting
 * sort results and determine the winners.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-rezultati",urlPatterns= {"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!GlasanjeGlasajServlet.hasVotingHappened()) {//do not show old results, say voting has not happened
			req.setAttribute("errorMessage", "Voting has not happened");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		String pollidString = req.getParameter("pollID");
		if(pollidString == null) {
			req.setAttribute("errorMessage", "invalid pollID");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		long pollID = Long.parseLong(req.getParameter("pollID"));
		
		List<PollOption> sortedByMostVotes = DAOProvider.getDao().getSorted(pollID);
	
		
				
		List<PollOption> winners = new ArrayList<>();
		
		long mostVotes = sortedByMostVotes.get(0).getVotesCount();
		
		for(PollOption pollOption : sortedByMostVotes) {
			if(pollOption.getVotesCount() == mostVotes) {
				winners.add(pollOption);
			} else {
				break;
			}
		}
		
		req.setAttribute("winners", winners);
		req.setAttribute("resultsSorted", sortedByMostVotes);
		req.setAttribute("pollID", pollID);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	
	

}
