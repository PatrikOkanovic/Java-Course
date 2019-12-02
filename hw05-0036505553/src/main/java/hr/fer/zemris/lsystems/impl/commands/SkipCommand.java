package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;
/**
 * Goes from one point to other like {@link DrawCommand} but leaves no trace
 * @author Patrik Okanovic
 *
 */
public class SkipCommand implements Command{

	/**
	 * Length of the movement
	 */
	private double step;
	/**
	 * Creates the skip command.
	 * @param step
	 */
	public SkipCommand(double step) {
		
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState();
		Vector2D oldPosition = state.getPosition();
		Vector2D direction = state.getDirection();
		double effectiveShift = state.getEffectiveShift();
		state.setPosition(oldPosition.translated(direction.scaled(effectiveShift * step)));
	}

}
