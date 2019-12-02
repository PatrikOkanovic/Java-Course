package hr.fer.zemris.java.webserver.workers;


import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Sets the color of the page.
 * 
 * @author Patrik Okanovic
 *
 */
public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		if(color != null && checkIsValidColor(color)) {
			context.setPersistentParameter("bgcolor", color);
			context.write("Color was updated!: " +
                    "<a href=/index2.html>Index2</a>");
		} else {
			context.write("Color was NOT updated!: " +
                    "<a href=/index2.html>Index2</a>");
		}
		
	}

	/**
	 * Checks if color is valid
	 * 
	 * @param color
	 * @return
	 */
	private boolean checkIsValidColor(String color) {
		if(color.length() !=6) {
			return false;
		}
		String hexDigits="0123456789ABCDEF";
		for(char c : color.toCharArray()) {
			if(hexDigits.indexOf(c) == -1) {
				return false;
			}
		}
		return true;
	}
	

}
