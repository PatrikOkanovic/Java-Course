package hr.fer.zemris.java.hw02;

/**
 * Represents an implementation for working with complex numbers.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class ComplexNumber {
	private static final double DIFFERENCE = 10E-7;
	/**
	 * Real part of the complex number.
	 */
	private double real;
	/**
	 * Imaginary part of the complex number.
	 */
	private double imaginary;
	/**
	 * Magnitude of the given number.
	 */
	private double magnitude;
	/**
	 * Angle of the given number.
	 */
	private double angle;
	
	/**
	 * Constructor for creating a complex number.
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		magnitude = Math.sqrt(Math.pow(real, 2)+Math.pow(imaginary,2));
		angle = Math.atan(imaginary/real);
		if((real < 0 && imaginary > 0) || (real < 0 && imaginary < 0)) {
			angle += Math.PI;
		}
		if(angle < 0) {
			angle += Math.PI * 2;
		}
	}
	
	/**
	 * Creates a ComplexNumber from real part.
	 * @param real
	 * @return
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0); 
	}
	
	/**
	 * Creates a ComplexNumber from imaginary part.
	 * @param real
	 * @return
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary); 
	}
	/**
	 * Creates a ComplexNumber from magnitude and angle.
	 * @param real
	 * @return
	 * @throws IllegalArgumetnException if magnitude is smaller than 0
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0) {
			throw new IllegalArgumentException();
		}
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	/**
	 * Creates a ComplexNumber from the given string.
	 * @param s input string
	 * @return ComplexNumber of the string if possible
	 * @throws NullPointerException if the argument is null
	 * @throws IllegalArgumentException if the string is not a valid input
	 */
	public static ComplexNumber parse(String s) {
		if(s == null) {
			throw new NullPointerException();
		}
		String withoutSpaces = s.replace(" ", "");
		if(withoutSpaces.contains("+-") || withoutSpaces.contains("-+") || withoutSpaces.contains("--") || withoutSpaces.contains("++")) {
			throw new IllegalArgumentException();
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
					throw new IllegalArgumentException();
				}
			}
		}
		String withoutSigns = withoutSpaces.replace("+", " ").replace("-", " ");
		String[] parts = withoutSigns.trim().split(" ");
		if(parts.length == 1) { //if there is only one number
			if(parts[0].contains("i")) {
				if(parts[0].charAt(parts[0].length()-1) == 'i') {
					if(parts[0].length() == 1) { // only i or -i
						imaginary = 1.0;
					} else {
						try {
							imaginary = Double.parseDouble(parts[0].substring(0, parts[0].length()-1));
						} catch(NumberFormatException exc) {
							throw new IllegalArgumentException();
						}
					}
				} else {
					throw new IllegalArgumentException();
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
					imaginary = Double.parseDouble(parts[1].substring(0, parts[1].length()-1));
				}
			} catch(NumberFormatException exc) {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
		//setting signs for real and imaginary part
		if(numSigns == 1 && signs[0] == '-') {
			imaginary *= -1;
		} else {
			if(signs[0] == '-') {
				real *= -1;
			}
			if(signs[1] == '-') {
				imaginary *= -1;
			}
		}
		
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * @return the real
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @return the imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		String s = "" + real;
		if(imaginary >= 0) {
			s += "+";
		}
		return s + imaginary + "i";
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return (real - other.real) < DIFFERENCE && (imaginary - other.imaginary) < DIFFERENCE;
	}

	/**
	 * Creates a new ComplexNumber which is the result of operation adding.
	 * @param c second operand of the operation
	 * @return sum of the two complex numbers
	 * @throws NullPointerException if the argument is null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException();
		}
		double real = this.real + c.real;
		double imaginary = this.imaginary + c.imaginary;
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Creates a new ComplexNumber which is the result of operation difference.
	 * @param c second operand of the operation
	 * @return difference of the two complex numbers
	 * @throws NullPointerException if the argument is null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException();
		}
		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Creates a new ComplexNumber which is the result of operation multiplying.
	 * @param c second operand of the operation
	 * @return multiply of the two complex numbers
	 * @throws NullPointerException if the argument is null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException();
		}
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Creates a new ComplexNumber which is the result of operation dividing.
	 * @param c seconds operand of the operation
	 * @return division of the two complex numbers
	 * @throws NullPointerException if the argument is null
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new NullPointerException();
		}
		double denominator = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		double real = (this.real * c.real + this.imaginary * c.imaginary) / denominator;
		double imaginary = (this.imaginary * c.real - this.real * c.imaginary) / denominator;
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Creates a new ComplexNumber which is the result of powering the complex number.
	 * @param n power of the complex number
	 * @return complex number powered by n
	 * @throws IllegalArgumentException if n is smaller than 0
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		double real = Math.pow(this.getMagnitude(), n) * Math.cos(n * this.getAngle());
		double imaginary = Math.pow(this.getMagnitude(), n) * Math.sin(n * this.getAngle());
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Creates a field of ComplexNumber which is the result of operation root.
	 * @param n the root we want to calculate
	 * @return root of the complex number
	 * * @throws IllegalArgumentException if n is smaller or equal than 0
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] field = new ComplexNumber[n];
		for(int i = 0; i < n; i++) {
			double real = Math.pow(magnitude, 1.0/n) * Math.cos((angle + 2*i*Math.PI) / n);
			double imaginary = Math.pow(magnitude, 1.0/n) * Math.sin((angle + 2*i*Math.PI) / n);
			field[i] = new ComplexNumber(real, imaginary);
		}
		
		return field;
	}
	
	
	
	
}
