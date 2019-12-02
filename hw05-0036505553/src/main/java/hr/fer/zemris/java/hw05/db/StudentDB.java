package hr.fer.zemris.java.hw05.db;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Contains a main method combining everything from the package to load a database and interact
 * with the user. Key words are query and exit.
 * Creates a visual table based on the interaction with the user.
 * @author Patrik Okanovic
 *
 */
public class StudentDB {

	/**
	 * The main method of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					 Paths.get("database.txt"),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("Could not read the file");
			System.exit(1);
		}
		
		StudentDatabase database = new StudentDatabase(lines);
		
		try(Scanner sc = new Scanner(System.in)) {
			while(true) {
				
				System.out.printf("> ");
				
				if(sc.hasNext()) {
					String line = sc.nextLine();
					
					if(line.trim().startsWith("exit")) {
						System.out.println("Goodbye!");
						break;
					}
					
					if(! line.trim().startsWith("query")) {
						System.out.println("Invalid input, you must start with query.");
						continue;
					}
					QueryParser parser;
					try {
						parser = new QueryParser(line.trim().substring(5));
						
					} catch(QueryParserException exc) {
						System.out.println("The line was not parseable, enter again.");
						continue;
					}
					
					List<StudentRecord> list;
					if(parser.isDirectQuery()) {
						System.out.println("Using index for record retrieval.");
						StudentRecord student = database.forJMBAG(parser.getQueriedJMBAG());
						list = new ArrayList<StudentRecord>();
						if(student != null) {
							list.add(student);
						}
					} else {
						list = database.filter(new QueryFilter(parser.getQuery()));
					}
					
					if(!list.isEmpty()) {
						List<String> output = RecordFormatter.format(list);
						output.forEach(System.out::println);
					}
					
					
					System.out.println("Records selected: " + list.size() + "\n");
					
					
				}
			}
		}
	}

	
}
