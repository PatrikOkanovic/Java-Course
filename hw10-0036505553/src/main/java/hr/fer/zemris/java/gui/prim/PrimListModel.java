package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
/**
 * Implements {@link ListModel}. Represents a list of prime numbers.
 * 
 * @author Patrik Okanovic
 *
 */
public class PrimListModel implements ListModel<Integer>{
	/**
	 * Storage for prime numbers.
	 */
	private List<Integer> numbers = new ArrayList<>();
	/**
	 * Storage for {@link ListDataListener}
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	/**
	 * The current prime number
	 */
	private int currentPrime = 1;
	/**
	 * Constructor of the class
	 */
	public PrimListModel() {
		numbers.add(1);
	}

	@Override
	public int getSize() {
		return numbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return numbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}
	/**
	 * Generates the next prime number to be added to the list.
	 */
	public void next() {
		
		while(true) {
			currentPrime++;
			if(checkIsPrime(currentPrime)) {
				break;
			}
		}
		
		numbers.add(currentPrime);
		
		int pos = numbers.size();
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
		
	}
	
	
	/**
	 * Method used to check if number is a prime number. Goes to the sqrt(x) to check if
	 * it is prime.
	 * 
	 * @param x
	 * @return true if the number is prime
	 */
	private boolean checkIsPrime(int x) {
		
		boolean isPrime = true;
		
		int i = 2;
		
		while(i * i <= x) {
			if(x % i == 0) {
				isPrime = false;
				break;
			}
			i++;
		}
		return isPrime;
	}

}
