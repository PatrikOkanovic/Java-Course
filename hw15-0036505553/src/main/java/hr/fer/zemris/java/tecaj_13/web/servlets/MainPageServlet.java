package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * {@link HttpServlet} used to represent the main page, where user can log in,
 * register, and  show a list of current blog users.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="MainPage",urlPatterns= {"/servleti/main"})
public class MainPageServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("blogUsers", blogUsers);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
