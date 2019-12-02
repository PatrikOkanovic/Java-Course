package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * A node representing an entire document. It inherits from Node class.
 * Main node generate from the text with the parser.
 * @author Patrik Okanovic
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
		
	}

}
