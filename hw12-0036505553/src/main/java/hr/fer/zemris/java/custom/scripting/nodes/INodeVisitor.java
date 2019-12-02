package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * Interface used for implementing Visitor pattern.
 * 
 * @author Patrik Okanovic
 *
 */
public interface INodeVisitor {
	/**
	 * Visit the text node
	 * 
	 * @param node {@link TextNode}
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Visit the ForLoopNode
	 * 
	 * @param node {@link ForLoopNode}
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Visit the EchoNode
	 * 
	 * @param node {@link EchoNode}
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Visit the DocumentNode
	 * 
	 * @param node {@link DocumentNode}
	 */
	public void visitDocumentNode(DocumentNode node);

}
