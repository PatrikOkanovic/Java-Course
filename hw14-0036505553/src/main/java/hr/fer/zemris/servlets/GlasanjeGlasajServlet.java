package hr.fer.zemris.servlets;



import java.io.IOException;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;
/**
 * {@link HttpServlet} used to track number of votes for your favourite band. Writes
 * the results to a file.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-glasaj",urlPatterns= {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * True when first voting happened
	 */
	private static boolean votingHappened = false;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		if(id == null) {
			req.setAttribute("errorMessage", "Invalid id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions();
		boolean foundID = false;
		for(PollOption pollOption : pollOptions) {
			if(pollOption.getId() == Long.parseLong(id)) {
				foundID = true;
				break;
			}
		}
		
		if(! foundID) {
			req.setAttribute("errorMessage", "Invalid id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		DAOProvider.getDao().performVoting(Long.parseLong(id));
		
		votingHappened = true;
		
		String pollID = req.getParameter("pollID");
		if(pollID == null) {
			req.setAttribute("errorMessage", "Invalid pollid");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
//		req.setAttribute("pollID", pollID);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+pollID);

	}
	/**
	 * Memorize voting happened
	 * 
	 * @return
	 */
	public static boolean hasVotingHappened() {
		return votingHappened;
	}
}
