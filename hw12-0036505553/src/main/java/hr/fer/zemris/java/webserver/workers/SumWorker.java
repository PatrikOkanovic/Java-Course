package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Represents Model-View-Controller Design Pattern.
 * Calculates the sum of numbers a and b, shows an image depending on it,.
 * Worker does not generate html, it delegates the work to the
 * {@link SmartScriptEngine}
 * 
 * @author Patrik Okanovic
 *
 */
public class SumWorker implements IWebWorker{


	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a = 1;
		int b = 2;
		try {
			a = Integer.parseInt(context.getParameter("a"));

			
		} catch(Exception e) {
			//ignore
		}
		
		try {
			b = Integer.parseInt(context.getParameter("b"));
			
		} catch(Exception e) {
			//ignore
		}
		
		int zbroj = a + b;
		
		context.setTemporaryParameter("varA", a + "");
		context.setTemporaryParameter("varB", b + "");
		context.setTemporaryParameter("zbroj", zbroj + "");
		
		if(zbroj % 2 == 0) {
			context.setTemporaryParameter("imgName","/images/homer.gif" );
		} else {
			context.setTemporaryParameter("imgName","/images/dog.png" );
		}
		
		context.getIDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
