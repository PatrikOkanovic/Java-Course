package hr.fer.zemris.java.hw06.shell;

import java.util.List;
/**
 * Abstraction for the commands used in the shell.
 * @author Patrik Okanovic
 *
 */
public interface ShellCommand {

	/**
	 * Executes the command for the given arguments.
	 * 
	 * @param env
	 * @param arguments
	 * @return {@link ShellStatus}
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the name of the command.
	 * 
	 * @return
	 */
	String getCommandName();
	
	/**
	 * Returns an unmodifiable list of descriptions of the command.
	 * 
	 * @return
	 */
	List<String> getCommandDescription();
}
