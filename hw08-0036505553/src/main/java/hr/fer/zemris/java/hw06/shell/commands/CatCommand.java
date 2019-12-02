package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.parser.ShellParser;
import hr.fer.zemris.java.hw06.shell.parser.ShellParserException;
/**
 * The Cat command takes one argument or two arguments, first argument is name of the file, 
 * second is charset, second argument is not obligatory. 
 * Writes the content of the file to the environment using the given charset.
 * 
 * @author Patrik Okanovic
 *
 */
public class CatCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
        
        ShellParser parser;
		List<String> list;
		
		try {
			parser = new ShellParser(arguments);
			
		} catch(ShellParserException exc) {
			env.writeln("The given arguments are unsupported");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		list = parser.getPaths();
		
		if(list.size() != 1 && list.size()!=2) {
			env.writeln("cat expects one or two arguments");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path p;
		Charset charset = Charset.defaultCharset();
		
		if(list.size() == 2) {
			try {
				charset = Charset.forName(list.get(1));
			} catch(IllegalCharsetNameException | UnsupportedCharsetException exc) {
				env.writeln("Unsupported charset");
                env.write(env.getPromptSymbol() + " ");
                return ShellStatus.CONTINUE;
            }
			
			p = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));
			
		} else {
			p = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));
		}
		
		try(InputStream is = Files.newInputStream(p, StandardOpenOption.READ)) {
			
			byte[] buff = new byte[1024];
			
			while(true) {
				
				int r = is.read(buff);
				
				if(r < 1) {
					break;
				}
				
				String line = new String(Arrays.copyOf(buff, r), charset);
				env.write(line);
				
			}
		} catch (IOException e) {
			env.writeln("Error while reading file");
            env.write(env.getPromptSymbol() + " ");
            return ShellStatus.CONTINUE;
		}
		
		env.writeln("");
		env.write(env.getPromptSymbol() + " ");
        return ShellStatus.CONTINUE;
	
	}
	

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Cat command");
		list.add("Takes one argument or two arguments, first argument is file, "
				+ "second charset, second argument is not obligatory");
		list.add("Writes the content of the file to the environment");
		return Collections.unmodifiableList(list);
	}

}
