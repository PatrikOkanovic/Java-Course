package hr.fer.zemris.java.custom.collections;
/**
 * Implementation of linked list-backed collection of objects.
 * General contract of this collection is: duplicate elements are allowed, storage of null references is not allowed.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class LinkedListIndexedCollection extends Collection{
	/**
	 * Helping class which represents a node in the list implementation of the collection.
	 */
	private static class ListNode {
		ListNode previous, next;
		Object value;
	}
	/**
	 * Reference to the first node in the list.
	 */
	private ListNode first;
	/**
	 * Reference to the last node in the list.
	 */
	private ListNode last;
	/**
	 * Holds the size of the collection in the moment.
	 */
	private int size;
	/**
	 * Creates an empty collection.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
	}
	/**
	 * Creates a collection with copied elements from the given collection.
	 * @throws NullPointerException if collection is null
	 */
	public LinkedListIndexedCollection(Collection collection) {
		if(collection == null) {
			throw new NullPointerException();
		}
		for(Object object : collection.toArray()) {
			add(object);
		}
	}
	/**
	 * Adds the given object into this collection at the end of collection, newly added element becomes the
	 * element at the biggest index.
	 * @param value to put in the collection
	 * @throws NullPointerException if the given value is null
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException();
		}
		ListNode newNode = new ListNode();
		newNode.value = value;
		if(size == 0) {
			first = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
		}
		last = newNode;
		size++;
	}
	/**
	 * Returns the object that is stored in linked list at position index. Valid indexes are 0 to size-1. 
	 * @param index
	 * @return object at the given index
	 * @throws IndexOutOfBondException if the index is not in 0 to size-1
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode tmp;
		if(index < size/2) {
			tmp = first;
			for(int i = 0; i < index; i++) {
				tmp = first.next;
			}
		} else {
			tmp = last;
			for(int i = size - 1; i > index; i--) {
				tmp = tmp.previous;
			}
		}
		return tmp.value;
	}
	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
	 * not found. Null is a valid value.
	 * @param value 
	 * @return index of the occurrence
	 */
	public int indexOf(Object value) {
		ListNode tmp = first;
		for(int i = 0; i < size; i++) {
			if(tmp.value.equals(value)) {
				return i;
			}
			tmp = tmp.next;
		}
		return -1;
	}
	/**
	 * Inserts (does not overwrite) the given value in the collection. All the elements remain 
	 * untouched. Legal positions are from 0 to size.
	 * @param value
	 * @param position from 0 to size
	 * @throws NullPointerException if value is a null reference
	 * @throws IndexOutOfBonds if poisiton is not in 0 to size
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException();
		}
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		if(position == 0) {
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
			if(size == 0) {
				last = newNode;
			}
		} else if(position == size) {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
			if(size == 0) {
				first = newNode;
			}
		} else {
			ListNode tmp = first;
			for(int i = 0; i < position - 1; i++) {
				tmp = tmp.next;
			}
			newNode.previous = tmp;
			newNode.next = tmp.next;
			tmp.next = newNode;
			newNode.next.previous = newNode;
		}
		size++;
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
		if(size == 1) {
			first = null;
			last = null;
		} else if(index == 0) {
			first = first.next;
			first.previous = null;
		} else if(index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode tmp = first;
			for(int i = 0; i < index; i++) {
				tmp = tmp.next;
			}
			tmp.previous.next = tmp.next;
			tmp.next.previous = tmp.previous;
		}
		size--;
	}
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) {
		ListNode tmp = first;
		for(int i = 0; i < size; i++) {
			if(tmp.value.equals(value)) {
				return true;
			}
			tmp = tmp.next;
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
		ListNode tmp = first;
		Object[] returnArray = new Object[size];
		for(int i = 0; i < size; i++) {
			returnArray[i] = tmp.value;
			tmp = tmp.next;
		}
		return returnArray;
	}
	
	@Override
	public void forEach(Processor processor) {
		Object[] array = toArray();
		for(Object object : array) {
			processor.process(object);
		}
	}


}
