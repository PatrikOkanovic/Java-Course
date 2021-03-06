package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Implementation of {@link Command}
 * @author Patrik Okanovic
 *
 */
public class ColorCommand implements Command{

	/**
	 * Color to be changed to.
	 */
	private Color color;
	
	/**
	 * Creates a color command
	 * @param color
	 */
	public ColorCommand(Color color) {
		
		this.color = color;
	}


	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState();
		state.setColor(color);
	}

}
