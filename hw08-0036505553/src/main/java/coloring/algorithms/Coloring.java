package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;
/**
 * Class implementing necessary interfaces so that we could implement searching the are of solutions with
 * {s0, process(s), succ(s), acceptable(s)}.
 * 
 * @author Patrik Okanovic
 *
 */
public class Coloring implements Consumer<Pixel>, Predicate<Pixel>, Function<Pixel,List<Pixel>>,Supplier<Pixel>{

	/**
	 * Reference to the first Pixel
	 */
	private Pixel reference;
	/**
	 * Reference to the {@link Picture} we are coloring
	 */
	private Picture picture;
	/**
	 * The fillColor
	 */
	private int fillColor;
	/**
	 * The referenced color
	 */
	private int refColor;
	/**
	 * Constructor of the class
	 * 
	 * @param reference
	 * @param picture
	 * @param fillColor
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> list = new ArrayList<>();
		
		if(t.y-1 >= 0) {
			list.add(new Pixel(t.x,t.y-1));
		}
		
		if(t.y+1 <= picture.getHeight()-1) {
			list.add(new Pixel(t.x, t.y+1));
		}
		
		if(t.x-1 >= 0) {
			list.add(new Pixel(t.x-1,t.y));
		}
		
		if(t.x+1 <= picture.getWidth()-1) {
			list.add(new Pixel(t.x+1, t.y));
		}
		
		
		
		return list;
	}

	@Override
	public boolean test(Pixel t) {
		return refColor == picture.getPixelColor(t.x, t.y);
	}
	
	
	

}
