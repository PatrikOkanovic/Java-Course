package hr.fer.zemris.java.fractals;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * Runs {@link NewtonProducer}, implemented to show Newton-Raphson iteration.
 * User enters "done" after inputing all the complex numbers.
 * 
 * @author Patrik Okanovic
 *
 */
public class Newton {

	/**
	 * Main method of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> list;
		
		try {
			list = scan();
		} catch(IllegalArgumentException | NullPointerException  exc) {
			System.out.println("Error while scanning");
			System.out.println(exc.getMessage());
			return;
		}
//		System.out.println("lista : " +list.toString());
		
		Complex[] array = list.toArray(new Complex[0]);
		
		ComplexRootedPolynomial polynom = new ComplexRootedPolynomial(Complex.ONE, array);
		
		
		FractalViewer.show(new NewtonProducer(polynom));
		
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		
	}
	
	/**
	 * Method implemented for scanning complex numbers.
	 * 
	 * @return
	 */
	private static List<Complex> scan() {
		List<Complex> list = new ArrayList<>();
		try(Scanner sc = new Scanner(System.in)) {
			int counter = 1;
			while(true) {
				System.out.printf("Root %d> ",counter++);
				String line = sc.nextLine();
				if(line.equals("done")) {
					break;
				}
				Complex number = parse(line);
				list.add(number);
			}
		}
		return list;
	}
	
	/**
	 * Creates a ComplexNumber from the given string.
	 * 
	 * @param s input string
	 * @return ComplexNumber of the string if possible
	 * @throws NullPointerException if the argument is null
	 * @throws IllegalArgumentException if the string is not a valid input
	 */
	public static Complex parse(String s) {
		if(s == null) {
			throw new NullPointerException();
		}
		String[] forChecking = s.trim().split(" ");
		if(forChecking.length>1) {
			if(!s.contains("+") && !s.contains("-")) {
				throw new IllegalArgumentException("a ib is not acceptable");
			}
		}
		String withoutSpaces = s.replace(" ", "");
		if(withoutSpaces.contains("+-") || withoutSpaces.contains("-+") || withoutSpaces.contains("--") || withoutSpaces.contains("++")) {
			throw new IllegalArgumentException("Cannot contain multiple signs together.");
		}
		double real = 0;
		double imaginary = 0;
		
		
		char[] signs = new char[2];
		int numSigns = 0;
		for(char c : withoutSpaces.toCharArray()) {
			if(c == '+' || c == '-') {
				try {
					signs[numSigns++] = c;
				} catch(IndexOutOfBoundsException exc) {
					//more than two in a row signs
					throw new IllegalArgumentException("Multiple signs.");
				}
			}
		}
		String withoutSigns = withoutSpaces.replace("+", " ").replace("-", " ");
		String[] parts = withoutSigns.trim().split(" ");
		if(parts.length == 1) { //if there is only one number
			if(parts[0].contains("i")) {
				if(parts[0].startsWith("i")) {
					if(parts[0].length() == 1) { // only i or -i
						imaginary = 1.0;
					} else {
						try {
							imaginary = Double.parseDouble(parts[0].substring(1, parts[0].length()));
						} catch(NumberFormatException exc) {
							throw new IllegalArgumentException("Unparseable imaginary");
						}
					}
				} else {
					throw new IllegalArgumentException("i must be on the first place");
				}
			} else {
				real = Double.parseDouble(parts[0]);
			}
		} else if(parts.length == 2) {
			
			try {
				if(!parts[1].contains("i")) {
					throw new IllegalArgumentException();
				}
				real = Double.parseDouble(parts[0]);
				if(parts[1].length() == 1) { // only i or -i
					imaginary = 1.0;
				} else {
					imaginary = Double.parseDouble(parts[1].substring(1, parts[1].length()));
				}
			} catch(NumberFormatException exc) {
				throw new IllegalArgumentException("i must be on the first place");
			}
		} else {
			throw new IllegalArgumentException("Number must be a + ib");
		}
		//setting signs for real and imaginary part
		if(numSigns == 1 && signs[0] == '-') {
			real *= -1;
			imaginary *= -1;
		} else {
			if(signs[0] == '-') {
				real *= -1;
			}
			if(signs[1] == '-') {
				imaginary *= -1;
			}
		}
		
		return new Complex(real, imaginary);
	}
	
}
