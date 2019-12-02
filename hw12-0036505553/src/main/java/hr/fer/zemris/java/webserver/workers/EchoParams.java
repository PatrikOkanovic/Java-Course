package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Implements {@link IWebWorker}.
 * Echos the given parameters, simply creates a table of key value format.
 * 
 * @author Patrik Okanovic
 *
 */
public class EchoParams implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		context.setMimeType("text/html");
		try {
			context.write("<html>");
			context.write("<head>\r\n" + 
					"<style>\r\n" + 
					"table, th, td {\r\n" + 
					"  border: 1px solid black;\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"}\r\n" + 
					"th, td {\r\n" + 
					"  padding: 5px;\r\n" + 
					"}\r\n" + 
					"th {\r\n" + 
					"  text-align: left;\r\n" + 
					"}\r\n" + 
					"</style>\r\n" + 
					"</head>");
			context.write("<body>");
			context.write("<h1>Echo Params Table</h1>");
			context.write("<table style=\"width:100%\">");
			context.write("<tr>\r\n" + 
					"    <th>Key</th>\r\n" + 
					"    <th>Value</th> \r\n" +  
					"  </tr>\r\n");
			for(String key : context.getParameterNames()) {
				context.write("<tr><td>"+key+"</td>");
				context.write("<td>"+context.getParameter(key)+"</td></tr>");
			}
			context.write("</table></body></html>");
						
		} catch (IOException ex) {
			// Log exception to servers log...
						ex.printStackTrace();
					}
	}

}
