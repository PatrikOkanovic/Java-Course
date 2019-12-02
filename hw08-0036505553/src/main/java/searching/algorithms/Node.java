package searching.algorithms;
/**
 * Used for keeping the reference to the current state, previous state, and cost for
 * getting to this state
 * 
 * @author Patrik Okanovic
 *
 * @param <S>
 */
public class Node<S> {

	/**
	 * The parent of the current state
	 */
	private Node<S> parent;
	
	/**
	 * Current state of the solution
	 */
	private S state;
	
	/**
	 * Cost of operations for getting to this state
	 */
	private double cost;

	/**
	 * Constructor of the class
	 * 
	 * @param parent
	 * @param state
	 * @param cost
	 */
	public Node(Node<S> parent, S state, double cost) {
		super();
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * @return the parent
	 */
	public Node<S> getParent() {
		return parent;
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
