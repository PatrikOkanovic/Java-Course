package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * A {@link HttpServlet} sets the attribute pickedBgColor from the parameter colour from the
 * request, default colour is white. Used to set the background colour.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet("/setcolor")
public class ColorServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pickedColor = req.getParameter("color");
		
		if(pickedColor != null) {
			req.getSession().setAttribute("pickedBgCol", pickedColor);
		} else {
			pickedColor = "white";
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
	}

}
