package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.massrename.FilterResult;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.massrename.parser.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The MassrenameCommand is used like this in {@link MyShell}: 
 * 			massrename DIR1 DIR2 CMD MASKA ostalo
 * Command is used for massive renaming and moving files which are directly in 
 * DIR1. Mask is the regular expression which files must match.
 * CMD can be : filter, groups, show, execute
 * filter - writes file names which match the regular expression
 * groups - writes groups with matcher based on MASK
 * show - generates new name of the files which match the MASK based on ostalo
 * execute - does the moving and renameing the files from DIR1 to DIR2 which satisfy MASK and creates name based on ostalo
 *  
 * @author Patrik Okanovic
 *
 */
public class MassrenameCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellParser parser;
		List<String> args;
		
		try {
			parser = new ShellParser(arguments);
			
		} catch(ShellParserException exc) {
			env.writeln("Cannot accept the given arguments");
			env.writeln(exc.getMessage());
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		args = parser.getPaths();
		
		Path dir1, dir2;
		
		if(args.size() != 4 && args.size() != 5) {
			env.writeln("Massrename takes 4 or 5 arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		try {
			dir1 = env.getCurrentDirectory().resolve(Paths.get(args.get(0)));
			dir2 = env.getCurrentDirectory().resolve(Paths.get(args.get(1)));
		} catch(InvalidPathException exc) {
			env.writeln("invalid path");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(! Files.isDirectory(dir1)) {
			env.writeln("The given dir1 is not a directory");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(! Files.isDirectory(dir2)) {
			env.writeln("The given dir2 is not a directory");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		String CMD = args.get(2);
		String mask = args.get(3);
		try {	
			List<FilterResult> list= filter(dir1, mask);
			String expression;
			NameBuilderParser nameParser;
			NameBuilder builder;
			switch(CMD) {
				case "filter":
					list.forEach(r -> env.writeln(r.toString()));
					break;
					
				case "groups":
					list.forEach((filterresult)-> {
						env.write(filterresult.toString() + " ");
						for(int i = 0; i <= filterresult.numberOfGroups(); i++) {
							env.write(i + ": " + filterresult.group(i) + " ");
						}
						env.writeln("");
					});
					break;
				
				case "show":
					if(args.size()!=5) {
						env.writeln("show needs 5th argument");
						env.write(env.getPromptSymbol() + " ");
						return ShellStatus.CONTINUE;
					}
					
					expression = args.get(4);
					nameParser = new NameBuilderParser(expression);
					builder = nameParser.getNameBuilder();
					for (FilterResult file : list) {
						StringBuilder sb = new StringBuilder();
						builder.execute(file, sb);
						env.writeln(file.toString() + " => " + sb.toString());
					}
					
					break;
					
				case "execute":
					if(args.size()!=5) {
						env.writeln("exectue needs 5th argument");
						env.write(env.getPromptSymbol() + " ");
						return ShellStatus.CONTINUE;
					}
					
					expression = args.get(4);
					nameParser = new NameBuilderParser(expression);
					builder = nameParser.getNameBuilder();
					for (FilterResult file : list) {
						StringBuilder sb = new StringBuilder();
						builder.execute(file, sb);
						Path destination = Paths.get(dir2.toString(), sb.toString());
						Files.move(file.getPath(), destination, StandardCopyOption.REPLACE_EXISTING);
						env.writeln(file.getPath().toString() + " => " + destination.toString());
					}
					
					
					break;
					
				default:
					env.writeln("No such option for massrename");
					env.write(env.getPromptSymbol() + " ");
			}
		} catch(Exception exc) {
			env.writeln(exc.getMessage());
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
				
		
	}
	
	/**
	 * Static method which filters the files from the given path based on the regular expression in 
	 * the String pattern. Returns a list of {@link FilterResult}
	 * 
	 * @param dir
	 * @param pattern
	 * @return list
	 * @throws IOException when error occurs during iterating the files
	 */
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> list = new ArrayList<>();
		Pattern pat = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
		
			for(Path path : stream) {
				if(!Files.isRegularFile(path)) {
					continue;
				}
				Matcher matcher = pat.matcher(path.getFileName().toString());
				if(matcher.matches()) {
					list.add(new FilterResult(path, matcher));
				}
			}
			
		}
		
		return list;
		
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Massrename command");
		list.add("The MassrenameCommand is used like this in MyShell:");
		list.add("massrename DIR1 DIR2 CMD MASKA ostalo");
		list.add("Command is used for massive renaming and moving files which are directly in  DIR1.");
		list.add("Mask is the regular expression which files must match.");
		list.add("CMD can be : filter, groups, show, execute");
		list.add("filter - writes file names which match the regular expression");
		list.add("groups - writes groups with matcher based on MASK");
		list.add("show - generates new name of the files which match the MASK based on ostalo");
		list.add("execute - does the moving and renameing the files from DIR1 to DIR2 which satisfy MASK and creates name based on ostalo");
		return Collections.unmodifiableList(list);
	}

}
