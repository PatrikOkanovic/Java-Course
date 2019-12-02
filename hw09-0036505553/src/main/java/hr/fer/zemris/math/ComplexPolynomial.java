package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
/**
 * Models a polynom over complex numbers based on the format:
 * f(z) = zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0
 * 
 * @author Patrik Okanovic
 *
 */
public class ComplexPolynomial {

	/**
	 * List of factors
	 */
	private List<Complex> factors;
	/**
	 * The constructor of the class.
	 * Order of factors from left to right is  z0, z1, z2,...
	 * 
	 * @param factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = new ArrayList<>();
		
		for(Complex factor : factors) {
			this.factors.add(factor);
		}
	}
	
	/**
	 * Returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * 
	 * @return order of the polynom
	 */
	public short order() {
		return (short)(factors.size() - 1);
	}
	
	/**
	 * Computes a new polynomial this*p
	 * 
	 * @param p
	 * @return complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] returnField = new Complex[order() + p.order() + 1];
		int length = order() + p.order() + 1;
		
		for(int i = 0; i < length; i++) {
			returnField[i] = Complex.ZERO;
		}
		
		for(int i = 0; i < factors.size(); i++) {
			for(int j = 0; j < p.factors.size(); j++) {
				returnField[i+j] = returnField[i+j].add(factors.get(i).multiply(p.factors.get(j)));
			}
		}
		
		return new ComplexPolynomial(returnField);
	}
	
	/**
	 * computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return {@link ComplexPolynomial}
	 */
	public ComplexPolynomial derive() {
		int order = order();
		Complex[] returnFactors = new Complex[order];
		
		for(int i = 0; i < order; i++) {
			returnFactors[i] = factors.get(i+1).multiply(new Complex(i+1,0));
		}
		
		return new ComplexPolynomial(returnFactors);
	}
	
	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z
	 * @return calculated complex number
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for(int i = 0; i < factors.size(); i++) {
			result = result.add(z.power(i).multiply(factors.get(i)));
		}
		return result;
	}
	
	@Override
	public String toString() {
		int n = factors.size() - 1;
		StringBuilder sb = new StringBuilder();
		
		for(int i = n; i >=0; i--) {
			sb.append(factors.get(i).toString());
			if(i != 0) {
				sb.append("*z^" + i +"+");
			}
		}
		
		return sb.toString();
	}

}
