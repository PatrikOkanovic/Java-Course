package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * The HelpCommand takes the name of the command as argument or takes no arguments. If it takes the name
 * of the command as argument then it returns command descriptions, otherwise it will list the names of 
 * commands.
 * 
 * @author Patrik Okanovic
 *
 */
public class HelpCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		SortedMap<String, ShellCommand> map = env.commands();
		
		if(arguments.length() == 0) {
			
			for(String name : map.keySet()) {
				env.writeln(name);
			}
			
		} else {
			
			if(! map.containsKey(arguments)) {
				
				env.writeln("Shell does not support that command!");
				
			} else {
				
				List<String> list = map.get(arguments).getCommandDescription();
				
				for(String line : list) {
					env.writeln(line);
				}
			}
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Help command");
		list.add("Takes the name of the command as argument or takes no arguments");
		list.add("Returns command descriptions or lists the names of commands");
		return Collections.unmodifiableList(list);
	}

}
