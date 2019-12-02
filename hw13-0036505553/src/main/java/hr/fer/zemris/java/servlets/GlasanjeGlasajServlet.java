package hr.fer.zemris.java.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to track number of votes for your favourite band. Writes
 * the results to a file.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-glasaj",urlPatterns= {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Map with current number of votes
	 */
	Map<String,String> brojGlasova = new TreeMap<>();
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
		Map<String,String[]> bands = GlasanjeServlet.readBands(req, resp);
		if(! bands.containsKey(id)) {
			req.setAttribute("errorMessage", "Invalid id");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		if(! brojGlasova.containsKey(id)) {
			brojGlasova.put(id, "0");
		}
		
		int brojGlasovaZaID = Integer.parseInt(brojGlasova.get(id));
		brojGlasovaZaID++;
		brojGlasova.put(id, brojGlasovaZaID+"");
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		if(! Files.exists(Paths.get(fileName))) {
			Files.newOutputStream(Paths.get(fileName), StandardOpenOption.CREATE_NEW);
		}
		
		
		FileOutputStream outStream = new FileOutputStream(Paths.get(fileName).toFile(),false);
		
		StringBuilder sb = new StringBuilder();
		for(Entry<String,String> entry : brojGlasova.entrySet()) {
			sb.append(entry.getKey() +"\t"+entry.getValue()+"\n");
		}
		sb.setLength(sb.length()-1);
		byte[] buf = sb.toString().getBytes(StandardCharsets.UTF_8);
		outStream.write(buf);
		outStream.close();
		
		votingHappened = true;
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

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
