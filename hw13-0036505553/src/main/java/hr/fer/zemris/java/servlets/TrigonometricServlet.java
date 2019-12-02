package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to sinus and cosinus from all the integers in the interval
 * from a to b. Forwards the request to trigonometric.jsp
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="trigonometric", urlPatterns={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int a = 0;
		int b = 360;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch(Exception e) {
		}
		
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch(Exception e) {
		}
		
		if(a>b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		if(b>a+720) {
			b = a+720;
		}
		
		
		Map<Integer,Double[]> map = new LinkedHashMap<>();
		
		for(int i = a ; i <=b; i++) {
			
			map.put(i, new Double[] {Math.sin(i*Math.PI/180),Math.cos(i*Math.PI/180)});
		}
		
		req.setAttribute("map", map);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		
		
	}

}
