package hr.fer.zemris.java.hw07.observer2;
/**
 *  Counts (and writes to the standard output) the number of times the 
 *  value stored in  {@link IntegerStorage} has been changed since this 
 *  observer’s registration.
 *  
 * @author Patrik Okanovic
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Used for counting changes in the Subject
	 */
	private int counter;
	
	@Override
	public void valueChanged(IntegerStorageChange change) {
		
		System.out.println("Number of value changes since tracking: " + ++counter);

	}

}
