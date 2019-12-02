package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 *  A node representing a command which generates some textual output dynamically. It inherits
 *	from Node class. Used in parser to create a tree .
 * @author Patrik OKanovic
 *
 */
public class EchoNode extends Node {

	private Element[] elements;

	

	public EchoNode(Element[] elements) {
		Objects.requireNonNull(elements);
		this.elements = elements;
	}
	
	/**
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() { // toString is used and not method asText() so that the escaping \\ and again parsing would work
		String s = "{$=";
		for(Element element : elements) {
			s += element.toString() + " ";
		}
		
		s+="$}";
		
		return s;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
		
	}
	
}
