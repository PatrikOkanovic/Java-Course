package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;
/**
 * Implementation of  the {@link Environment}
 * 
 * @author Patrik Okanovic
 *
 */
public class EnvironmentImpl implements Environment{

	/**
	 * Current promptSymbol
	 */
	private Character promptSymbol = '>';
	/**
	 * Current multilineSymbol
	 */
	private Character multilineSymbol = '|';
	/**
	 * Current morelinesSymbol
	 */
	private Character morelinesSymbol = '\\';
	/**
	 * Scanner for reading data from the user
	 */
	private Scanner sc = new Scanner(System.in);
	/**
	 * Storage for commands
	 */
	private SortedMap<String, ShellCommand> map;
	/**
	 * The current path of the {@link Environment}
	 */
	private Path currentDirectory = Paths.get(".").toAbsolutePath().normalize();
	/**
	 * Used to get and set shared data
	 */
	private Map<String, Object> sharedData = new HashMap<>();
	
	/**
	 * Constructor of the {@link EnvironmentImpl}. Creates the map with commands
	 */
	public EnvironmentImpl() {
		writeln("Welcome to MyShell v 1.0");
		write(promptSymbol + " ");
		map = new TreeMap<>();
		
		putCommands();
		
		setSharedData("cdstack", new Stack<Path>());
	}
	
	private void putCommands() {
		CatCommand cat = new CatCommand();
		map.put(cat.getCommandName(), cat);
		
		CharsetCommand charset = new CharsetCommand();
		map.put(charset.getCommandName(), charset);
		
		CopyCommand copy = new CopyCommand();
		map.put(copy.getCommandName(), copy);
		
		ExitCommand exit = new ExitCommand();
		map.put(exit.getCommandName(), exit);
		
		HelpCommand help = new HelpCommand();
		map.put(help.getCommandName(), help);
		
		HexdumpCommand hexdump = new HexdumpCommand();
		map.put(hexdump.getCommandName(), hexdump);
		
		LsCommand ls = new LsCommand();
		map.put(ls.getCommandName(), ls);
		
		MkdirCommand mkdir = new MkdirCommand();
		map.put(mkdir.getCommandName(), mkdir);
		
		SymbolCommand symbol = new SymbolCommand();
		map.put(symbol.getCommandName(), symbol);
		
		TreeCommand tree = new TreeCommand();
		map.put(tree.getCommandName(), tree);
		
		//new commands
		PwdCommand pwd = new PwdCommand();
		map.put(pwd.getCommandName(), pwd);
		
		CdCommand cd = new CdCommand();
		map.put(cd.getCommandName(), cd);
		
		PushdCommand pushd = new PushdCommand();
		map.put(pushd.getCommandName(), pushd);
		
		PopdCommand popd = new PopdCommand();
		map.put(popd.getCommandName(), popd);
		
		ListdCommand listd = new ListdCommand();
		map.put(listd.getCommandName(), listd);
		
		DropdCommand dropd = new DropdCommand();
		map.put(dropd.getCommandName(), dropd);
		
		MassrenameCommand massrename = new MassrenameCommand();
		map.put(massrename.getCommandName(), massrename);
	}

	@Override
	public String readLine() throws ShellIOException {

		String line;
		try {
			line = sc.nextLine();
		} catch (Exception exc) {
			throw new ShellIOException();
		}
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(map);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
		
	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if(!Files.isDirectory(path)) {
			throw new ShellIOException("The given path is not a directory");
		}
		currentDirectory = path;
		
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
		
	}

}
