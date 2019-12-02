package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
/**
 * Class used to write the tree generated with the {@link SmartScriptParser}
 * using the visitor pattern
 * 
 * @author Patrik Okanovic
 *
 */
public class TreeWriter {
	
	/**
	 * Main method of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("I was expecting argument file name");
			return;
		}
		String fileName = args[0];
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Invalid document path!");
			return;
		}
		
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	
	
	/**
	 * Implementation of the {@link INodeVisitor}, writes the generated
	 * tree from {@link DocumentNode}
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf(node.toString());
			
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.printf(node.toString());
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.printf("{$END$}");
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf(node.toString());			
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			if(node.numberOfChildren() != 0) {
				for(int i = 0, len = node.numberOfChildren(); i < len; i++) {
					node.getChild(i).accept(this);
				}
			}
			
		}
		
	}

}



