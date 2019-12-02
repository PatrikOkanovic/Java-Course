package hr.fer.zemris.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.model.Poll;
/**
 * {@link HttpServlet} used to give access to user to determine which
 * Poll is he going to vote.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="s1",urlPatterns= {"/servleti/index.html"})
public class OdabirPollaServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	 @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
