package hr.fer.zemris.java.hw07.observer2;
/**
 * Used to store previous and current state of {@link IntegerStorage}
 * Returns read-only values.
 * 
 * @author Patrik Okanovic
 *
 */
public class IntegerStorageChange {
	
	/**
	 * Reference to the {@link IntegerStorage}
	 */
	private IntegerStorage istorage;
	
	/**
	 * Saves the previous value
	 */
	private int previousValue;
	/**
	 * Constructor of the {@link IntegerStorageChange} 
	 * 
	 * @param istorage
	 * @param previousValue
	 */
	public IntegerStorageChange(IntegerStorage istorage, int previousValue) {
		this.istorage = istorage;
		this.previousValue = previousValue;
	}

	/**
	 * Returns the previousValue before the change
	 * 
	 * @return previousValue
	 */
	public int getPreviousValue() {
		return previousValue;
	}
	
	/**
	 * Returns the reference to the {@link IntegerStorage}
	 * 
	 * @return integerStorage
	 */
	public IntegerStorage getIntegerStorage() {
		return istorage;
	}
	
	/**
	 * Returns new value of the {@link IntegerStorage}
	 * 
	 * @return newValue
	 */
	public int getNewValue() {
		return istorage.getValue();
	}

	
	

}
