package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;
/**
 * USed to implement searching through space of solutions,and to solve the puzzle.
 * 
 * @author patri
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,Predicate<KonfiguracijaSlagalice>, Function<KonfiguracijaSlagalice,List<Transition<KonfiguracijaSlagalice>>> {

	/**
	 * Reference to the initial state
	 */
	private KonfiguracijaSlagalice pocetna;
	
	/**
	 * Constructor of the class
	 * 
	 * @param pocetna
	 */
	public Slagalica(KonfiguracijaSlagalice pocetna) {
		this.pocetna = pocetna;
	}

	/**
	 * The solution  we are trying to achieve.
	 */
	private int[] goal = {1,2,3,4,5,6,7,8,0};
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<>();
		int indexOfZero = t.indexOfSpace();
		
		int tmp;
		int[] tmpField;
		
		if(indexOfZero-1 >= 0) {
			tmpField = t.getPolje();
			tmp = tmpField[indexOfZero];
			tmpField[indexOfZero] = tmpField[indexOfZero-1];
			tmpField[indexOfZero-1] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(tmpField), 1));
		}
		
		if(indexOfZero+1 <= 8) {
			tmpField = t.getPolje();
			tmp = tmpField[indexOfZero];
			tmpField[indexOfZero] = tmpField[indexOfZero+1];
			tmpField[indexOfZero+1] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(tmpField), 1));
		}
		
		if(indexOfZero-3 >= 0) {
			tmpField = t.getPolje();
			tmp = tmpField[indexOfZero];
			tmpField[indexOfZero] = tmpField[indexOfZero-3];
			tmpField[indexOfZero-3] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(tmpField), 1));
		}
		
		if(indexOfZero+3 <= 8) {
			tmpField = t.getPolje();
			tmp = tmpField[indexOfZero];
			tmpField[indexOfZero] = tmpField[indexOfZero+3];
			tmpField[indexOfZero+3] = tmp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(tmpField), 1));
		}
		
		return list;
		
		
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(goal, t.getPolje());
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return pocetna;
	}

	
	
	
}
