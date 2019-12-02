package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * Implements {@link ServletContextListener}.
 * When the server starts stores a variable of currentMiliSeconds to context attributes,
 * so that appInof.jsp can calculate how long the server has been running.
 * 
 * @author Patrik Okanovic
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startedAt", System.currentTimeMillis());
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
