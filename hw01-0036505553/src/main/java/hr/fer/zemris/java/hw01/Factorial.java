package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The program calculates the factorial of numbers  from 3 to 20.
 * String "kraj" kills the program.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class Factorial {
	/**
	 * Method is called when starting the program.
	 * Main program calculates the factorial of a number in interval [3,20]. Programm finishes
	 * when input "kraj" is scanned.
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.format("Unesite broj > ");
			if(sc.hasNext()) {
				String input = sc.next();
				try{
					int number = Integer.parseInt(input);
					if(number < 3 || number > 20) {
						System.out.println("\'" + input + "\' nije broj u zadanome intervalu.");
						continue;
					}
					long factorial = calculateFactorial(number);
					System.out.println(number + "! = " + factorial );
					
				} catch(NumberFormatException exc) {
					if(input.equals("kraj")) {
						break;
					}
					System.out.println("\'" + input + "\' nije cijeli broj.");
				}
			}
			
		}
		sc.close();
	}
		
	
	/**
	 * The method calculates factorial from the number in [0,20].
	 * @param number whose factorial is calculated
	 * @throws IllegalArgumentException if the factorial isn't in [0,20] or isn't
	 * @return result which is the factorial of the given number
	 */
	public static long calculateFactorial(int number) {
		if(number < 0 || number > 20) {
			throw new IllegalArgumentException();
		}
		long result = 1;
		for(int i = 1; i <= number; i++) {
			result *= i;
		}
		return result;
	}

}
