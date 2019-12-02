package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Creates an excel file generated from parameters a, b and n from the request.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="powers", urlPatterns={"/powers"})
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Start of the interval
	 */
	private int a;
	/**
	 * End of the interval
	 */
	private int b;
	/**
	 * Number of sheets in the excel file
	 */
	private int n;
	/**
	 * Lower bound for a and b
	 */
	private static final int AB_LOWER = -100;
	/**
	 * Lower upper for a and b
	 */
	private static final int AB_UPPER = 100;
	/**
	 * Lower bound for n
	 */
	private static final int N_LOWER = 1;
	/**
	 * Upper bound for n
	 */
	private static final int N_UPPER = 5;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch(Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if(checkInvalidParameters()) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		generateExcel(req,resp);
		
	}

	/**
	 * Generates excel file and sets its name. Each sheet has two columns.
	 * First column shows integers from a to b.
	 * Second column is value from the left column powered with number of the sheet.
	 * 
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 */
	private void generateExcel(HttpServletRequest req, HttpServletResponse resp) {
		try {

			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
			
			List<Integer> interval = new ArrayList<>(); // solves problem with rows if a is negative
			for(int i = a ; i <= b; i++) {
				interval.add(i);
			}

			HSSFWorkbook hwb = new HSSFWorkbook();
			
			for(int i = 1; i <= n; i++) {
				HSSFSheet sheet = hwb.createSheet(i+"");
				for(int j = 0; j <= b - a ; j++) {
					HSSFRow rowhead = sheet.createRow((short)j);
					rowhead.createCell((short)0).setCellValue(interval.get(j)+"");
					rowhead.createCell((short)1).setCellValue(Math.pow(interval.get(j), i)+"");;
				}
			}

			OutputStream fileOut = resp.getOutputStream();

			hwb.write(fileOut);

			hwb.close();

		} catch (Exception ex) {
			System.out.println(ex);

		}

	}

	/**
	 * Checks if parameters are valid. If they are not valid forwards the request
	 * to error.jsp, which just shows that some of the parameters has not been set as expected.
	 * 
	 * @return true if parameters are not in the defined bounds
	 */
	private boolean checkInvalidParameters() {
		return a < AB_LOWER || a > AB_UPPER
				|| b < AB_LOWER || b > AB_UPPER
				|| n < N_LOWER || n > N_UPPER || a > b;
	}


}
