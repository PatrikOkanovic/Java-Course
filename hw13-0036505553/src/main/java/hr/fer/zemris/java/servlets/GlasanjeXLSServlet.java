package hr.fer.zemris.java.servlets;

import java.io.IOException;

import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * {@link HttpServlet} used to generate a xls file based on the current votings
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-xls",urlPatterns= {"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!GlasanjeGlasajServlet.hasVotingHappened()) {
			req.setAttribute("errorMessage", "Voting has not happened");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		try {

			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=\"glasanjeRezultati.xls\"");
			
			

			HSSFWorkbook hwb = new HSSFWorkbook();

			HSSFSheet sheet = hwb.createSheet("new sheet");
			
//			Map<String, String[]> results = (Map<String, String[]>)req.getSession().getAttribute("resultsSorted");
			Map<String, String[]> results = GlasanjeRezultatiServlet.readSortedResults(req, resp);
			if(results == null) {
				req.setAttribute("errorMessage", "No votings have been done");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				hwb.close();
				return;
			}
			int br = 0;
			for(Entry<String,String[]> entry : results.entrySet()) {
				HSSFRow rowhead = sheet.createRow((short) br++);
				rowhead.createCell((short) 0).setCellValue(entry.getValue()[1]);
				rowhead.createCell((short) 1).setCellValue(entry.getValue()[0]);
			}

			OutputStream fileOut = resp.getOutputStream();

			hwb.write(fileOut);

			hwb.close();

		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}
