package hr.fer.zemris.java.hw07.demo2;
/**
 * Demo example to show how {@link PrimesCollection} works
 * 
 * @author Patrik Okanovic
 *
 */
public class PrimesDemo1 {

	/**
	 * Main method of the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		
		for (Integer prime : primesCollection) {
			
			System.out.println("Got prime: " + prime);
		}
	}

}
