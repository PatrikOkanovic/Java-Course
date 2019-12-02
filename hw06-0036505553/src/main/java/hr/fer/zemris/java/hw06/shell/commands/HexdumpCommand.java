package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
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
 * The Hexdump command takes one argument, a file name.
 * Produces a hex-output of the file.
 * 
 * @author Patrik Okanovic
 *
 */
public class HexdumpCommand implements ShellCommand{

	private static final int LOWER = 32;
	private static final int UPPER = 127;
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
			env.writeln("hexdump expects one argument");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path path;
		
		try {
			path = Paths.get(paths.get(0));
		} catch(InvalidPathException exc) {
			env.writeln("invalid path");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isRegularFile(path)) {
			env.writeln("able to create hexdump of a file only");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
			byte[] buff = new byte[16];
			int current = 0x0;
			
			while(true) {
				StringBuilder sb = new StringBuilder();
				int r = is.read(buff);
				
				if(r < 1) {
					break;
				}
				
				sb.append(String.format("%08X", current));
				sb.append(": ");
				
				for(int i = 0; i < 16; i++) {
					if(i >= r) {
						sb.append(" ".repeat(3));
						
					} else {
						sb.append(String.format("%02X", buff[i])).append(" ");
					}
					
					if(i == 7) {
						sb.setLength(sb.length()-1);
						sb.append("|");
					}
					
					
				}
				sb.append(" | ");
				
				for(int i = 0; i < r; i++) {
					if(buff[i] < LOWER || buff[i] > UPPER) {
						sb.append(".");
					} else {
						sb.append((char)buff[i]);
					}
				}
				
				
				env.writeln(sb.toString());
				current += 16;
			}
			
			
		} catch (IOException e) {
			env.writeln("Error while reading");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Hexdump command");
		list.add("Takes one argument, a file name");
		list.add("Produces a hex-output of the file");
		return Collections.unmodifiableList(list);
	}

}
