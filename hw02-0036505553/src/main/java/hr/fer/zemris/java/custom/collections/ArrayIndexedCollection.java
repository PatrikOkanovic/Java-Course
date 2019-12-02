package hr.fer.zemris.java.custom.collections;


/**
 *  Implementation of resizeable array-backed collection of objects. Duplicate elements are
 *  allowed. Null reference is not allowed in the collection. 
 * @author Patrik Okanovic
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	private final static int STARTING_CAPACITY = 16;
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
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	/**
	 * Method returns capacity of the current collection.
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
}
