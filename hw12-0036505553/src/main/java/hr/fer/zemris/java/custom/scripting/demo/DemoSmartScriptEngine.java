package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Class used to show implementation result from homework.
 * 
 * @author Patrik Okanovic
 *
 */
public class DemoSmartScriptEngine {
	/**
	 * Main method of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		osnovni();
		
		System.out.println("==============================================");
		
		zbrajanje();
		System.out.println();
		
		System.out.println("==============================================");

		brojPoziva();
		
		System.out.println("==============================================");
		
		fibonacci();
		
		System.out.println("==============================================");
		
		fibonaccih();
		
		System.out.println("==============================================");
		
		
		
	}

	
	

	/**
	 * Fibonacci from homework
	 */
	private static void fibonacci() {
		String documentBody;
		try {
			documentBody = readFromDisk("src/main/resources/fibonacci.smscr");
		} catch (IOException e) {
			System.out.println("Unable to read the script");
			return;
		}
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();

		
	}

	/**
	 * Fibonacci html from homework
	 */
	private static void fibonaccih() {
		String documentBody;
		try {
			documentBody = readFromDisk("src/main/resources/fibonaccih.smscr");
		} catch (IOException e) {
			System.out.println("Unable to read the script");
			return;
		}
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}

	/**
	 * Broj poziva from homework
	 */
	private static void brojPoziva() {

		String documentBody = null;
		try {
			documentBody = readFromDisk("src/main/resources/brojPoziva.smscr");
		} catch (IOException e) {
			System.out.println("Unable to read the script");
			return;
		}
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
		cookies);
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));

	}

	/**
	 * Zbrajanje from homework
	 */
	private static void zbrajanje() {

		String documentBody = null;
		try {
			documentBody = readFromDisk("src/main/resources/zbrajanje.smscr");
		} catch (IOException e) {
			System.out.println("Unable to read the script");
			return;
		}
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();

	}

	/**
	 * Osnovni from homework
	 */
	private static void osnovni() {
		String documentBody;
		try {
			documentBody = readFromDisk("src/main/resources/osnovni.smscr");
		} catch (IOException e) {
			System.out.println("Unable to read the script");
			return;
		}
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
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
}
