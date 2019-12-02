package hr.fer.zemris.java.custom.collections.demo;



import java.util.NoSuchElementException;

import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
/**
 * First demo programm from the first problem.
 * @author patri
 *
 */
public class ElementsGetterDemo1 {

	public static void main(String[] args) {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch(NoSuchElementException exc) {
			System.out.println("Catched NoSuchElementException");
		}
		}
}
