package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * The program calculates the area and the perimeter of an rectangle.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class Rectangle {
	/**
	 * Method is called when starting the program.
	 * @param args two positive double numbers or nothing
	 */
	public static void main(String[] args) {
		if(args.length != 0 && args.length != 2) {
			System.out.println("Neispravan unos parametara!");
			return;
		}
		
		double height, width;
		if(args.length == 2) {
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
				if(width <= 0 || height <= 0) {
					System.out.println("Visina i širina moraju biti pozitivni.");
					return;
				}
			} catch (NumberFormatException exc) {
				System.out.println("Neispravni unos argumenata.");
				return;
			}
		} else {
			Scanner sc = new Scanner(System.in);
			width = enterDimension(sc, "širinu");
			height = enterDimension(sc, "visinu");
			sc.close();
		}
		
		double perimeter = calculatePerimeter(width, height);
		double area = calculateArea(width, height);
		
		System.out.println("Pravokutnik širine " + width + " i visine " + height
				+ " ima površinu " + area + " i opseg " + perimeter);
	}
	
	/**
	 * Method calculates the perimeter of an rectangle.
	 * @param width
	 * @param height
	 * @return perimeter
	 */
	public static double calculatePerimeter(double width, double height) {
		return 2 * (width + height);
	}
	
	/**
	 * Method calculates area of an rectangle.
	 * @param width
	 * @param height
	 * @return area
	 */
	public static double calculateArea(double width, double height) {
		return width * height;
	}

	/**
	 * Method is used for scanning height and width until they are positive double numbers.
	 * @param sc scanner for interaction with the user
	 * @param dimension string which represents height or width
	 * @return height or width
	 */
	public static double enterDimension(Scanner sc, String dimension) {
		double result;
		while(true) {
			System.out.format("Unesite " + dimension + " > ");
			if(sc.hasNext()) {
				String input = sc.next();
				try {
					result = Double.parseDouble(input);
					if(result <= 0) {
						System.out.println("Vrijednost mora biti pozitivna.");
						continue;
					}
					break;
				} catch(NumberFormatException exc) {
					System.out.println("\'" + input +"\'" + " se nemože protumačiti kao broj.");
				}
			}
		}
		return result;
	}

}
