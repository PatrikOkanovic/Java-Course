package hr.fer.zemris.java.custom.collections;

/**
 * Interface used to test if the object is acceptable or not.
 * @author Patrik Okanovic
 *
 */
public interface Tester {

	/**
	 * Test the given object.
	 * @param obj
	 * @return true if the object is acceptable
	 */
	boolean test(Object obj);

}
