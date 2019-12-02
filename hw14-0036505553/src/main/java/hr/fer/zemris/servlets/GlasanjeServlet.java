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
import hr.fer.zemris.java.p12.dao.sql.model.Poll;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;
/**
 * {@link HttpServlet} used to display {@link PollOption} for the selected {@link Poll}
 * And give access to user to vote. 
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje",urlPatterns= {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {

private static final long serialVersionUID = 1L;
	

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pollidString = req.getParameter("pollID");
		if(pollidString == null) {
			req.setAttribute("errorMessage", "pollID must be submitted");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		long pollID = Long.parseLong(pollidString);
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions();
		
		
		
		if(pollOptions == null) {
			req.setAttribute("errorMessage", "Could not read file");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<PollOption> currentPollOptions = new ArrayList<>();
		
		for(PollOption pollOption : pollOptions) {
			if(pollOption.getPollID() == pollID) {
				currentPollOptions.add(pollOption);
			}
		}
		
		Poll poll = DAOProvider.getDao().getPollByID(pollID);
		
		req.setAttribute("poll", poll);
		
		req.setAttribute("pollOptions", currentPollOptions);
		
		req.setAttribute("pollID", pollID);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);

		
		
	}
}
