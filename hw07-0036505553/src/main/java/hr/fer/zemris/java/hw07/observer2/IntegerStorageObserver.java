package hr.fer.zemris.java.hw07.observer2;
/**
 * Interface representing the Abstract Observer in the Observer design pattern.
 * 
 * @author Patrik Okanovic
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method called for each Observer when the Subject has been modified.
	 * 
	 * @param istorage
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
