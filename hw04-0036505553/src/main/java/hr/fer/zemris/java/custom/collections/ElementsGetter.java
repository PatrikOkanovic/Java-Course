package hr.fer.zemris.java.custom.collections;
/**
 * Used for iterating elements of the collection. Offers methods
 * hasNextElement and getNextElement
 * @author Patrik Okanovic
 *
 */
public interface ElementsGetter<E>{
	/**
	 * Checks if there are still elements in the collection which have not been iterated.
	 * @return true if ElementsGetter still has elements 
	 * @throws ConcurrentModificationException if the collection has been modified
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next element which is located in the collection if there is still 
	 * any more elements in the collection.
	 * @return Object
	 * @throws NoSuchElementException if there is no more elements to iterate
	 * @throws ConcurrentModificationException if the collection has been modified
	 */
	E getNextElement();
	
	/**
	 * Calls method process for each of the remaining elements from the ElementsGetter
	 * @param p Processor
	 */
	default void processRemaining(Processor<E> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}