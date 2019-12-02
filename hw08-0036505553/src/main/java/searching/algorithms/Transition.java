package searching.algorithms;
/**
 * Represents a pair (sj,cj), (state,cost)
 * 
 * @author Patrik Okanovic
 *
 * @param <S>
 */
public class Transition<S> {

	/**
	 * The state
	 */
	private S state;
	/**
	 * The cost
	 */
	private double cost;

	/**
	 * The constructor of the class
	 * 
	 * @param state
	 * @param cost
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	/**
	 * @return the state
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	
	
}
