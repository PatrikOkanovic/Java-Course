package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Mkdir command takes one argument, the directory name.
 * Creates the directory with the given path.
 * 
 * @author Patrik Okanovic
 *
 */
public class MkdirCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser;
		List<String> paths;
		
		try {
			parser = new ShellParser(arguments);
			
		} catch(ShellParserException exc) {
			env.writeln("The given path is invalid");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		paths = parser.getPaths();
		
		if(paths.size() != 1) {
			env.writeln("mkdir expects one argument");
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
		
		
		try {
			Files.createDirectories(p);
			   env.writeln("Directory created");
        } catch (IOException exc) {
            env.writeln("Error while creating directory");
            
        }
		
		env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Mkdir command");
		list.add("Takes one argument, the directory name");
		list.add("Creates the directory with the given path");
		return Collections.unmodifiableList(list);
	}

}
