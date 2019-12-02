package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * {@link HttpServlet} used to analyze results of voting
 * sort results and determine the winners.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-rezultati",urlPatterns= {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * State is 0 if everything is fine, used to determine mistakes
	 */
	private static int state = 0;
	/**
	 * Currently number of most votes
	 */
	private static int mostVotes;
	
	/**
	 * Map of ID, number of votes
	 */
	private static Map<String,Integer> resultsSorted;
	/**
	 * Map of bends, ID is key, value is [bandName, link to song]
	 */
	private static Map<String, String[]> bends;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!GlasanjeGlasajServlet.hasVotingHappened()) {//do not show old results, say voting has not happened
			req.setAttribute("errorMessage", "Voting has not happened");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		Map<String,String[]> resultsSortedWithNames = readSortedResults(req, resp);
		
		if(state == 1) {
			req.setAttribute("errorMessage", "Voting results do not exist");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		} else if(state == 2) {
			req.setAttribute("errorMessage", "Could not read file");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		} else if(state == 3) {
			req.setAttribute("errorMessage", "No voting has been done");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
				
		Map<String,String> winners = new LinkedHashMap<>();
		
		for(Entry<String,Integer> entry : resultsSorted.entrySet()) {
			if(entry.getValue() != mostVotes) {
				break;
			}
			winners.put(bends.get(entry.getKey())[0], bends.get(entry.getKey())[1]);
		}
		
		req.setAttribute("winners", winners);
		req.setAttribute("resultsSorted", resultsSortedWithNames);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * Used to analyze results, and return a map of sorted results, key is ID, value
	 * is [numberOfVotes,bandName
	 * ]
	 * @param req
	 * @param resp
	 * @return
	 */
	public static Map<String,String[]> readSortedResults(HttpServletRequest req, HttpServletResponse resp) {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		if(!Files.exists(Paths.get(fileName))) {
			state=1;
			return null;
		}
		
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch(IOException e) {
			state=2;
			return null;
		}
		
		bends = GlasanjeServlet.readBands(req, resp);
		
		if(bends == null) {
			state=3;
			return null;
		}
		
		state = 0;
		
		Map<String, Integer> results = new LinkedHashMap<>();
		
		for(String line : lines) {
			String[] splitted = line.split("\t");
			results.put(splitted[0], Integer.parseInt(splitted[1]));
		}

		resultsSorted = results.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)); 
		
		Map<String,String[]> resultsSortedWithNames  = new LinkedHashMap<>();
		
		for(Entry<String,Integer> entry : resultsSorted.entrySet()) {
			resultsSortedWithNames.put(entry.getKey(), new String[] {entry.getValue()+"", bends.get(entry.getKey())[0]});
		}
		
		mostVotes = resultsSorted.values().iterator().next();
		
		return resultsSortedWithNames;
	}
	

}
