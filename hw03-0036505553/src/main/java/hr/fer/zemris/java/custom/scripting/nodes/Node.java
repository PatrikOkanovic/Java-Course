package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes. Used to implement the parser tree.
 * @author Patrik Okanovic
 *
 */
public class Node {

	/**
	 * Saves the children of the parent node.
	 */
	private ArrayIndexedCollection collection;
	/**
	 * Adds the child node to the parent node.
	 * @param child
	 * @throws NullPointerException
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);
		if(collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}
	/**
	 * Returns the number of the children.
	 * @return
	 */
	public int numberOfChildren() {
		if(collection == null) {
			return 0;
		}
		return collection.size();
	}
	/**
	 * Returns the child of the parent node at the given index.
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException{
		return (Node)collection.get(index);
	}
}
