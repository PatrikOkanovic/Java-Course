package hr.fer.zemris.java.custom.collections;

/**
 *  Represents some general collection of objects
 *  Provides general methods for handling with collections.
 *  Used only to be extended.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public interface Collection<E> {
	/**
     * Returns {@code true} if this list contains no elements.
     * @return {@code true} if this list contains no elements
     */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
     * Returns the number of elements in this collection.
     * @return size
     */
	 int size();
	
	/**
	 * Adds the given object into this collection.
	 * @param value to put in the collection
	 */
	public void add(E value);
	
	/**
	 * Returns true only if the collection contains given value, as determined by equals method. It is OK to ask if collection contains null.
	 * @param value 
	 * @return true if value is in the collection
	 */
	public boolean contains(Object value);
	
	/**
	 * Returns true only if the collection contains given value as determined by equals method and removes
	 * one occurrence of it.
	 * @param value
	 * @return true if it is removed
	 */
	public boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this collections, fills it with collection content and
	 * returns the array. This method never returns null. 
	 */
	public Object[] toArray() ;
	
	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * @param processor
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection. This other collection
	 * remains unchanged. 
	 * @param other
	 */
	default void addAll(Collection<? extends E> other) {
		/**
		 * Implements the <code>Processor<code\> class whose method process adds the values 
		 * to the current collection.
		 */
		class LocalProcessor implements Processor<E> {
			@Override
			public void process(E value) {
				add(value);
			}
		}
		
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Removes all elements from this collection. Implemented here as an empty method.
	 */
	void clear();
	
	/**
	 * Creates an object used for iteration in the collection.
	 * @return ElementsGetter
	 */
	public ElementsGetter<E> createElementsGetter();
	/**
	 * Adds all satisfying Objects from collection if they return true with the Tester.
	 * @param col
	 * @param tester
	 * @throws NullPointerException if col or tester is null
	 */
	default void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		if(col == null || tester == null) {
			throw new NullPointerException();
		}
		ElementsGetter<? extends E> getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			E element = getter.getNextElement();
			if(tester.test(element)) {
				add(element);
			}
		}
	}

	
}
