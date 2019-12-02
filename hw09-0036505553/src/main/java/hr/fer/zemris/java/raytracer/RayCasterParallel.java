package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer; 

/**
 * Paralellization of {@link RayCaster}.
 * 
 * @author Patrik Okanovic
 *
 */
public class RayCasterParallel {
	/**
	 * Used because of double precision
	 */
	private static final double DIFF = 1E-9;
	/**
	 * The main method of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
		
	}

	/**
	 * Method used for getting the {@link IRayTracerProducer}.
	 * 
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				viewUp = viewUp.normalize();
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				Job job = new Job(0,height-1,height,width,screenCorner,eye ,xAxis,yAxis,scene,red,green,blue,
								horizontal, vertical);
				
				pool.invoke(job);
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	/**
	 * Method used for determining the color of the pixel from image.
	 * 
	 * @param scene
	 * @param ray
	 * @param rgb
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		for(LightSource lightSource : scene.getLights()) {
			Ray ray2 = Ray.fromPoints(lightSource.getPoint(), closest.getPoint());
			RayIntersection closest2 = findClosestIntersection(scene, ray2);
			if(closest2 != null) {
				
				double firstDistance = lightSource.getPoint().sub(closest.getPoint()).norm();
				double secondDistance = lightSource.getPoint().sub(closest2.getPoint()).norm();
				
				if(firstDistance - secondDistance > DIFF) {
					continue;
				}
				
				determineColor(lightSource, closest, ray, rgb);
			}
		}
		 
	}
	
	/**
	 * Used to determine the diffuse and reflective component.
	 * 
	 * @param lightSource
	 * @param closest
	 * @param ray
	 * @param rgb
	 */
	private static void determineColor(LightSource lightSource, RayIntersection closest, Ray ray, short[] rgb) {
		Point3D l = lightSource.getPoint().sub(closest.getPoint());
		Point3D n = closest.getNormal();
		
		double ln = l.normalize().scalarProduct(n) > 0 ? l.normalize().scalarProduct(n) : 0; 
		
		rgb[0] += lightSource.getR() * ln * closest.getKdr();
		rgb[1] += lightSource.getG() * ln * closest.getKdg();
		rgb[2] += lightSource.getB() * ln * closest.getKdb();
		
		Point3D v = ray.start.sub(closest.getPoint()).normalize();
		Point3D r = l.sub(n.scalarMultiply(2 * l.scalarProduct(n))).normalize().negate();
		
		if(v.scalarProduct(r) > 0) {
			rgb[0] += lightSource.getR() * Math.pow(v.scalarProduct(r), closest.getKrn()) * closest.getKrr();
			rgb[1] += lightSource.getG() * Math.pow(v.scalarProduct(r), closest.getKrn()) * closest.getKrg();
			rgb[2] += lightSource.getB() * Math.pow(v.scalarProduct(r), closest.getKrn()) * closest.getKrb();
		}
	}
	

	/**
	 * Finds the closest intersection between the ray and the objects in the scene.
	 * Returns null if there is no intersection.
	 * 
	 * @param scene
	 * @param ray
	 * @return {@link RayIntersection}
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		RayIntersection closest = null;
		
		for(GraphicalObject object : scene.getObjects()) {
			
			RayIntersection curretnIntersection = object.findClosestRayIntersection(ray);
			
			if(curretnIntersection != null) {
				if(closest == null) {
					closest = curretnIntersection;
				} else if(curretnIntersection.getDistance() < closest.getDistance()) {
					closest = curretnIntersection;
				}
			}
		}
		
		return closest;
	}
	/**
	 * Extends {@link RecursiveAction}, does the parallel work of creating the image from homework 
	 * problem 3.
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	private static class Job extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		/**
		 * Min value of y
		 */
		private int yMin;
		/**
		 * Max value of y
		 */
		private int yMax;
		/**
		 * The height
		 */
		private int height;
		/**
		 * The width
		 */
		private int width;
		/**
		 * The screen corner of the image
		 */
		private Point3D screenCorner;
		/**
		 * Point from where we are looking
		 */
		private Point3D eye;
		/**
		 * The x axis
		 */
		private Point3D xAxis;
		/**
		 * The y axis
		 */
		private Point3D yAxis;
		/**
		 * The scene
		 */
		private Scene scene;
		/**
		 * Red colour
		 */
		private short[] red;
		/**
		 * Green colour
		 */
		private short[] green;
		/**
		 * Blue colour
		 */
		private short[] blue;
		/**
		 * Horizontal length
		 */
		private double horizontal;
		/**
		 * Vertical length
		 */
		private double vertical;
		/**
		 * The treshold
		 */
		private static final int treshold = 16;
	
		
		/**
		 * The constructor of the class
		 * 
		 * @param yMin
		 * @param yMax
		 * @param height
		 * @param width
		 * @param screenCorner
		 * @param eye
		 * @param xAxis
		 * @param yAxis
		 * @param zAxis
		 * @param scene
		 * @param red
		 * @param green
		 * @param blue
		 * @param horizontal
		 * @param vertical
		 */
		public Job(int yMin, int yMax, int height, int width, Point3D screenCorner,Point3D eye,  Point3D xAxis, Point3D yAxis, Scene scene, short[] red, short[] green, short[] blue, double horizontal, double vertical) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.height = height;
			this.width = width;
			this.screenCorner = screenCorner;
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.scene = scene;
			this.red = red;
			this.blue = blue;
			this.green = green;
			this.horizontal = horizontal;
			this.vertical = vertical;
		}

		@Override
		protected void compute() {
			if(yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(new Job(yMin, yMin+(yMax-yMin)/2, height, width, screenCorner,eye , xAxis, yAxis, scene, red, green, blue, horizontal, vertical),
					  new Job(yMin+(yMax-yMin)/2+1, yMax, height, width, screenCorner, eye, xAxis, yAxis, scene, red, green, blue, horizontal, vertical));
			
		}
		
		/**
		 * Computes the task directly when small enough.
		 */
		private void computeDirect() {
			int offset = yMin*width;
			short[] rgb = new short[3];
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x*horizontal/(width-1))).sub(yAxis.scalarMultiply(y*vertical/(height-1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
					tracer(scene, ray, rgb);
					
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
		}
		
	}
	}
	
}