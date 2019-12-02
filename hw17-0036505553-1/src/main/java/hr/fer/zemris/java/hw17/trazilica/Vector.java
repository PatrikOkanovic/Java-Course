package hr.fer.zemris.java.hw17.trazilica;

/**
 * Represents a model of vector, containing a field of double values.
 * Capable of calculating the cosinus between itself and another vector.
 * 
 * @author Patrik Okanovic
 *
 */
public class Vector {

	/**
	 * Multidimensional field of values
	 */
	private double[] field;
	
	/**
	 * The constructor
	 * 
	 * @param field
	 */
	public Vector(double[] field) {
		this.field = field;
	}

	/**
	 * @return the field
	 */
	public double[] getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(double[] field) {
		this.field = field;
	}

	/**
	 * Calculates the cosinus between two vectors, using formula
	 * dot(v1,v2)/(norm(v1)*norm(v2))
	 * 
	 * @param tfidf the second vector
	 * @return
	 */
	public double cosSimilarity(Vector tfidf) {
		if(this.getField().length != tfidf.getField().length) {
			throw new RuntimeException("For scalar product fields must be the same size");
		}
		double firstNorm = norm(tfidf);
		double secondNorm = norm(this);
		
		double scalarProduct = 0;
		
		for(int i = 0, len = field.length; i < len; i++) {
			scalarProduct += field[i] * tfidf.getField()[i];
		}
		
		return scalarProduct / (firstNorm * secondNorm);
	}

	/**
	 * Calculates the norm of the vector.
	 * 
	 * @param vector
	 * @return double value of the norm
	 */
	private double norm(Vector vector) {
		double sum = 0;
		double[] field = vector.getField();
		for(int i = 0, len = field.length; i < len; i++) {
			sum += Math.pow(field[i], 2);
		}
		
		return Math.sqrt(sum);
	}
}
