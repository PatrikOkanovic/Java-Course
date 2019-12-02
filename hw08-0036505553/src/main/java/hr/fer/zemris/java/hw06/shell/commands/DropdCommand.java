package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * The Dropd command takes no arguments.
 * Removes the path from the stack of shared data in environment, 
 * does not change the current directory. If the stack has no paths throws an exception.
 * 
 * @author Patrik Okanovic
 *
 */
public class DropdCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(! arguments.isBlank()) {
			env.writeln("Dropd takes no arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		
		if(stack.isEmpty()) {
			throw new ShellIOException("Dropd tried to remove a path from an empty stack, cannot be done");
		}
		
		stack.pop();
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
		
		
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Dropd command");
		list.add("Takes no arguments");
		list.add("Removes the path from the stack of shared data in environment, does not change the current directory");
		list.add("If the stack has no paths throws an exception");
		return Collections.unmodifiableList(list);
	}

}
