package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The charset command takes no arguments and returns all the available charsets
 * written each command per line.
 * 
 * @author Patrik Okanovic
 *
 */
public class CharsetCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments.length() != 0) {
			env.writeln("CharsetCommand does not take any arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String, Charset> map = Charset.availableCharsets();
		
		for(String name : map.keySet()) {
			env.writeln(name);
		}
		env.write(env.getPromptSymbol() + " ");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Charset command");
		list.add("Takes no arguments and returns all the available charsets");
		list.add("Charset per line is written");
		return Collections.unmodifiableList(list);
	}

}
