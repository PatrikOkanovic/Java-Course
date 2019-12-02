package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Implementation of {@link Command}. Rotates our current state for an angle.
 * @author Patrik Okanovic
 *
 */
public class RotateCommand implements Command {

	/**
	 * The angle.
	 */
	private double angle;
	/**
	 * Creates the rotate command
	 * @param angle
	 */
	public RotateCommand(double angle) {

		this.angle = angle;
	}
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setDirection(state.getDirection().rotated(angle));
		
	}

}
