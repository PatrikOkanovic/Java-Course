package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Represents a collection of prime numbers, which does not use any multiple element storage.
 * 
 * @author Patrik Okanovic
 *
 */
public class PrimesCollection implements Iterable<Integer>{

	/**
	 * Number of prime numbers in the collection
	 */
	private int size;
	
	/**
	 * Constructor of the {@link PrimesCollection}
	 * 
	 * @param numOfPrimes
	 */
	public PrimesCollection(int numOfPrimes) {
		this.size = numOfPrimes;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator();
	}
	
	/**
	 * Used to iterate on the {@link PrimesCollection}
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {

		/**
		 * Saves the number of primes which have been returned
		 */
		private int numOfReturnedPrimes;
		
		/**
		 * Saves the last returned prime number
		 */
		private int previousPrime = 1;
		
		@Override
		public boolean hasNext() {
			return numOfReturnedPrimes < size;
		}

		@Override
		public Integer next() {
			if(! hasNext()) {
				throw new NoSuchElementException("No more primes in the collection");
			}
			
			while(true) {
				previousPrime++;
				if(checkIsPrime(previousPrime)) {
					break;
				}
			}
			numOfReturnedPrimes++;
			return previousPrime;
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

}
