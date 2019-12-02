package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Implementation of {@link Command}. Pushes the current state to the {@link Context}
 * @author Patrik Okanovic
 *
 */
public class PushCommand implements Command{

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState().copy();
		ctx.pushState(currentState);
		
	}

}
