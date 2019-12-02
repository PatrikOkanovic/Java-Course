package hr.fer.zemris.java.hw06.shell.massrename;

import java.nio.file.Path;
import java.util.regex.Matcher;

import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
/**
 * Used for implementing {@link MassrenameCommand}. Contains information about
 * the path of the Object and the matcher based on the pattern from the given command.
 * 
 * @author Patrik Okanovic
 *
 */
public class FilterResult {

	/**
	 * Path of the file
	 */
	private Path path;

	/**
	 * The matcher to see if the file matches the regular expression
	 */
	private Matcher matcher;
	
	/**
	 * Constructor of {@link FilterResult}
	 * @param path
	 * @param matcher
	 */
	public FilterResult(Path path, Matcher matcher) {
		this.matcher = matcher;
		this.path = path;
	}
	
	@Override
	public String toString() {
		return path.getFileName().toString();
	}
	/**
	 * Returns the number of capturing groups in {@link FilterResult} matcher's pattern
	 * 
	 * @return numberOFgroups
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}
	
	/**
	 * Returns the input subsequence captured by the given group during the 
	 * previous match operation
	 * 
	 * @param index
	 * @return
	 */
	public String group(int index) {
		return matcher.group(index);
	}
	
	/**
	 * Returns the path
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

}
