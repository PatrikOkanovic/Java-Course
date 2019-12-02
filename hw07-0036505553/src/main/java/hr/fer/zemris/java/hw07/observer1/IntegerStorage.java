package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;
/**
 * Class used to explain the Observer design pattern. This class represents the Subject
 * whose changes we are watching.
 * 
 * @author Patrik Okanovic
 *
 */
public class IntegerStorage {
	/**
	 * Value which is changing
	 */
	private int value;
	/**
	 * Stored observers
	 */
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!

	/**
	 * Constructor of the Subject, sets the value to the initialValue
	 * 
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Adds the observer to the Subject if there is no such Observer already
	 * 
	 * @param observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(! observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes the observer from the list of observers if there is such a observer watching.
	 * 
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Removes all observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Return the value
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of the Subject to the given value only 
	 * if it is different from the current value
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				List<IntegerStorageObserver> copyOfListeners = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : copyOfListeners) {
					observer.valueChanged(this);
				}
			}
		}
	}
}

