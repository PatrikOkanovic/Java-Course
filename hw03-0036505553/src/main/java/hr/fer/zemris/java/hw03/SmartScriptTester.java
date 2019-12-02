package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
/**
 * Demo program from the homeworks third problem used to test the {@link SmartScriptParser}
 * Takes the text as a string from the document. Then tries to parse it and create a parsing tree.
 * From the tree recretes what was written in the document.
 *
 */
public class SmartScriptTester {
	/**
	 * Main method of the class.
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid arguments.");
			return;
		}
		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Invalid document path!");
			return;
		}

		SmartScriptParser parser = null;

		try {
			
			parser = new SmartScriptParser(docBody);
			
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit (-1);
		} catch (Exception exc) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		
		System.out.println();
		System.out.println(originalDocumentBody.equals(originalDocumentBody2)); //the parsed text must be reparseable and if the two strings
		// of originalDocumentBodies are equal the parser is working
	}
	/**
	 * Creates the text of the original document body from the DocumentNode
	 * @param document
	 * @return text of the document
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		String s = "";
		if(document.numberOfChildren() != 0) {
			for(int i = 0; i < document.numberOfChildren(); i++) {
				s += recurisevelyBuildTree(document.getChild(i));
			}
		}
		return s;
	}
	
	/**
	 * Goes to the deepest part recursively of the node. Thus the nested for loops
	 * can be recreated.
	 * @param child
	 * @return
	 */
	private static String recurisevelyBuildTree(Node child) {
		String s = child.toString();
		
		for(int i = 0; i < child.numberOfChildren(); i++) {
			s += recurisevelyBuildTree(child.getChild(i));
		}
		if(child instanceof ForLoopNode) {
			s += "{$END$}";
		}
		
		return s;
	}
	

}
