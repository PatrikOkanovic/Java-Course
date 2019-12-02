package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 *  A node representing a piece of textual data. It inherits from Node class
 * @author Patrik Okanovic
 *
 */
public class TextNode extends Node {

	/**
	 * Text of the node.
	 */
	private String text;
	/**
	 * Creates the TextNode
	 * @param text
	 * @throws NullPointerException
	 */
	public TextNode(String text) {
		Objects.requireNonNull(text);
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		String s = "";
		char[] data = text.toCharArray();
		for(int i = 0, length = data.length; i < length; i++) {
			if(data[i] == '\\') {
				s+="\\\\";
			} else if(i+1 < length && data[i]=='{' && data[i+1]=='$') {
				s += "\\{";
			} else {
				s += data[i];
			}
		}
		return s;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
}
