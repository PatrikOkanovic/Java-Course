package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to get data from the file with the definition of the bands
 * and set attribute for 
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje",urlPatterns= {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String,String[]> bands = readBands(req, resp);
		
		if(bands == null) {
			req.setAttribute("errorMessage", "Could not read file");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		req.setAttribute("bendovi", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);

		
		
	}

	/**
	 * Used to read bands from a text file and create a map.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static Map<String,String[]> readBands(HttpServletRequest req, HttpServletResponse resp) {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch(IOException e) {
			
			return null;
		}
		
		Map<String,String[]> bands = new LinkedHashMap<>();
		
		for(String line : lines) {
			String[] splitted = line.split("\t");
			bands.put(splitted[0], new String[] {splitted[1],splitted[2]});
		}
		
		return bands;
	}

}
