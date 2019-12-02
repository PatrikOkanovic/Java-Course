package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
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
 * The Popd command takes no arguments. Takes the path from the
 * stack of shared data and sets the current directory with that path
 * If the directory from the stack does not exist the directory is not changed but path is removed from stack
 * If the stack is empty throws an exception
 * 
 * @author Patrik Okanovic
 *
 */
public class PopdCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(!arguments.isBlank()) {
			env.writeln("PopdCommand does not take any arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		
		if(stack.isEmpty()) {
			throw new ShellIOException("The stack is empty");
		}
		
		Path p = stack.pop();
		
		if(!Files.exists(p)) {
			env.writeln("The path from the stack exists no more, current directory remains");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		env.setCurrentDirectory(p);
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Popd command");
		list.add("Takes no arguments");
		list.add("Takes the path from the stack of shared data and sets teh current directory with that path");
		list.add("If the directory from the stack does not exist the directory is not changed but path is removed from stack");
		list.add("If the stack is empty throws an exception");
		return Collections.unmodifiableList(list);
	}

}
