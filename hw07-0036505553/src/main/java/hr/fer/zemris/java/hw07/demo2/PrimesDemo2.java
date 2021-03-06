package hr.fer.zemris.java.hw07.demo2;
/**
 * Demo program to show that {@link PrimesCollection} works with nested for.
 * 
 * @author Patrik Okanovic
 *
 */
public class PrimesDemo2 {

	/**
	 * Main method of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
