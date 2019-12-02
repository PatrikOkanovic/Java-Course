package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * {@link HttpServlet} used to redirect requests for adding new blog entries, editing previous
 * and adding comments.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="AuthorServlet",urlPatterns= {"/servleti/author/*"})
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getPathInfo());
		String[] splittedUrl = req.getPathInfo().substring(1).split("/");
		if(splittedUrl.length == 1) {
			doAuthor(req,resp);
		} else if(splittedUrl.length == 2) {
			if(splittedUrl[1].equals("new")) {
				doNew(req,resp);
			} else if(splittedUrl[1].equals("edit")) {
				doEdit(req,resp);
			} else {
				doEID(req,resp);
			}
		} else {
			req.setAttribute("errorMessage", "Invalid url!");
		}
		
		
	}

	/**
	 * Does the request when the url is /author/NICK/EID, then it shows the {@link BlogEntry} page
	 * with comments.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doEID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1); // remove first/
		String nickName = pathInfo.split("/")[0];
		String entryID = pathInfo.split("/")[1];
		Long id;
		try {
			id = Long.parseLong(entryID);
		} catch(NumberFormatException e) {
			req.setAttribute("errorMessage", "Invalid id of blogEntry");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
		
		List<BlogComment> comments = DAOProvider.getDAO().getBlogComments(blogEntry);
		
		req.setAttribute("author", nickName);
		req.setAttribute("blogEntry", blogEntry);
		req.setAttribute("comments", comments);
		
		req.getRequestDispatcher("/WEB-INF/pages/blogEntry.jsp").forward(req, resp);
	}


	/**
	 * Does the request when url is /author/NICK/edit, then redirects where user can 
	 * edit his blog entry, but only if he is logged in
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1); // remove first/
		String nickName = pathInfo.split("/")[0];
		
		String id = req.getParameter("id");
		
		
		
		Long ID = null;
		try {
			ID = Long.parseLong(id);
		} catch(NumberFormatException e) {
			req.setAttribute("errorMessage", "Invalid id of blogEntry");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(ID);
		
		req.setAttribute("author", nickName);
		req.setAttribute("id", id);
		req.setAttribute("blogEntry", entry);
		
		req.getRequestDispatcher("/WEB-INF/pages/editEntryBlog.jsp").forward(req, resp);
		
	}


	/**
	 * Does the request when url is /author/NICK/new, adds the new {@link BlogEntry},
	 *  but only the logged in user can add entries to his own page.
	 *  
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1); // remove first/
		String nickUrl = pathInfo.split("/")[0];
		req.setAttribute("author", nickUrl);
		
		req.getRequestDispatcher("/WEB-INF/pages/newEntryBlog.jsp").forward(req, resp);
		
	}


	/**
	 * Does the request when url is /author/NICK, shows a page of an {@link BlogUser}
	 * with his {@link BlogEntry}. Here user can choose to add a new blog entry if he is logged in.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1); // remove first/
		String nickUrl = pathInfo.split("/")[0];
		System.out.println(pathInfo);
		System.out.println(nickUrl);
		BlogUser user = DAOProvider.getDAO().getUser(nickUrl);
		
		if(user == null) {
			req.setAttribute("errorMessage", "No such user!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntries(user);
		
		req.setAttribute("blogEntries", blogEntries);
		req.setAttribute("author", nickUrl);
		
		
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
		
	}
}
