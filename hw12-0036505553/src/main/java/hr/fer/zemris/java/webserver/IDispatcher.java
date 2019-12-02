package hr.fer.zemris.java.webserver;
/**
 * Defines how the request with urlPath is performed.
 * 
 * @author Patrik Okanovic
 *
 */
public interface IDispatcher {
	/**
	 * Dispatches the request.
	 * 
	 * @param urlPath string format of the urlPath
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
