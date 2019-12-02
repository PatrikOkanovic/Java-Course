package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * The pwd command takes no arguments and prints the current
 * directory of {@link MyShell}l
 * 
 * @author Patrik Okanovic
 *
 */
public class PwdCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("CharsetCommand does not take any arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		env.writeln(env.getCurrentDirectory().toString());
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Pwd command");
		list.add("Takes no arguments");
		list.add("Prints the current directory of Shell");
		return Collections.unmodifiableList(list);
	}

}
