package hr.fer.zemris.java.custom.collections;

/**
 *  Represents some general collection of objects
 *  Provides general methods for handling with collections.
 *  Used only to be extended.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class Collection {

	/**
	 * Default constructor but visibility is protected.
	 */
	protected Collection() {

	}
	
	/**
     * Returns {@code true} if this list contains no elements.
     * @return {@code true} if this list contains no elements
     */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
     * Returns the number of elements in this collection.
     * @return size
     */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 * @param value to put in the collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Returns true only if the collection contains given value, as determined by equals method. It is OK to ask if collection contains null.
	 * @param value 
	 * @return true if value is in the collection
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes
	 * one occurrence of it.
	 * @param value
	 * @return true if it is removed
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and
	 * returns the array. This method never returns null. 
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection
	 * remains unchanged. 
	 * @param other
	 */
	public void addAll(Collection other) {
		/**
		 * Implements the <code>Processor<code\> class whose method process adds the values 
		 * to the current collection.
		 */
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Removes all elements from this collection. Implemented here as an empty method.
	 */
	public void clear() {
		
	}
}
