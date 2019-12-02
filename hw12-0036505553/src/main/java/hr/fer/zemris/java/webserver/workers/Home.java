package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Implements {@link IWebWorker}.
 * Gives links to previous pages.
 * 
 * @author Patrik Okanovic
 *
 */
public class Home implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String color = context.getPersistentParameter("bgcolor");
		
		if(color != null) {
			context.setTemporaryParameter("bacground", color);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		context.getIDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

	
}
