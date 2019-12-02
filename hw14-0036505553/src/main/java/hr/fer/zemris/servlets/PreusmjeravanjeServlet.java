package hr.fer.zemris.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to redirect to /servleti/index.html
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="preusmjeravanje",urlPatterns= {"/index.html"})
public class PreusmjeravanjeServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./servleti/index.html");
	}
}
