package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * {@link HttpServlet} used to edit a {@link BlogEntry}, but only
 * if the user is logged in the current session and if it is his {@link BlogEntry}
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="EditServlet",urlPatterns= {"/servleti/editEntryBlog"})
public class EditBlogEntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String title = req.getParameter("title");
		String text = req.getParameter("text");
		
		Object nickObject = req.getSession().getAttribute("current.user.nick");
		
		if(nickObject == null) {
			req.setAttribute("errorMessage", "No permission to do that");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
			
		}
		
		String nick = (String)req.getSession().getAttribute("current.user.nick");
		
		BlogUser creator = DAOProvider.getDAO().getUser(nick);
		
		
		
		if(creator == null) {
			req.setAttribute("errorMessage", "No permission to do that");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		String idString = req.getParameter("id");
		Long ID = null;
		
		try {
			ID = Long.parseLong(idString);
		} catch(NumberFormatException e) {
			req.setAttribute("errorMessage", "Invalid id of blogEntry");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(ID);
		
		if(blogEntry == null) {
			req.setAttribute("errorMessage", "Invalid id of blogEntry");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		blogEntry.setLastModifiedAt(new Date());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		
		DAOProvider.getDAO().saveBlogEntry(blogEntry);//only update existing
	
		
		resp.sendRedirect("author/"+nick+"/"+ID);
	}

}
