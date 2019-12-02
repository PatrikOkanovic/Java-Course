package hr.fer.zemris.java.custom.collections;
/**
 * Represents an implementation of a stack
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class ObjectStack {

	/**
	 * Internally used as an Adaptee to the Adapter ObjectStack in the Adapter pattern.
	 * Stack is bulit based on this collection.
	 */
	private ArrayIndexedCollection collection;
	
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
     * Returns {@code true} if this list contains no elements.
     * @return {@code true} if this list contains no elements
     */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
     * Returns the number of elements in this collection.
     * @return size
     */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Pushes given value on the stack. Null value must not be allowed to be
	 * placed on stack.
	 * @param value
	 */
	public void push(Object value) throws NullPointerException {
		collection.add(value);
	}
	
	/**
	 *  Removes last value pushed on stack from stack and returns it. If the stack is empty when
	 *	method pop is called
	 * @return last value put on the stack
	 * @throws EmptyStackException if the stack is empty when
	 *		   method is called
	 */
	public Object pop() {
		if(collection.size() == 0) {
			throw new EmptyStackException("The stack is empty");
		}
		Object returnObject = collection.get(collection.size()-1);
		collection.remove(collection.size()-1);
		return returnObject;
	}
	
	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * @return last element put on stack
	 * @throws EmptyStackException if the stack is empty when the method is called
	 */
	public Object peek() {
		if(collection.size() == 0) {
			throw new EmptyStackException("The stack is empty");
		}
		return collection.get(collection.size()-1);
	}
	
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		collection.clear();
	}
	
	
}
