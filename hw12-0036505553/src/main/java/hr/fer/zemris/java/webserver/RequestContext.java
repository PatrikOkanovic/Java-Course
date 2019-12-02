package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class RequestContext {

	/**
	 * The outputStream
	 */
	private OutputStream outputStream;
	
	/**
	 * The used charset
	 */
	private Charset charset;
	
	/**
	 * The used encoding
	 */
	private String encoding = "UTF-8";
	
	/**
	 * The status code
	 */
	private int statusCode = 200;
	
	/**
	 * The status text
	 */
	private String statusText = "OK";
	
	/**
	 * The mime type
	 */
	private String mimeType  ="text/html";
	
	/**
	 * The content length
	 */
	private Long contentLength = null;
	
	/**
	 * The parameters
	 */
	private Map<String,String> parameters; 
	
	/**
	 * The temporary parameters
	 */
	private Map<String,String> temporaryParameters = new HashMap<>();
	
	/**
	 * The persistent parameters
	 */
	private Map<String,String> persistentParameters; // if null, treat as empty
	
	/**
	 * The output cookies
	 */
	private List<RCCookie> outputCookies; 

	/**
	 * True when header has been generated
	 */
	private boolean headerGenerated = false;
	
	/**
	 * The {@link IDispatcher}
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Session ID
	 */
	private String SID;
	
	/**
	 * Constructor of the class
	 * 
	 * @param outputStream must not be null
	 * @param parameters if null, treat as empty
	 * @param persistentParameters if null, treat as empty
	 * @param outputCookies if null, treat as empty
	 */
	public RequestContext(
			OutputStream outputStream, // must not be null!
			Map<String,String> parameters, // if null, treat as empty
			Map<String,String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies) { // if null, treat as empty
			
		this(outputStream, parameters, persistentParameters, outputCookies, null, null,null);
	}
	/**
	 * Constructor of the class
	 * @param outputStream must not be null
	 * @param parameters if null, treat as empty
	 * @param persistentParameters if null, treat as empty
	 * @param outputCookies if null, treat as empty
	 * @param temporaryParameters if null, treat as empty
	 * @param dispatcher {@link IDispatcher}
	 */
	public RequestContext(
			OutputStream outputStream, // must not be null!
			Map<String,String> parameters, // if null, treat as empty
			Map<String,String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies,
			Map<String,String> temporaryParameters,
			IDispatcher dispatcher,
			String SID) {
		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		
		if(parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}
		
		if(persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		
		if(outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}
		
		if(temporaryParameters == null) {
			this.temporaryParameters = new HashMap<>();
		} else {
			this.temporaryParameters = temporaryParameters;
		}
		
		this.dispatcher = dispatcher;
		this.SID = SID;
		
	}
	

	/**
	 * @param encoding the encoding to set
	 * @throws RuntimeException if header has been generated
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("The header has already been generated,cannot change encoding");
		}
		this.encoding = encoding;
	}

	/**
	 * @param statusCode the statusCode to set
	 * @throws RuntimeException if header has been generated
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("The header has already been generated,cannot change statusCode");
		}
		this.statusCode = statusCode;
	}

	/**
	 * @param statusText the statusText to set
	 * @throws RuntimeException if header has been generated
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("The header has already been generated,cannot change statusText");
		}
		this.statusText = statusText;
	}

	/**
	 * @param mimeType the mimeType to set
	 * @throws RuntimeException if header has been generated
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("The header has already been generated,cannot change mimeType");
		}
		this.mimeType = mimeType;
	}

	/**
	 * @param contentLength the contentLength to set
	 * @throws RuntimeException if header has been generated
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException("The header has already been generated,cannot change contentLength");
		}
		this.contentLength = contentLength;
	}
	
	/**
	 * 
	 * @return the {@link IDispatcher}
	 */
	public IDispatcher getIDispatcher() {
		return dispatcher;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String,String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the temporaryParameters
	 */
	public Map<String,String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * @param temporaryParameters the persistentParamemters to set
	 */
	public void setTemporaryParameters(Map<String,String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * @return the persistentParameters
	 */
	public Map<String,String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * @param persistentParameters the persistentParamemters to set
	 */
	public void setPersistentParameters(Map<String,String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}
	
	/**
	 * Adds the cookie to the list of cookies
	 * 
	 * @param cookie RCCookie
	 * @throws RuntimeException if header has been generated
	 */
	public void addRCCookie(RCCookie cookie) {
		if(headerGenerated) {
			throw new RuntimeException("The header has already been generated,cannot change outputCookies");
		}
		Objects.requireNonNull(cookie);
		outputCookies.add(cookie);
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association exists):
	 * 
	 * @param name key in the map parameters
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in parameters map (note, this set must be read-only)
	 * 
	 * @return set of names
	 */
	public Set<String> getParameterNames() {
		return new HashSet<String>(parameters.keySet()); // this way it will be read-only
	}
	
	/**
	 * Method that retrieves value from persistentParameters map (or null if no association exists):
	 * 
	 * @param name key in the persistentParameters map
	 * @return string
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	/**
	 * Method that retrieves names of all parameters in persistent parameters map (note, this set must be read-only)
	 * 
	 * @return set of keys of persistentParameters map
	 */
	public Set<String> getPersistentParameterNames() {
		return new HashSet<String>(persistentParameters.keySet());
	}
	
	/**
	 * Method that stores a value to persistentParameters map
	 * 
	 * @param name key
	 * @param value to be set
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from persistentParameters map
	 * 
	 * @param name key in the map
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Method that retrieves value from temporaryParameters map (or null if no association exists).
	 * 
	 * @param name key in the map
	 * @return
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in temporary parameters map (note, this set must be read-only).
	 * 
	 * @return set of names
	 */
	public Set<String> getTemporaryParameterNames() {
		return new HashSet<String>(temporaryParameters.keySet());
	}
	
	/**
	 * Method that retrieves an identifier which is unique for current user session (for now, implement it to return empty string).
	 */
	public String getSessionID() {
		return SID;
	}
	
	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name key in the map
	 * @param value to be put in the map
	 */
	public void setTemporaryParameter(String name, String value) {
		this.temporaryParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name key in the map
	 */
	public void removeTemporaryParameter(String name) {
		this.temporaryParameters.remove(name);
	}
	
	
	/**
	 * Write the data to the outputStream
	 * 
	 * @param data byte array
	 * @return this
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes the data to the outputStream.
	 * 
	 * @param data to be written
	 * @param offset the offset
	 * @param len
	 * @return {@link RequestContext}
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		
		outputStream.write(data, offset, len);
		return this;
	}
	
	/**
	 * Write the data from the string to the outputStream.
	 * 
	 * @param text string
	 * @return this
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		
		byte[] data = text.getBytes(charset);
		outputStream.write(data);
		return this;
	}

	/**
	 * Used to create the header when for the first time a write method has been called.
	 */
	private void generateHeader() {
		charset = Charset.forName(encoding);
		headerGenerated = true;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		
		sb.append("Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset="+encoding);
		}
		sb.append("\r\n");
		
		if(contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		
		if(! outputCookies.isEmpty()) {
			for(RCCookie cookie : outputCookies) {
				sb.append("Set-Cookie: " + cookie.name+"="+"\""+cookie.value+"\"");
				
				if(cookie.domain != null) {
					sb.append("; Domain="+cookie.domain);
				}
				if(cookie.path != null) {
					sb.append("; Path="+cookie.path);
				}
				if(cookie.maxAge != null) {
					sb.append("; Max-Age="+cookie.maxAge);
				}
				sb.append("; HttpOnly");
				sb.append("\r\n");
			}
		}
		
		sb.append("\r\n");
		
		try {
			outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Represents a cookie on the internet, used for implementing {@link SmartHttpServer}
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	public static class RCCookie {
		
		/**
		 * The properties
		 */
		private String properties;
		/**
		 * The name
		 */
		private String name;
		/**
		 * The value
		 */
		private String value;
		/**
		 * The domain
		 */
		private String domain;
		/**
		 * The path
		 */
		private String path;
		
		/**
		 * Maximal age
		 */
		private Integer maxAge;

		/**
		 * Constructor of the class
		 * 
		 * @param properties
		 * @param name
		 * @param value
		 * @param domain
		 * @param path
		 * @param maxAge
		 */
		public RCCookie(String properties, String name, String value, String domain, String path, Integer maxAge) {
			super();
			this.properties = properties;
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Constructor of the class.
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}

		/**
		 * @return the properties
		 */
		public String getProperties() {
			return properties;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		
	}
}
