package hr.fer.zemris.java.custom.collections;

/**
 * Interface used as an implementation of collections.
 * @author Patrik Okanovic
 *
 */
public interface List extends Collection {

	/**
	 * Returns the object that is stored in backing array at position index.
	 * @param index
	 * @return Object
	 */
	Object get(int index);
	/**
	 * Inserts (does not overwrite) the given value in the collection. All the elements remain 
	 * untouched.
	 * @param value
	 * @param position
	 */
	void insert(Object value, int position);
	/**
	 * Searches the collection and returns the index of the first occurrence.
	 * @param value
	 * @return index of the element
	 */
	int indexOf(Object value);
	/**
	 * Removes element at specified index from collection.
	 * @param index
	 */
	void remove(int index);
}
