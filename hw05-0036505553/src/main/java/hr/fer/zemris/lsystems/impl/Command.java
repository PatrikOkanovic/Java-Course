package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
/**
 * Interface which represents a command, with one method execute.
 * @author Patrik Okanovic
 *
 */
public interface Command {

	/**
	 * Executes the method and does the wanted thing.
	 * @param ctx
	 * @param painter
	 */
	void execute(Context ctx, Painter painter);

}
