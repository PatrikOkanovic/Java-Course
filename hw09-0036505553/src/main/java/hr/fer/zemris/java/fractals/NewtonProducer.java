package hr.fer.zemris.java.fractals;

import java.util.ArrayList;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * Implements Newton-Raphson iteration. Separates the job to smaller amounts
 * using {@link Thread} to process the work.
 *   
 * @author Patrik Okanovic
 *
 */
public class NewtonProducer implements IFractalProducer{

	/**
	 * Pool of working threads
	 */
	private ExecutorService pool;
	/**
	 * Rooted polynom used for drawing
	 */
	private ComplexRootedPolynomial rootedPolynomial;
	/**
	 * Polynom used for drawing
	 */
	private ComplexPolynomial polynomial;
	
	/**
	 * The constructor of the class
	 * 
	 * @param polynom
	 */
	public NewtonProducer(ComplexRootedPolynomial polynom) {
		this.rootedPolynomial = polynom;
		this.polynomial = polynom.toComplexPolynom();
		pool = Executors.newFixedThreadPool(8 * Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
		
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Zapocinjem izracun...");

		short[] data = new short[width * height];
		final int brojTraka = 16;
		int brojYPoTraci = height / brojTraka;

		
		List<Future<Void>> results = new ArrayList<>();
		
		for(int i = 0; i < brojTraka; i++) {
			int yMin = i * brojYPoTraci;
			int yMax = (i+1) * brojYPoTraci - 1;
			if(i == brojTraka-1) {
				yMax = height-1;
			}
			Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
			results.add(pool.submit(job));
		}
		for(Future<Void> result : results) {
			try {
				result.get();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		
	}
	/**
	 * Implements {@link Callable}, used for parallelization of calculating fractals.
	 * 
	 * @author Patrik Okanovic
	 *
	 */
	public  class Job implements Callable<Void> {

		/**
         * Min value of real part
         */
        private double reMin;
        /**
         * Max value of real part
         */
        private double reMax;
        /**
         * minimal value of imaginary part
         */
        private double imMin;
        /**
         * Max value of imaginary part
         */
        private double imMax;
        /**
         * The width
         */
        private int width;
        /**
         * The height
         */
        private int height;
        /**
         * Min value of y
         */
        private int yMin;
        /**
         * Max value of y
         */
        private int yMax;
        /**
         * Max number of iterations
         */
        private static final int maxIter = 16 * 16 * 16;
        /**
         * Reference to set results 
         */
        private short[] data;
        
        /**
         * The convergence treshold
         */
        private static final double convergenceTreshold = 1E-3;
        /**
         * The acceptable root distance
         */
        private static final double acceptable_root_distance = 0.002;
        
        /**
         * The constructor of the class
         * 
         * @param reMin
         * @param reMax
         * @param imMin
         * @param imMax
         * @param width
         * @param height
         * @param yMin
         * @param yMax
         * @param data
         */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				 short[] data) {
			this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.data = data;
		}

		@Override
		public Void call() throws Exception {
			
			int offset = yMin * width;
			
			for(int y = yMin; y <= yMax; y++) {
				for(int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x,y);
					
					int iter = 0;
					double module = 0;
					
					ComplexPolynomial derived = polynomial.derive();
					
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
						
						
					} while(module > convergenceTreshold && iter < maxIter);
					
					int index = rootedPolynomial.indexOfClosestRootFor(zn, acceptable_root_distance);
					data[offset++] = (short)(index + 1);
				}
			}
			
			return null;
		}
		
		/**
		 * Maps two coordinates on screen to complex coordinate scale.
		 * 
		 * @param x
		 * @param y
		 * @return mapped value
		 */
		private Complex mapToComplexPlain(int x, int y) {
			double real = reMin + ((double) (reMax - reMin) / (double) (width - 1)) * x;
			double imaginary = imMin + ((double) (imMax - imMin) / (double) (height - 1)) * (height - y - 1.0);
			return new Complex(real,imaginary);
		}

		
		
	}
	
}
