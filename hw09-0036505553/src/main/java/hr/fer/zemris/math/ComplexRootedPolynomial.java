package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a complex rooted polynom with the form : 
 * f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)
 * 
 * @author Patrik Okanovic
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * The constant
	 */
	private Complex constant;
	/**
	 * The roots
	 */
	private List<Complex> roots = new ArrayList<>();
	
	/**
	 * The constructor of the class.
	 * 
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		for(Complex root : roots) {
			this.roots.add(root);
		}
	}
	
	
	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		
		for(Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		
		return result;
	}
	
	
	/**
	 * Converts this representation to {@link ComplexPolynomial} type
	 * 
	 * @return {@link ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynom = new ComplexPolynomial(constant);
		
		for(Complex complex : roots) {
			polynom = polynom.multiply(new ComplexPolynomial(complex.negate(), Complex.ONE));
		}
		
		return polynom;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());
		
		for(Complex root : roots) {
			sb.append("*(z-" + root.toString() + ")");
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1
	 * first root has index 0, second index 1, etc
	 * 
	 * @param z
	 * @param treshold
	 * @return
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double distance = treshold;
		int index = -1;
		
		for(int i = 0; i < roots.size(); i++) {
			double currentDistance = z.sub(roots.get(i)).module();
			if(currentDistance < distance) {
				distance = currentDistance;
				index = i;
			}
		}
		
		return index;
	}
	
	
}
