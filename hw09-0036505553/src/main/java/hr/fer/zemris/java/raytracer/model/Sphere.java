package hr.fer.zemris.java.raytracer.model;
/**
 * Implementation of {@link GraphicalObject}. Represents a sphere with center radius and 
 * coefficient for rgb.
 * 
 * @author Patrik Okanovic
 *
 */
public class Sphere extends GraphicalObject {

	/**
     * The center of sphere.
     */
    private Point3D center;

    /**
     * The radius of sphere.
     */
    private double radius;

    /**
     * Red diffuse coefficient.
     */
    private double kdr;

    /**
     * Green diffuse coefficient.
     */
    private double kdg;

    /**
     * Blue diffuse coefficient.
     */
    private double kdb;

    /**
     * Red reflective coefficient.
     */
    private double krr;

    /**
     * Green reflective coefficient.
     */
    private double krg;

    /**
     * Blue reflective coefficient.
     */
    private double krb;

    /**
     * The shininess factor, larger for smoother surfaces
     */
    private double krn;
    
    /**
     * The constructor of the class.
     * 
     * @param center of the sphere
     * @param radius of the sphere
     * @param kdr red diffuse coefficient
     * @param kdg green diffuse coefficient
     * @param kdb blue diffuse coefficient
     * @param krr red reflective coefficient
     * @param krg green reflective coefficient
     * @param krb blue reflective coefficient
     * @param krn shininess coefficient
     */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}


	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {		
		Point3D v = center.sub(ray.start);
		
		double dot = v.scalarProduct(v);
		double b = v.scalarProduct(ray.direction);
		
		double disriminant = b*b - dot + radius*radius;
		
		if(disriminant < 0) {
			return null;
		}
		
		double d = Math.sqrt(disriminant);
		double root1 = b - d;
		double root2 = b+d;
		double distance = Double.POSITIVE_INFINITY;
		
		if (root2 > 0) {
			if (root1 < 0) {
				if (root2 < distance) {
					distance = root2;
				}
			} else {
				if (root1 < distance) {
					distance = root1;
				}
			}
		} else {
			return null;
		}
		
		Point3D intersection = new Point3D(ray.start.x + distance * ray.direction.x,
											ray.start.y + distance * ray.direction.y,
											ray.start.z + distance * ray.direction.z);
		
		return new RayIntersectionImpl(intersection, distance, true);
	}
	/**
	 * An implementation of {@link RayIntersection}.
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private class RayIntersectionImpl extends RayIntersection {

		protected RayIntersectionImpl(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}
		

		@Override
		public Point3D getNormal() {
			return this.getPoint().sub(center).normalize();
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}


		@Override
		public double getKdr() {
			return kdr;
		}
	}
}
