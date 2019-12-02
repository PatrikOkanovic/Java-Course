package hr.fer.zemris.java.hw07.observer2;

/**
 *  Writes to the standard output double value  
 *  of the current value which is stored in subject {@link IntegerStorage}, but only first 
 *  n times since its registration with the subject (n is given in constructor). After writing the double value for 
 *  the n-th time, the observer automatically de-registers itself from the subject.
 *  
 * @author Patrik Okanovic
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	private int counter;
	
	public DoubleValue(int n) {
		this.counter = n;
	}

	@Override
	public void valueChanged(IntegerStorageChange change) {
		
		System.out.println("Double value: " + change.getNewValue()*2);
		
		if(--counter == 0) {
			change.getIntegerStorage().removeObserver(this);
		}

	}

}
