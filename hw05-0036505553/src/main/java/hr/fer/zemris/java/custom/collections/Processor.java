package hr.fer.zemris.java.custom.collections;

/**
 *  Represents a model of an object capable of performing some operation on the passed object.
 *  Class represents an
 *  conceptual contract between clients which will have objects to be processed.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
@FunctionalInterface
public interface Processor<E> {

	/**
	 * Processes the given value.
	 * @param e 
	 */
	public void process(E e);
}
