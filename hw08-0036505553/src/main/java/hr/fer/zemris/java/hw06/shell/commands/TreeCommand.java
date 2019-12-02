package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Tree command takes one argument, the directory name.
 * Prints a tree, every directory or file shifts two spaces to the right in a new line
 * 
 * @author Patrik Okanovic
 *
 */
public class TreeCommand implements ShellCommand{

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
			env.writeln("tree expects one argument");
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
		
		if(! Files.isDirectory(p)) {
			env.writeln("The given path is not a directory");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(p, new TreeVisitor());
		} catch(IOException exc) {
			env.writeln("Error while reading from directory");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Tree command");
		list.add("Takes one argument, the directory name");
		list.add("Prints a tree, every directory shifts two spaces to the right in a new line");
		return Collections.unmodifiableList(list);
	}
	
	private static class TreeVisitor implements FileVisitor<Path> {
		int depth;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			System.out.println(" ".repeat(2*depth) + dir.getFileName());
			depth++;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			depth--;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			System.out.println(" ".repeat(2*depth) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}

}
