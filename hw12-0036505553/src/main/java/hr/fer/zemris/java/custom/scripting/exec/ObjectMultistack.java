package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  Allows the user to store multiple values
 *  for same key and it provides a stack-like abstraction. Keys are Strings
 *  and value is a ValueWrapper which can be any Object-
 * 
 * @author Patrik Okanovic
 *
 */
public class ObjectMultistack {

	/**
	 * Storage of keys, maps the key to the value on top of the stack.
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();
	
	/**
	 * Pushes the value on top of the stack for the given key
	 * 
	 * @param keyName
	 * @param valueWrapper
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(valueWrapper);
		
		if(! map.containsKey(keyName)) {
			map.put(keyName, new MultistackEntry(valueWrapper, null));
		} else {
			MultistackEntry entry = map.get(keyName);
			if(entry == null) {
				map.put(keyName, new MultistackEntry(valueWrapper, null));
			} else {
				map.put(keyName, new MultistackEntry(valueWrapper,entry));
			}
		}
		
	}
	/**
	 * Pops the value from the stack for the given key
	 * 
	 * @param keyName
	 * @return {@link ValueWrapper}
	 * @throws EmptyStackException 
	 */
	public ValueWrapper pop(String keyName) {
		if(! map.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		
		MultistackEntry entry = map.get(keyName);
		
		if(entry == null) {
			throw new EmptyStackException();
		}
		
		map.put(keyName, entry.next);
		
		return entry.value;
		
	}
	
	/**
	 * Pops the element from {@link MultistackEntry}
	 * 
	 * @param keyName
	 * @return {@link ValueWrapper}
	 * @throws EmptyStackException
	 */
	public ValueWrapper peek(String keyName) {
		if(! map.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		
		MultistackEntry entry = map.get(keyName);
		
		if(entry == null) {
			throw new EmptyStackException();
		}
		
		return entry.value;
	}
	public boolean isEmpty(String keyName) {
		
		return map.get(keyName) == null;
	}
	
	
	/**
	 * Used internally to map String keys with {@link ValueWrapper} 
	 * implements a single-linked list
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private static class MultistackEntry {
		/**
		 * The value stored
		 */
		ValueWrapper value;
		/**
		 * Reference to the next element in a single-linked list
		 */
		MultistackEntry next;
		
		/**
		 * The constructor of {@link MultistackEntry}
		 * @param value
		 * @param next
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
	}

}
