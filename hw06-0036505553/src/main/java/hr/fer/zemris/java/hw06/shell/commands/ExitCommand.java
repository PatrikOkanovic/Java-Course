package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * The Exit command takes no arguments.
 * It exits from MyShell.
 * 
 * @author Patrik Okanovic
 *
 */
public class ExitCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() != 0) {
			env.writeln("ExitCommand does not take any arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Exit command");
		list.add("Takes no arguments");
		list.add("It exits from MyShell");
		return Collections.unmodifiableList(list);
	}

}
