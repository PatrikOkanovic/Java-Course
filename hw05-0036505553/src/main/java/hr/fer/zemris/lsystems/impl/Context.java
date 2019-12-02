package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;
/**
 * Represents context of the current state. USed for managing with the {@link TurtleState}
 * @author Patrik Okanovic
 *
 */
public class Context {

	/**
	 * Stores the states of the {@link TurtleState} on a stack
	 */
	private ObjectStack<TurtleState> stack = new ObjectStack<>();
	/**
	 * Returns the current state.
	 * @return
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	/**
	 * Pushes the current state.
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	/**
	 * Pops the current state from the context.
	 */
	public void popState() {
		stack.pop();
	}
}
