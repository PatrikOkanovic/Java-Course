package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class represents an implementation of the map as a hash table. Keys cannot be null,
 * but values can be null.
 * @author Patrik Okanovic
 *
 * @param <K> 
 * @param <V>
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	/**
	 * Size of the map.
	 */
	private int size;
	/**
	 * Internally save the data for the map.
	 */
	private TableEntry<K,V>[] table;
	/**
	 * Used to save number of saved slots and to check the percentage of filled hash table
	 */
	private int numOfFilledSlots;
	/**
	 * Remebers the number of modifications made on the table
	 */
	private int modificationCount;
	
	public static final int DEFAULT_CAPACITY = 16;
	/**
	 * Default constructor with the capacity set to 16.
	 */
	public SimpleHashtable(){
		this(DEFAULT_CAPACITY);
	}
	/**
	 * Creates a {@link SimpleHashtable} with the capacity of the next
	 *  first bigger or equal potency of number 2.
	 *
	 * @param capacity
	 * @throws IllegalArgumentException if the capacity is smaller than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity){
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be smaller than 1.");
		}
		
		table =(TableEntry<K,V>[]) new TableEntry[nextExpo(capacity)];
	}
	/**
	 * Add the given key with his value to the hash table. If
	 * the key already exists then rewrite the value.
	 * 
	 * @param key
	 * @param value
	 * @throws NullPointerException if key is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		int index = Math.abs(key.hashCode()) % table.length;
		
		if(table[index] == null) {
			table[index] = new TableEntry<K,V>(key, value);
			size++;
			numOfFilledSlots++;
			modificationCount++;
			if(calculateFillingPercentage() >= 0.75) {
				expandTableCapacity();
			}
			return;
		}
		
		TableEntry<K, V> tmp = table[index];
		while(tmp.next != null && !tmp.getKey().equals(key)) {
			tmp = tmp.next;
		}
		
		if(tmp.getKey().equals(key)) {
			tmp.setValue(value);
		} else {
			tmp.next = new TableEntry<K,V>(key, value);
			size++;
			modificationCount++;
		}
	}
	/**
	 * Expands capacity of the table by two when the filling percentage of slots becomes 
	 * bigger or equal than 75%.
	 */
	@SuppressWarnings("unchecked")
	private void expandTableCapacity() {
		TableEntry<K, V>[] tableOfAllElements = (TableEntry<K, V> []) new  TableEntry[size];
		int br = 0;
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> tmp = table[i];
			while(tmp != null) {
				tableOfAllElements[br++] = tmp;
				tmp = tmp.next;
			}
		}
		size = 0;
		numOfFilledSlots = 0;
		int newCapacity = table.length * 2;
		table = (TableEntry<K, V> []) new  TableEntry[newCapacity];
		for(TableEntry<K, V> tmp : tableOfAllElements) {
			put(tmp.getKey(),tmp.getValue());
		}
		
	}
	/**
	 * Calculates the percentage of the filled slots of the hash table.
	 * 
	 * @return percentage of filling
	 */
	private double calculateFillingPercentage() {
		return ((double) numOfFilledSlots) / table.length;
	}
	/**
	 * Return the value for the given key in the table. If the given key
	 * doesn't exist then null is returned. It is allowed for key to be null because
	 * there is no such key in the hash table to be null.
	 * 
	 * @param key
	 * @return value
	 */
	public V get(Object key) {
		if(!containsKey(key)) {
			return null;
		}
		
		int index = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> tmp = table[index];
		while(!tmp.getKey().equals(key)) {
			tmp = tmp.next;
		}
		
		return tmp.getValue();
	}
	/**
	 * Number of pairs in the hash table.
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}
	/**
	 * Returns boolean whether the hash table contains key or not.
	 * It is possible that the key is null, but it will always be false because there
	 * cannot be a null key.
	 * 
	 * @param key
	 * @return true whether it contains the key
	 */
	public boolean containsKey(Object key) {
		int index = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> tmp = table[index];
		while(true) {
			if(tmp == null) {
				return false;
			} else if(tmp.getKey().equals(key)) {
				return true;
			}
			tmp = tmp.next;
		}
	}
	/**
	 * Returns boolean whether the hash table contains value or not.
	 * It is possible that the value is null.
	 * 
	 * @param key
	 * @return true if it contains the value
	 */
	public boolean containsValue(V value) {
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> tmp = table[i];
			while(tmp != null) {
				if(tmp.getValue().equals(value)) {
					return true;
				}
				tmp = tmp.next;
			}
		}
		return false;
	}
	/**
	 * Remove the given key and his value from the hash table.
	 * Doesn't do anything if the key doesn't exist in the table.
	 * 
	 * @param key
	 */
	public void remove(Object key) {
		if(!containsKey(key)) {
			return;
		}
		
		int index = Math.abs(key.hashCode()) % table.length;
		//if it is on the first spot of table we must remove the reference from the table to the next TablEntry
		if(table[index].getKey().equals(key)) { 
			table[index] = table[index].next;
			
		} else {
			
			TableEntry<K, V> tmp = table[index].next;
			TableEntry<K, V> old = table[index];
			
			while(tmp != null) {
				if(tmp.getKey().equals(key)) {
					old.next = tmp.next;
					break;
				}
				old = tmp;
				tmp = tmp.next;
			}
		}
		size--;
		modificationCount++;
	}
	
	/**
	 * Checks if the hash table is empty
	 * @return true if is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int numOfAddedElements = 0;
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> tmp = table[i];
			while(tmp != null) {
				sb.append(tmp.toString());
				numOfAddedElements++;
				if(numOfAddedElements != size) {
					sb.append(", ");
				}
				tmp = tmp.next;
			}
		}
		
		sb.append("]");
		return sb.toString();
	}
	/**
	 * Clears the hash table. Does not change the capacity of the hash table.
	 */
	public void clear() {
		size = 0;
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
	}
	/**
	 * Calculate the first bigger or equal potency of number 2 from the 
	 * given number.
	 * 
	 * @param x
	 * @return 
	 */
	private int nextExpo(int x) {
		return (int) Math.pow(2, Math.ceil(Math.log(x) / Math.log(2)));
	}
	
	/**
	 * Represents an slot of the HashTable. Key cannot be null, but the value can
	 * be null.
	 * @author Patrik Okanovic
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static class TableEntry<K,V>{
		/**
		 * Key of the TableEntry
		 */
		private K key;
		/**
		 * Value of the TableEntry
		 */
		private V value; 
		/**
		 * Pointer on the next slot in the hash table
		 */
		private TableEntry<K,V> next = null;
		/**
		 * Creates an TableEntry
		 * @param key
		 * @param value
		 * @throws NullPointerException if key is null
		 */
		public TableEntry(K key, V value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
			
		}
		/**
		 * Get the key
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		/**
		 * Get the value
		 * @return value of the slot
		 */
		public V getValue() {
			return value;
		}
		/**
		 * Set the value
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}
	/**
	 * Implementation of the Iterator so that the hash table would be iterable
	 * @author Patrik Okanovic
	 *
	 */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {
		/**
		 * Saves the cuurentIndex of the slot in the table
		 */
		private int currentIndex;
		/**
		 * Last returned element in the iterator, null if the next() has not yet been called
		 */
		private TableEntry<K, V> currentElement;
		/**
		 * Remembers modificationCount from when the Iterator has been created
		 */
		private int iteratorModificationCount;
		/**
		 * Checks if there is still any elements that have not been iterated.
		 */
		private int elementsToIterate;
		
		/**
		 * Creates an instance of the IteratorImpl
		 * @param modificationCount
		 */
		public IteratorImpl(int modificationCount) {
			this.iteratorModificationCount = modificationCount;
			elementsToIterate = size;
		}
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if the table is modified without iterator
		 */
		@Override
		public boolean hasNext() {
			if(iteratorModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Hashtable has been modified without iterator");
			}
			return elementsToIterate > 0;
		}

		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if the table is modified without iterator
		 * @throws NoSuchElementException when there is no more elements to return
		 */
		@Override
		public TableEntry<K, V> next() {
			if(iteratorModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Hashtable has been modified without iterator");
			}
			if(elementsToIterate == 0) {
				throw new NoSuchElementException("No more elements to iterate");
			}
			if(currentElement == null) { // only in first iteration
				while(currentElement == null) {
					currentElement = table[currentIndex++];
				}
			} else {
				currentElement = currentElement.next;
				if(currentElement == null) {
					while(currentElement == null) {
						currentElement = table[currentIndex++];
					}
				}
			}
			elementsToIterate--;
			return currentElement;				
		}
		
		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if the table is modified without iterator
		 * @throws IllegalStateException if the method next has not been called yet or the node has already been erased
		 */
		@Override
		public void remove() {
			if(iteratorModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Hashtable has been modified without iterator");
			}
			if(currentElement == null) {
				throw new IllegalStateException("Method next() has not been called yet");
			}
			int oldSize = size;
			SimpleHashtable.this.remove(currentElement.getKey());
			
			//if the element has already been removed and nothing has happened
			if(oldSize == size) {
				throw new IllegalStateException("Element has already been removed");
			}
			iteratorModificationCount++;
	
		}
		
	}
}
