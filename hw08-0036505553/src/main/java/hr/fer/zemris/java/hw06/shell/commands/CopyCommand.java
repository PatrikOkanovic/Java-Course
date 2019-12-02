package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Copy command takes two argument, the source file name and destination file name.
 * Copies file from source to destination, does not work with copying directories
 * Asks for permission to overwrite a file
 * 
 * @author Patrik Okanovic
 *
 */
public class CopyCommand implements ShellCommand{

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
		
		if(paths.size() != 2) {
			env.writeln("copy expects two arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path source, destination;
		
		try {
			source = env.getCurrentDirectory().resolve(Paths.get(paths.get(0)));
			destination = env.getCurrentDirectory().resolve(Paths.get(paths.get(1)));
		} catch(InvalidPathException exc) {
			env.writeln("invalid path");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(source)) {
			env.writeln("The given source path cannot be a directory");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (Files.isDirectory(destination)) {
            destination = Paths.get(destination.toString() + "/" + source.getFileName());
        }
		
		if (source.equals(destination)) {
            env.writeln("Not able to copy file to itself");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
        }
		
		if (Files.exists(destination)) {
            env.writeln("Do you want to overwrite file?(Y/N)");
            env.write(env.getPromptSymbol() + " ");
            String answer = env.readLine();
            if(!answer.equals("Y") && !answer.equals("N")) {
            	env.writeln("Invalid answer, user must enter Y/N");
                env.write(env.getPromptSymbol() + " ");
                return ShellStatus.CONTINUE;
            } else if (answer.equals("N")) {
                env.writeln("File will not be copied");
                env.write(env.getPromptSymbol() + " ");
                return ShellStatus.CONTINUE;
            }
        }
		
		try (InputStream is = Files.newInputStream(source, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(destination, StandardOpenOption.CREATE)) {
			byte[] buff = new byte[1024];
			while (true) {

				int r = is.read(buff);

				if (r < 1) {
					break;
				}

				os.write(buff, 0, r);
			}

		} catch (IOException e) {
			env.writeln("Invalid paths");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

        env.writeln("File copied");
        env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Copy command");
		list.add("Takes two argument, the source file name and destination file name");
		list.add("Copies file from source to destination, does not work with copying directories");
		list.add("Asks for permission to overwrite a file");
		return Collections.unmodifiableList(list);
	}

}
