package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Pushd command takes one argument, a relative or absolute path.
 * Puts the current directory to the stack of shared data on key "cdstack"
 * If the given path argument does not exist the command does not change the current directory or
 * stack
 * 
 * @author Patrik Okanovic
 *
 */
public class PushdCommand implements ShellCommand{

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
			env.writeln("Pushd expects one argument");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path p;
		
		try {
			p = env.getCurrentDirectory().resolve(Paths.get(paths.get(0)));
		} catch(InvalidPathException exc) {
			env.writeln("invalid path");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(p)) {
			env.writeln("The given path does not exist");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
	
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		
		stack.push(env.getCurrentDirectory());
		
		env.setCurrentDirectory(p);
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Pushd command");
		list.add("Takes one argument, a relative or absolute path");
		list.add("Puts the current directory to the stack of shared data on key \"cdstack\"");
		list.add("If the given path argument does not exist the command does not change the current directory or"
				+ "stack");
		return Collections.unmodifiableList(list);
	}

}
