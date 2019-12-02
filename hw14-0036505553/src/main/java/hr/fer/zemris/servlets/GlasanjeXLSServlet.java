package hr.fer.zemris.servlets;

import java.io.IOException;


import java.io.OutputStream;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;


/**
 * {@link HttpServlet} used to generate a xls file based on the current votings
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="glasanje-xls",urlPatterns= {"/servleti/glasanje-xls"})
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
			long pollID = Long.parseLong(req.getParameter("pollID"));

			
			List<PollOption> results = DAOProvider.getDao().getSorted(pollID);
			if(results == null) {
				req.setAttribute("errorMessage", "No votings have been done");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				hwb.close();
				return;
			}
			int br = 0;
			for(PollOption pollOption : results) {
				HSSFRow rowhead = sheet.createRow((short) br++);
				rowhead.createCell((short) 0).setCellValue(pollOption.getOptionTitle());
				rowhead.createCell((short) 1).setCellValue(pollOption.getVotesCount());
			}

			OutputStream fileOut = resp.getOutputStream();

			hwb.write(fileOut);

			hwb.close();

		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}
