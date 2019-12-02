package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;
/**
 * Implementation of {@link Command}. Draws the line form one point to other.
 * @author Patrik Okanovic
 *
 */
public class DrawCommand implements Command{

	/**
	 * Distance to be drawn in the direction from our {@link Context}
	 */
	private double step;
	/**
	 * Creates draw command
	 * @param step
	 */
	public DrawCommand(double step) {
		
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState();
		Vector2D oldPosition = state.getPosition();
		
		double x0 = oldPosition.getX();
		double y0 = oldPosition.getY();
		
		Vector2D direction = state.getDirection();
		double effectiveShift = state.getEffectiveShift();
		state.setPosition(oldPosition.translated(direction.scaled(effectiveShift * step)));
		
		double x1 = state.getPosition().getX();
		double y1 = state.getPosition().getY();
		
		painter.drawLine(x0, y0, x1, y1, state.getColor(), 1f);
	}

}
