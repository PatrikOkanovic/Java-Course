package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Scales the vector of currents state.
 * @author Patrik Okanovic
 *
 */
public class ScaleCommand implements Command{

	/**
	 * Scale factor of the vector
	 */
	private double factor;
	/**
	 * Creates the scale command
	 * @param factor
	 */
	 public ScaleCommand(double factor) {
		 this.factor = factor;
	}
	 
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setEffectiveShift(factor * state.getEffectiveShift()); 
	}

}
