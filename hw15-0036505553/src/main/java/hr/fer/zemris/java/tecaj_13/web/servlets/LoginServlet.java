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
 * {@link HttpServlet} used to implement login, if there is no current login user in the session, the
 * login is available. If the nick and password match, the user gets logged, else he is redirected back to the
 * main page with previously added nickname and blank password field.
 * 
 * @author Patrik Okanovic
 *
 */
@WebServlet(name="LoginPage",urlPatterns= {"/servleti/login"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 2863655182835276900L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		System.out.println(nick +" " + password);
		
		if(nick == null || password == null) {
			req.setAttribute("message", "Nick and password cannot be null.");
			resp.sendRedirect("main");
			return;
		}
		
		if(nick.isBlank()) {
			req.setAttribute("message", "Nick cannot be blank.");
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}
		
		if(password.isBlank()) {
			req.setAttribute("previousNick", nick);
			req.setAttribute("message", "Password cannot be blank.");
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}
		
		
		
		BlogUser user = DAOProvider.getDAO().getUser(nick,PasswordUtil.hashPassword(password));
		
		if(user == null) {
			req.setAttribute("message", "Invalid password.");
			req.setAttribute("previousNick", nick);
			List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
			req.setAttribute("blogUsers", blogUsers);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
//			resp.sendRedirect("main");
			return;
		} else {
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			
			resp.sendRedirect("main");
		}
	}
}
