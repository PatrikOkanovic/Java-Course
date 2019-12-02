package hr.fer.zemris.java.webserver;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * An implementation of the HTTP Server.
 * Program ends using terminate button.
 *  
 * @author Patrik Okanovic
 *
 */
public class SmartHttpServer {

	/**
	 * Time of five minutes for cleaning thread
	 */
	private static final long FIVE_MINUTES = 5*60*1000;

	/**
	 * The address
	 */
	private String address;
	
	/**
	 * The domain name
	 */
	private String domainName;
	
	/**
	 * The port
	 */
	private int port;
	
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	
	/**
	 * The session timeout
	 */
	private int sessionTimeout;
	
	/**
	 * Map of mimeTypes
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * The server thread
	 */
	private ServerThread serverThread;
	
	/**
	 * The thread pool
	 */
	private ExecutorService threadPool;
	
	/**
	 * The document root, used for resolving to absolute path in the code.
	 */
	private Path documentRoot;
	
	/**
	 * Map of {@link IWebWorker}
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map of sessions
	 */
	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * Random generator for sessions
	 */
	private Random sessionRandom = new Random();


	/**
	 * The constructor of the class, load all the parameters form the config.propetries
	 * 
	 * @param configFileName path of the config.properties file
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		
		try {
			properties.load(Files.newInputStream(Paths.get(configFileName)));
		} catch(IOException exc) {
			exc.printStackTrace();
		}
		
		address = properties.getProperty("server.properties");
		domainName = properties.getProperty("server.domainName");
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
		documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
		
		String mimeTypesPath = properties.getProperty("server.mimeConfig");
		Properties mimeProperties = new Properties();
		try {
			mimeProperties.load(Files.newInputStream(Paths.get(mimeTypesPath)));
		} catch(IOException exc) {
			exc.printStackTrace();
		}
		
		for(Object key : mimeProperties.keySet()) {
			mimeTypes.put(key.toString(), mimeProperties.get(key).toString());
		}
		
		//load workers
		String workerPath = properties.getProperty("server.workers");
		loadWorkers(Paths.get(workerPath));
		
	}
	/**
	 * Used to load workers and create a map of {@link IWebWorker}
	 * from the workers.properties
	 * 
	 * @param path of the file workers.config
	 */
	private void loadWorkers(Path path) {
		Properties workerProperties = new Properties();
		try {
			workerProperties.load(Files.newInputStream(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
		for(Object workerPath : workerProperties.keySet()) {
			String workerPathString = workerPath.toString();
			String fqcn = workerProperties.getProperty(workerPathString);
			
			IWebWorker iww = loadWebWorker(fqcn);
			
			if(workersMap.containsKey(workerPathString)) {
				throw new RuntimeException("Cannot have different workers with same path");
			}
			workersMap.put(workerPathString, iww);

		}
		
		
		
	}
	
	/**
	 * Used to create instances of {@link IWebWorker} using JVM.
	 * 
	 * @param fqcn fully qualified class name
	 * @return {@link IWebWorker}
	 */
	@SuppressWarnings("deprecation")
	private IWebWorker loadWebWorker(String fqcn)  {
		Class<?> referenceToClass = null;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object newObject = null;
		try {
			newObject = referenceToClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		IWebWorker iww = (IWebWorker)newObject;
		
		return iww;
	}

	/**
	 * Starts the server thread and creates the threadPool
	 */
	protected synchronized void start() {
		
		if (serverThread == null && threadPool == null) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread = new ServerThread();
//			serverThread.setDaemon(true);
			serverThread.start();
			
			Thread sessionGarbageCollectorThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						for(Entry<String,SessionMapEntry> entry : sessions.entrySet()) {
							long currentTime = System.currentTimeMillis()/1000;
							if(entry.getValue().validUntil < currentTime) {
								sessions.remove(entry.getKey());
							}
						}
						
						try {
							Thread.sleep(FIVE_MINUTES);
						} catch(InterruptedException e) {
							//ignore
						}
					}
					
				}
			});
			
			sessionGarbageCollectorThread.setDaemon(true);
			sessionGarbageCollectorThread.start();
			
			
		}	
		
		
	}

	/**
	 * Stops the server thread, and shutdown the pool
	 */
	protected synchronized void stop() {
		serverThread.kill();
		threadPool.shutdown();
	}

	/**
	 * The server thread used to accept requests from clients and 
	 * submit them to the thread pool
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * Used to know when to kill the thread
		 */
		private volatile boolean stop = false;
		
		/**
		 * Change stop to true
		 */
		public void kill() {
			stop = true;
		}
		
		@Override
		public void run() {
			
			try (ServerSocket socket = new ServerSocket()) {
				socket.bind(new InetSocketAddress(InetAddress.getByName(address),port));
				while(! stop) {
					Socket client = socket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}

	/**
	 * Implement {@link Runnable} and {@link IDispatcher}.
	 * Represents a client who sets requests to the server thread.
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private class ClientWorker implements Runnable,IDispatcher {
		private static final int SID_LENGTH = 20;

		/**
		 * The socket
		 */
		private Socket csocket;
		
		/**
		 * The {@link PushbackInputStream}
		 */
		private PushbackInputStream istream;
		
		/**
		 * The {@link OutputStream}
		 */
		private OutputStream ostream;
		
		/**
		 * Version of the HTTP
		 */
		private String version;
		
		/**
		 * Method from the header
		 */
		private String method;
		
		/**
		 * The host
		 */
		private String host;
		
		/**
		 * Map of parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		
		/**
		 * Map of temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		
		/**
		 * Map of permanent parameters
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		
		/**
		 * List of {@link RCCookie}
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * 
		 */
		private String SID;
		
		/**
		 * The {@link RequestContext}
		 */
		private RequestContext context;

		
		/**
		 * Constructor of the class.
		 * 
		 * @param csocket {@link Socket}
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			
		}

		@Override
		public void run() {
			
			try {
				
				ostream = csocket.getOutputStream();
				istream =  new PushbackInputStream(csocket.getInputStream());
				
				
				List<String> request = readRequest();
				
				if(request == null || request.size() < 1) {
					sendError(ostream,400,"Bad request");
					return;
				}
				
				String firstLine = request.get(0);
				String[] firstLineSplitted = firstLine.split(" ");
				if(isHeaderInvalid(firstLineSplitted)) {
					return;
				}
				boolean foundHost = false;
				for(String line : request) {
					if(line.startsWith("Host")) {
						String[] hostSplitted = line.split(" ");
						if (hostSplitted[1].contains(":")) {
							host = (hostSplitted[1].split(":"))[0];
						} else {
							host = hostSplitted[1];
						}
						foundHost = true;
						break;
					}
				}
				
				if(! foundHost) {
					host = domainName;
				}
//				System.out.println(host);
//				System.out.println(domainName);
				
				String path;
				String paramString;
				String requestedPath = firstLineSplitted[1];
				
				checkSession(request);
				
				if(requestedPath.contains("?")) {
					String[] requestedPathSplitted = requestedPath.split("\\?");
					path = requestedPathSplitted[0];
					paramString = requestedPathSplitted[1];
					parseParameters(paramString);
				} else {
					path = requestedPath;
				}
				
				internalDispatchRequest(path, true);
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ostream.flush();
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				
				
		}

		/**
		 * Method used to check cookies and sessions.
		 * 
		 * @param request list of strings from the request
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = null;
			for(String line : request) {
				if(!line.startsWith("Cookie:")) {
					continue;
				}
				String cookieLine = line.substring(8);
				String[] cookies = cookieLine.split(";");
				for(String cookie : cookies) {
//					System.out.println(cookie);
					if(cookie.startsWith("sid")) {
						sidCandidate = cookie.substring(5,cookie.length()-1);

					}
				}
				
			}
			
			boolean needToGenerateSID = false;
			SessionMapEntry session;
			if (sidCandidate != null && (session=sessions.get(sidCandidate)) != null) {
				if (! host.equals(session.host)) {
					needToGenerateSID = true;
					
				} else {
					if (session.validUntil < System.currentTimeMillis() / 1000) {// too old
						sessions.remove(sidCandidate);
						needToGenerateSID = true;
					} else {
						// found
						session.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
						permParams = session.map;
						return;
					}
				}

			} else {
            	needToGenerateSID = true;
            }
            if(needToGenerateSID) {
            	sidCandidate = generateSID();
            }
            SID = sidCandidate;
            long time = System.currentTimeMillis() / 1000 + sessionTimeout;
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            SessionMapEntry newSession = new SessionMapEntry();
            newSession.host = host;
            newSession.sid = SID;
            newSession.map = map;
            newSession.validUntil = time;
            sessions.put(SID, newSession);
            permParams = newSession.map;
            outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
            
		}

		/**
		 * Generates random 20 long alphabet code.
		 *  
		 * @return
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();
			String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			for(int i = 0; i < SID_LENGTH; i++) {
				int index = (int) (Alphabet.length() * sessionRandom.nextDouble());
				sb.append(Alphabet.charAt(index));
			}
			return sb.toString();
		}

		/**
		 * Method used to parse parameters, parameters are split with &, and parameter key with =.
		 * 
		 * @param paramString to be parsed
		 */
		private void parseParameters(String paramString) {
			String[] paramsSplited = paramString.split("&");
			for(String parameter : paramsSplited) {
				String[] keyAndValue = parameter.split("=");
				params.put(keyAndValue[0], keyAndValue[1]);
			}
		}

		/**
		 * Header is valid if version of HTTP is 1.0 or 1.1
		 * and first line is starting with GET
		 * 
		 * @param firstLineSplitted first line of the header
		 * @return
		 * @throws IOException
		 */
		private boolean isHeaderInvalid(String[] firstLineSplitted) throws IOException {
			version = firstLineSplitted[2];
			method = firstLineSplitted[0];
			if(!(method.equals("GET") &&(version.equals("HTTP/1.0") || version.equals("HTTP/1.1")) )) {
				sendError(ostream, 400, "Bad request");
				return true;
			}
			return false;
		}

	
		/**
		 * Header represented as a single string splits by enters watching
		 * on the multi line attributes.
		 *  
		 * @return
		 * @throws IOException
		 */
		private  List<String> readRequest() throws IOException {
			byte[] header = readRequest(istream);
			if(header == null) {
				sendError(ostream, 400, "Bad request");
				return null;
			}
			String requestHeader = new String(header, StandardCharsets.US_ASCII);
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Simple state machine for reading the header of the HTTP request.
		 * 
		 * @param is {@link InputStream}
		 * @return
		 * @throws IOException
		 */
		private  byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				return bos.toByteArray();
			}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);

		}
		
		/**
		 * Does the real work of the method dispatchRequest
		 * 
		 * @param urlPath
		 * @param directCall true if the call was direct, used for implementing private requests
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall)
				 throws Exception {
			try {
				Path reqPath = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();
				
				if(context == null) {
					context = new RequestContext(ostream, params, permParams, outputCookies,tempParams,this,SID);
				}
	//			System.out.println(reqPath);
	//			System.out.println(urlPath);
				if(urlPath.startsWith("/private") && directCall == true) {
					sendError(ostream, 404, "Forbidden!");
				}else if(urlPath.startsWith("/ext")) {
					String fqcn = "hr.fer.zemris.java.webserver.workers."+urlPath.substring(5);
					IWebWorker webWorker = loadWebWorker(fqcn);
					webWorker.processRequest(context);
				} else if(workersMap.containsKey(urlPath)) {
					IWebWorker worker = workersMap.get(urlPath);
					worker.processRequest(context);
				} else if(! reqPath.startsWith(documentRoot)) {
					sendError(ostream, 403, "Forbidden!");
				} else if(! Files.exists(reqPath) || !Files.isReadable(reqPath)) {
					sendError(ostream, 404, "File not found!");
				} else if(urlPath.endsWith("smscr")) {
					String documentBody;
					try {
						documentBody = readFromDisk(reqPath.toString());
					} catch (IOException e) {
						sendError(ostream, 404, "File not found!");
						System.out.println("Nema te skripte");
						return;
					}
					new SmartScriptEngine(
							new SmartScriptParser(documentBody).getDocumentNode(),
							context
							).execute();
					
				} else {
					//determine mimetype
					String[] splitedForExtension = reqPath.getFileName().toString().split("\\.");
					String extension = splitedForExtension[splitedForExtension.length-1];
					String mime = mimeTypes.get(extension);
					byte[] data = Files.readAllBytes(reqPath);
					String mimeType = mime == null ? "application/octet-stream" : mime;
					RequestContext rc = context;
					rc.setMimeType(mimeType);
					rc.setStatusCode(200);
					rc.write(data);
	
				}
			} finally {
				ostream.flush();
			}
			
		
		}
			
		
		


	}
	/**
	 * Class used to implement sessions.
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private static class SessionMapEntry {
		@SuppressWarnings("unused")
		/**
		 * Session id
		 */
		String sid;
		/**
		 * The host
		 */
		String host;
		/**
		 * Time when it stops being valid
		 */
		long validUntil;
		/**
		 * Thread safe map
		 */
		Map<String,String> map;
		}

	
	/**
	 * Reads from the disk
	 * 
	 * @param filePath
	 * @return string
	 * @throws IOException
	 */
	private static String readFromDisk(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
	}
	
	
	
	/**
	 * Sends error to the {@link OutputStream}
	 * 
	 * @param cos {@link OutputStream}
	 * @param statusCode 404,403..
	 * @param statusText 
	 * @throws IOException
	 */
	private static void sendError(OutputStream cos, 
			int statusCode, String statusText) throws IOException {
			try {
				cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
				cos.flush();
			} catch(Exception e) {
				//ignore
			}

		}
	
	/**
	 * Main method of the class.
	 * Stops program when user inputs "kill"
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("I was expecting configFile path");
			return;
		}
		
		SmartHttpServer server = new SmartHttpServer(args[0]);
		
		server.start();
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			if(sc.hasNext()) {
				if(sc.nextLine().equals("kill")) {
					try {
						server.stop();
					} catch(Exception e) {
							
					} finally {
						sc.close();
						Runtime.getRuntime().halt(0);
					}
				}
			}
		}
		
	}

}
