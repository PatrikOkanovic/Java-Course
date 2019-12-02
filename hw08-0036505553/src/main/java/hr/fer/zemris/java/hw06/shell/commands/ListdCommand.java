package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * The Listd command takes no arguments.
 * Writes all the paths located on the stack in the environment shared data.
 * If the stack has no paths, writes a message.
 * 
 * @author Patrik Okanovic
 *
 */
public class ListdCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("Listd takes no arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		
		if(stack.isEmpty()) {
			env.writeln("Nema pohranjenih direktorija");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Stack<Path> helpingStack = new Stack<Path>();
		
		while(! stack.isEmpty()) {
			env.writeln(stack.peek().toString());
			helpingStack.push(stack.pop());
		}
		
		//return everything to the stack
		while(! helpingStack.isEmpty()) {
			stack.push(helpingStack.pop());
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Listd command");
		list.add("Takes no arguments");
		list.add("Writes all the paths located on the stack in the environment shared data");
		list.add("If the stack has no paths, writes a message.");
		return Collections.unmodifiableList(list);
	}

}
