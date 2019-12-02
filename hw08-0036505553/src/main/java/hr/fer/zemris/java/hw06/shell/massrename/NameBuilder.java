package hr.fer.zemris.java.hw06.shell.massrename;

import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;

/**
 * Used to generate name for {@link MassrenameCommand} by writing to {@link StringBuilder} 
 * 
 * @author Patrik Okanovic
 *
 */
public interface NameBuilder {

	/**
	 * Writes to StrignBuilder sb.
	 * 
	 * @param result
	 * @param sb
	 */
	void execute(FilterResult result, StringBuilder sb);

}
