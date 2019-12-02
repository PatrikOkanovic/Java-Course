package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents an implementation for working with complex numbers.
 * 
 * @author Patrik Okanovic
 *
 */
public class Complex {

	/**
	 * Represents a complex zero
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Represents a complex real one, 1 + 0i
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Represents a complex negative real one, -1 + 0i
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Represents a complex imaginary one, 0 + 1i
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Represents a complex imaginary negative one, 0 - 1i
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Real part of the complex number.
	 */
	private double re;
	
	/**
	 * Imaginary part of the complex number
	 */
	private double im;
	
	
	/**
	 * Constructor of the class.
	 * 
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns module of complex number
	 * 
	 * @return module
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	
	/**
	 * Returns current complex number multiplied with c.
	 * 
	 * @param c
	 * @return this*c
	 */
	public Complex multiply(Complex c) {
		
		return new Complex(re*c.re - im*c.im, im*c.re + re*c.im);
	}
	
	
	/**
	 * Returns current complex number divided with c.
	 * 
	 * @param c
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		double denominator = c.re*c.re + c.im*c.im;
		double real = (re*c.re + im*c.im) / denominator;
		double imaginary = (im*c.re - re*c.im) / denominator;
		
		return new Complex(real, imaginary);
	}
	
	
	/**
	 * Returns current complex number added with c.
	 * 
	 * @param c
	 * @return this+c
	 */
	public Complex add(Complex c) {
		return new Complex(re+c.re, im+c.im);
	}
	
	
	/**
	 * Returns current complex number subtracted with c.
	 * 
	 * @param c
	 * @return this-c
	 */
	public Complex sub(Complex c) {
		return new Complex(re-c.re, im-c.im);
	}
	
	
	/**
	 *  Returns negative complex number from the current.
	 *  
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-1*re, -1*im);
	}
	
	
	/**
	 * Returns this^n, n is non-negative integer.
	 * 
	 * @param n
	 * @return power of current complex number
	 * @throws IllegalArgumentException if n is smaller than zero
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		
		double module = this.module();
		
		double angle = getAngle();
		
		double real = Math.pow(module, n) * Math.cos(n * angle);
		double imaginary = Math.pow(module, n) * Math.sin(n * angle);
		return new Complex(real, imaginary);
	}
	
	/**
	 * Used to get the angle of the complex number
	 * 
	 * @return angle
	 */
	private double getAngle() {
		double angle = Math.atan(im/re);
		if((re < 0 && im > 0) || (re < 0 && im < 0)) {
			angle += Math.PI;
		}
		if(angle < 0) {
			angle += Math.PI * 2;
		}
		return angle;
	}
	
	
	/**
	 * Creates a list of ComplexNumber which is the result of operation root.
	 * 
	 * @param n the root we want to calculate
	 * @return root of the complex number
	 * @throws IllegalArgumentException if n is smaller or equal than 0
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		List<Complex> list = new ArrayList<>();
		double module = this.module();
		double angle = getAngle();
		for(int i = 0; i < n; i++) {
			double real = Math.pow(module, 1.0/n) * Math.cos((angle + 2*i*Math.PI) / n);
			double imaginary = Math.pow(module, 1.0/n) * Math.sin((angle + 2*i*Math.PI) / n);
			list.add(new Complex(real,imaginary));
		}
		
		return list;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("(%.1f", re));
		if(im >= 0) {
			sb.append("+");
		} else {
			sb.append("-");
		}
		
		sb.append(String.format("i%.1f)", Math.abs(im)));
		
		return sb.toString();
	}
	
}

