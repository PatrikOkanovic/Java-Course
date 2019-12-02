package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Ls command takes one argument, the directory name.
 * Writes four columns for each recording in the directory, such as is it readeable,
 * is it writeable, executable, is it directory, time and date of creation
 * name and size
 * 
 * @author Patrik Okanovic
 *
 */
public class LsCommand implements ShellCommand{

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
			env.writeln("ls expects one argument");
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
		
		try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(p)) {
			
			for(Path path : directoryStream) {
				StringBuilder sb = new StringBuilder();
				
				if (Files.isDirectory(path)) {
					sb.append("d");
				} else {
					sb.append("-");
				}
				if (Files.isReadable(path)) {
					sb.append("r");
				} else {
					sb.append("-");
				}
				if (Files.isWritable(path)) {
					sb.append("w");
				} else {
					sb.append("-");
				}
				if (Files.isExecutable(path)) {
					sb.append("x");
				} else {
					sb.append("-");
				}
				
				sb.append(" ");
				
				long size = Files.size(path);
				sb.append(String.format("%10d", size));
				sb.append(" ");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				sb.append(formattedDateTime);
				sb.append(" ");
				sb.append(path.getFileName());
				
				env.writeln(sb.toString());
			}
			
		} catch (IOException e) {
			env.writeln("Error while reading from directory!");
            
		}
		
		env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Ls command");
		list.add("Takes one argument, the directory name");
		list.add("Writes four columns for each recording in the directory, such as is it readeable,"
				+ "is it writeable, executable, is it directory, time and date of creation"
				+ "name and size");
		return Collections.unmodifiableList(list);
	}

}
