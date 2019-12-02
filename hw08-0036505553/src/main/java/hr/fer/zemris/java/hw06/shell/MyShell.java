package hr.fer.zemris.java.hw06.shell;
/**
 * Implementation of shell. With commands and a working environment
 * 
 * @author Patrik Okanovic
 *
 */
public class MyShell {

	/**
	 * Main method of the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Environment env = new EnvironmentImpl();
		ShellStatus status = ShellStatus.CONTINUE;
		do {
			String line = env.readLine();
			
			if (line.endsWith(env.getMorelinesSymbol().toString()) && line.charAt(line.length()-2)==' ') {
				StringBuilder sb = new StringBuilder();
				while (line.endsWith(env.getMorelinesSymbol().toString()) && line.charAt(line.length()-2)==' ') {
					sb.append(line, 0, line.length() - 1); // must not add the morelines symbol
					env.write(env.getMultilineSymbol() + " ");
					line = env.readLine();
				}
				sb.append(line);
				line = sb.toString();

			}
			String commandName = extractCommandName(line);
			String arguments = extractArguments(line);
			
			ShellCommand command = env.commands().get(commandName);
			
			if(command == null) {
				env.writeln("No such command!");
				env.write(env.getPromptSymbol() + " ");
			} else {
				try {
					status = command.executeCommand(env, arguments);
				} catch(ShellIOException exc) {
					env.writeln(exc.getMessage());
					env.write(env.getPromptSymbol()+" ");
				}
			}
		} while(status != ShellStatus.TERMINATE);
	}

	/**
	 * Used to extract arguments from the command given to the shell.
	 * 
	 * @param line
	 * @return arguments
	 */
	private static String extractArguments(String line) {
		String[] splited = line.trim().split("\\s+");
		if(splited.length == 1) {
			return "";
		}
//		int index = line.indexOf(splited[1]);
//		if(splited[1].equals("help") && splited[0].equals("help") && splited.length==2) {
//			return "help";
//		}
//		
//		return line.substring(index);
		return line.trim().substring(splited[0].length()).trim();
	}

	/**
	 * Used to get the name of the command from the input to the shell.
	 * 
	 * @param line
	 * @return command name
	 */
	private static String extractCommandName(String line) {
		String[] splited = line.trim().split("\\s+");
		return splited[0];
	}
	
}
