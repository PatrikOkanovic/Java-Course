package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Cd command. Changes current directory of {@link MyShell}.
 * Takes one argument, a relative or absolute path.
 *  
 * @author Patrik Okanovic
 *
 */
public class CdCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser;
		List<String> paths;
		
		try {
			parser = new ShellParser(arguments);
			
		} catch(ShellParserException exc) {
			env.writeln(exc.getMessage());
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		paths = parser.getPaths();
		
		if(paths.size() != 1) {
			env.writeln("Cd command accepts only one argument");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		env.setCurrentDirectory(env.getCurrentDirectory().resolve(paths.get(0)));
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Cd command");
		list.add("Takes one argument, a relative or absolute path");
		list.add("Sets the current directory of the environment to the given path");
		return Collections.unmodifiableList(list);
	}

}
