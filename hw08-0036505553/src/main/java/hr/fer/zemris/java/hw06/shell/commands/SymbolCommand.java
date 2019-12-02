package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The symbol command. Takes one or two arguments. If one argument is provided 
 * writes the current symbol for PROMPT, MORELINES,or MULTILINE.
 * If two arguments are provided sets the new symbol for 
 * PROMPT, MORELINES,or MULTILINE
 * 
 * @author Patrik Okanovic
 *
 */
public class SymbolCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] arg = arguments.split("\\s+");
		
		if(arg.length == 1) {
			Character symbol;
			switch(arg[0]) {
				case "PROMPT":
					symbol = env.getPromptSymbol();
					break;
				case "MULTILINE":
					symbol = env.getMultilineSymbol();
					break;
				case "MORELINES":
					symbol = env.getMorelinesSymbol();
					break;
				default:
					env.writeln("Unsupported operation!");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for " + arguments + " is '" + symbol + "'");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		} else if(arg.length == 2) {
			Character oldSymbol;
			switch(arg[0]) {
				case "PROMPT":
					oldSymbol = env.getPromptSymbol();
					env.setPromptSymbol(arg[1].charAt(0));
					break;
				case "MULTILINE":
					oldSymbol = env.getMultilineSymbol();
					env.setMultilineSymbol(arg[1].charAt(0));
					break;
				case "MORELINES":
					oldSymbol = env.getMultilineSymbol();
					env.setMorelinesSymbol(arg[1].charAt(0));
					break;
				default:
					env.writeln("Unsupported operation!");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
			}
			env.writeln("Symbol for " + arg[0] + " changed from '"
					+ oldSymbol + "' to '" + arg[1] + "'");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		} else {
			env.writeln("Invalid number of arguments!");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Symbol command");
		list.add("Takes one or two arguments");
		list.add("If one argument is provided writes the current symbol for PROMPT, MORELINES,or MULTILINE");
		list.add("If two arguments are provided sets the new symbol for PROMPT, MORELINES,or MULTILINE");
		return Collections.unmodifiableList(list);
	}

}
