package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import java.util.Date;

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
 * {@link HttpServlet} used to add comment, email address of the user is set if he is logged in
 * else email address is anonymous.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="AddCommentServlet",urlPatterns= {"/servleti/addComment"})
public class AddCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		
		String nick = req.getParameter("nick");//to that user add a comment
		String blogID = req.getParameter("id"); // ID of the blog to add a comment
		Long id = null;
		try {
			id = Long.parseLong(blogID);
		} catch(NumberFormatException e) {
			req.setAttribute("errorMessage", "Invalid id!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		String comment = req.getParameter("comment");
		
		Object currentUser = req.getSession().getAttribute("current.user.nick");
		String email;
		String EMail = req.getParameter("EMail");
		if(currentUser == null) {
			email = EMail;;
		} else {
			BlogUser user = DAOProvider.getDAO().getUser((String)currentUser);
			email = user.getEmail(); 
		}
		
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
		
		if(blogEntry == null) {
			req.setAttribute("errorMessage", "No such blog entry");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if(comment.isBlank()) {
			req.setAttribute("errorMessage", "Cannot submit blank comment");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		BlogComment blogComment = new BlogComment();
		blogComment.setMessage(comment);
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setBlogEntry(blogEntry);
		
		DAOProvider.getDAO().saveBlogComment(blogComment);
		
		String urlPath = req.getContextPath() + "/servleti/author/" + nick +"/" + id;
		resp.sendRedirect(urlPath);
		

		
		
	}

}
