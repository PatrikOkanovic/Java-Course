package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

public class EnvironmentImpl implements Environment{

	private Character promptSymbol = '>';
	private Character multilineSymbol = '|';
	private Character morelinesSymbol = '\\';
	private Scanner sc = new Scanner(System.in);
	private SortedMap<String, ShellCommand> map;
	
	public EnvironmentImpl() {
		writeln("Welcome to MyShell v 1.0");
		write(promptSymbol + " ");
		map = new TreeMap<>();
		
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
	}
	
	@Override
	public String readLine() throws ShellIOException {
		StringBuilder sb = new StringBuilder();
		
		String line = sc.nextLine();
		
		while(line.endsWith(morelinesSymbol.toString())) {
			sb.append(line, 0, line.length() - 1); // must not add the morelines symbol
			write(multilineSymbol + " ");
			line = sc.nextLine();
		}
		
		sb.append(line);
		return sb.toString();
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

}
