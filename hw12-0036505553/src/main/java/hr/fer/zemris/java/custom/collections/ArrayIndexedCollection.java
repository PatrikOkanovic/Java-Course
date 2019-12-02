package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 *  Implementation of resizeable array-backed collection of objects. Duplicate elements are
 *  allowed. Null reference is not allowed in the collection. 
 * @author Patrik Okanovic
 * @version 1.0
 */
public class ArrayIndexedCollection implements List {
	private final static int STARTING_CAPACITY = 16;

	/**
	 * Counts if collection has been modified by expanding the field of objects or
	 * moving elements to the right or to the left.
	 */
	private long modificationCount = 0;
	/**
	 * Holds the size of the collection in the moment.
	 */
	private int size;
	/**
	 * Stores elements for the collection.
	 */
	private Object[] elements;
	/**
	 * Shows how many elements can collection fit.
	 */
	private int capacity;
	/**
	 * Constructor who preallocates field of objects to the starting capacity which is 16.
	 */
	public ArrayIndexedCollection() {
		this(STARTING_CAPACITY);
	}
	/**
	 * Preallocates elements field to the initialCapacity.
	 * @param initialCapacity
	 * @throws IllegalArgumentException if initialCapacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
		capacity = initialCapacity;
	}
	/**
	 * Creates a new collection with copied elements from the given collection.
	 * @param other Collection to be copied in the new collection.
	 * @throws NullPointException if the given collection is a null reference
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, STARTING_CAPACITY);
	}
	/**
	 * Creates a new collection with copied elements from the given collection and
	 * with the initalCapacity
	 * @param other
	 * @param initialCapacity
	 * @throws NullPointException if the given collection is a null reference
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) {
			throw new NullPointerException();
		}
		capacity = initialCapacity < other.size() ? other.size() : initialCapacity;
		elements = new Object[capacity];
		for(Object object : other.toArray()) {
			add(object);
		}
	}
	/**
	 * Adds the given object into this collection.
	 * @param value to put in the collection
	 * @throws NullPointerException if the given value is null
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(size == capacity) {
			expandField();
		}
		elements[size] = value;
		modificationCount++;
		size++;
	}
	/**
	 * Helping method for expanding field capacity by 2 and copying elements.
	 */
	private void expandField() {
		capacity *= 2;
		Object[] tmp = elements;
		elements = new Object[capacity];
		for(int i = 0; i < tmp.length; i++) {
			elements[i] = tmp[i];
		}
		modificationCount++;
		
	}
	/**
	 * Returns the object that is stored in backing array at position index.
	 *  Valid indexes are 0 to size-1.
	 * @param index
	 * @return object at the given index
	 * @throws IndexOutOfBoundsException if index is not valid
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
	 * not found.
	 * @param value 
	 * @return index of the occurrence
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if(value.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection.
	 * @param index
	 * @throws IndexOutOfBoundsException if index is not in interval 0 to size-1
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index; i < size - 1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		size = size - 1;
		modificationCount++;
	}
	/**
	 * Inserts (does not overwrite) the given value in the collection. All the elements remain 
	 * untouched. Legal positions are from 0 to size.
	 * @param value
	 * @param position from 0 to size
	 * @throws NullPointerException if value is a null reference
	 * @throws IndexOutOfBonds if position is not in 0 to size
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if(size == capacity) {
			expandField();
		}
		
		for(int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;
		modificationCount++;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		if(!contains(value)) {
			return false;
		}
		int index = indexOf(value);
		remove(index);
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] returnArray = new Object[size];
		for(int i = 0; i < size; i++) {
			returnArray[i] = elements[i];
		}
		return returnArray;
	}
	
	
	/**
	 * Method returns capacity of the current collection.
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * Returns number of modifications on teh collection.
	 * @return number of modifications
	 */
	public long getModificationCount() {
		return modificationCount;
	}
	
	/**
	 * Implementation of the ElementsGetter used to iterate the ArrayIndexedCollection.
	 * @author Patrik Okanovic
	 *
	 */
	private static class ElementsGetterArray implements ElementsGetter  {
		/**
		 * Reference to the collection, used to check if there has been any modifications.
		 */
		private ArrayIndexedCollection collection;
		/**
		 * Number of elements in the collection at the moment of creating the ElementsGetter
		 */
		private int size;
		/**
		 * Saves the last number of modifications in the collection.
		 */
		private long  savedModificationCount;
		/**
		 * Used to remember the position of the next element to be returned from the collection
		 * in the iteration.
		 */
		private int position = 0;
		/**
		 * Reference to the field of elements from the collection where they are located.
		 */
		private Object[] array;
		/**
		 * Creates an implementation of the ElementsGetter
		 * @param array
		 */
		private ElementsGetterArray(Object[] array, int size, long modificationCount, ArrayIndexedCollection collection) {
			this.array = array;
			this.size = size;
			this.savedModificationCount = modificationCount;
			this.collection = collection;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.getModificationCount()) {
				throw new ConcurrentModificationException();
			}
			return position < size;
		}
		
		@Override
		public Object getNextElement() {
			if(savedModificationCount != collection.getModificationCount()) {
				throw new ConcurrentModificationException();
			}
			if(position >= size) {
				throw new NoSuchElementException();
			}
			return array[position++];
		}
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterArray(elements,size, modificationCount, this);
	}
	
}
