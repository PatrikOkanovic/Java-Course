package hr.fer.zemris.java.webserver;
/**
 * An interface toward any object that can process current request: it gets
 * {@link RequestContext} as parameter and it is expected to create a content for client.
 * 
 * @author Patrik Okanovic
 *
 */
public interface IWebWorker {

	/**
	 * Creates different content for client.
	 * 
	 * @param context {@link RequestContext}
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
