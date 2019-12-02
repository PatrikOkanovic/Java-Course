package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of the dictionary. A kind of collection which connects a particular key with a value.
 * Key cannot be null, but a value can. No duplicate keys are allowed.
 * @author Patrik Okanovic
 *
 * @param <K> key
 * @param <V> value
 */
public class Dictionary<K,V> {

	/**
	 * Adaptee for the dictionary, saves the records.
	 */
	private ArrayIndexedCollection<Record> collection = new ArrayIndexedCollection<>();
	/**
	 * Helping class to save records for the dictionary.
	 * @author Patrik Okanovic
	 *
	 */
	private static class Record {
		private Object key;
		private Object value;
		/**
		 * Creates an instance of record.
		 * @param key
		 * @param value
		 */
		public Record(Object key, Object value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}
	}
	/**
	 * Checks if the dictionary contains any elements.
	 * @return true if size is 0
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	/**
	 * Returns the number of pairs in the dictionary.
	 * @return size
	 */
	public int size() {
		return collection.size();
	}
	/**
	 * Empties the collections.
	 */
	public void clear() {
		collection.clear();
	}
	/**
	 * Puts the given key and value in the dictionary. Overwrites the existing record.
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) throws NullPointerException {
		ElementsGetter<Record> getter = collection.createElementsGetter();
		Record record;
		while(getter.hasNextElement()) {
			record = getter.getNextElement();
			if(record.key.equals(key)) {
				record.value = value;
				return;
			}
		}
		collection.add(new Record(key, value));
	}
	/**
	 * Returns the value of the given key if the key exists in the collection.
	 * @param key
	 * @return
	 * @throws NoSuchElementException if there exists no such key in the dictionary
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		ElementsGetter<Record> getter = collection.createElementsGetter();
		Record record;
		while(getter.hasNextElement()) {
			record = getter.getNextElement();
			if(record.key.equals(key)) {
				return (V) record.value;
			}
		}
		throw new NoSuchElementException();
		
	}
}
