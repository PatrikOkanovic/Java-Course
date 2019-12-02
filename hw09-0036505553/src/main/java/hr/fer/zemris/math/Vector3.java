package hr.fer.zemris.math;

/**
 * Class implementing a 3-D Vector. It is an immutable object.
 * 
 * @author Patrik Okanovic
 *
 */
public class Vector3 {

	/**
	 * X component
	 */
	private double x;
	/**
	 * Y component
	 */
	private double y;
	/**
	 * Z component
	 */
	private double z;
	
	/**
	 * The constructor of the class.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	} 
	
	/**
	 * Returns the length of the vector
	 * 
	 * @return
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	} 
	
	/**
	 * Returns the normalized vector.
	 * 
	 * @return Vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x/norm, y/norm, z/norm);
	} 
	
	/**
	 * Adds the given vector to the current
	 * 
	 * @param other
	 * @return new Vector added to the current
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x+other.x , this.y+other.y, this.z+other.z);
	} 
	
	/**
	 * Subtracts the given vector from this one.
	 * 
	 * @param other
	 * @return new Vector subtracted with other
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x-other.x , this.y-other.y, this.z-other.z);
	} 
	
	/**
	 * Returns the scalar product of the current vector and other
	 * 
	 * @param other
	 * @return double 
	 */
	public double dot(Vector3 other) {
		return this.x*other.x + this.y*other.y + this.z*other.z;
	} 
	
	/**
	 * Returns a new Vector which is the result of cross product
	 * of the current vector and other.
	 * 
	 * @param other
	 * @return cross product of the current and other
	 */
	public Vector3 cross(Vector3 other) {
		double xComponent = y*other.z - z*other.y;
		double yComponent = z*other.x - x*other.z;
		double zComponent = x*other.y - y*other.x;
		
		return new Vector3(xComponent, yComponent, zComponent);
	} 
	
	/**
	 * Returns the vector scale with the given parameter s
	 * 
	 * @param s
	 * @return vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
	} 
	
	/**
	 * Returns the cosinus between the current vector and the other
	 * 
	 * @param other
	 * @return double angle
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (this.norm() * other.norm());
	} 
	
	/**
	 * Return the x component
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	} 
	
	/**
	 * Return the y component
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	} 
	
	/**
	 * Return the z component
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	} 
	
	/**
	 * Returns the array of components {x,y,z}
	 * 
	 * @return array
	 */
	public double[] toArray() {
		return new double[] {x,y,z};
	} 
	
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x,y,z);
	}
	
}
