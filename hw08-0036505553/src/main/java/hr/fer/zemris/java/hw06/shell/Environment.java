package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;
/**
 * Represents the environment in which our shell is. Used as connection between {@link MyShell}
 * and {@link ShellCommand}.
 * 
 * @author Patrik Okanovic
 *
 */
public interface Environment {

	/**
	 * Reads the line from the environment.
	 * 
	 * @return
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes the given text to the environment.
	 * 
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes the given text to the environment and then goes to the new line.
	 * 
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns an unmodifiable map of commands.
	 * 
	 * @return
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Character used as a multiline symbol in {@link MyShell}
	 * 
	 * @return
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol to the environment.
	 * 
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Character used as a multiline symbol in {@link MyShell}
	 * 
	 * @return
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol to the environment.
	 * 
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Character used as morelines symbol in {@link MyShell}
	 * 
	 * @return
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol to the environment.
	 * 
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
	/**
	 * Returns the current directory of the {@link Environment}
	 * @return
	 */
	Path getCurrentDirectory();
	/**
	 * Sets the current directory of the {@link Environment}
	 * @param path
	 */
	void setCurrentDirectory(Path path);
	/**
	 * Returns the shared data
	 * @param key
	 * @return
	 */
	Object getSharedData(String key);
	/**
	 * Sets the shared data
	 * @param key
	 * @param value
	 */
	void setSharedData(String key, Object value);

}
