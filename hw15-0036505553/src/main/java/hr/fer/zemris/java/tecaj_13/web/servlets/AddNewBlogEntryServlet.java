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
 * Used to add a new {@link BlogEntry}, only logged in user can add an entry to his 
 * own page of entries
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="AddNewServlet",urlPatterns= {"/servleti/addNewEntryBlog"})
public class AddNewBlogEntryServlet extends HttpServlet {

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
		
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		blogEntry.setCreator(creator);
		
		DAOProvider.getDAO().saveBlogEntry(blogEntry);
	
		
		resp.sendRedirect("author/"+nick);
	}

}
