package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.PasswordUtil;
/**
 * {@link HttpServlet} used to register a user, none of the fields can be blank
 * , nick is unique, if some of the requests is not satisfied the user is redirected back to the
 * register page. If registration is successful new user is set in the session atributes.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="RegisterPage",urlPatterns= {"/servleti/register"})
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String method = req.getParameter("method");
		
		if(method.equals("Cancel")) {
			resp.sendRedirect("main");
			return;
		}
		
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		BlogUser user = new BlogUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setEmail(email);
		
		
		req.setAttribute("user", user);
		
		List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
		boolean nickUnique = true;
		for(BlogUser us : blogUsers) {
			if(us.getNick().equals(nick)) {
				nickUnique = false;
			}
		}
		
		if(! nickUnique) {
			req.setAttribute("errorMessage", "Nick already taken, please chose another!");
			doGet(req, resp);
			return;
		}
	
		if(firstName.isBlank()) {
			req.setAttribute("errorMessage", "Name cannot be empty");
			doGet(req, resp);
			return;
		}
		if(lastName.isBlank()) {
			req.setAttribute("errorMessage", "Last name cannot be empty");
			doGet(req, resp);
		}
		if(email.isBlank()) {
			req.setAttribute("errorMessage", "Email cannot be empty");
			doGet(req, resp);
			return;
		}
		if(nick.isBlank()) {
			req.setAttribute("errorMessage", "Nick cannot be empty");
			doGet(req, resp);
			return;
		}
		if(password.isBlank()) {
			req.setAttribute("errorMessage", "Password cannot be empty");
			doGet(req, resp);
			return;
		}
		
		user.setPasswordHash(PasswordUtil.hashPassword(password));
		
		DAOProvider.getDAO().saveUser(user);
		
		setNewSessionUser(user,req);
		
		resp.sendRedirect("main");

	}

	/**
	 * Sets the new user in the session.
	 * 
	 * @param user
	 * @param req
	 */
	private void setNewSessionUser(BlogUser user, HttpServletRequest req) {
		req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());
	}


}
