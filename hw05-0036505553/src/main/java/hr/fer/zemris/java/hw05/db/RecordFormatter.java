package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
/**
 * Used to create a visualization of the table.
 * 
 * @author Patrik Okanovic
 *
 */
public class RecordFormatter {

	/**
	 * Creates the visualization of the table.
	 * @param list
	 * @return List with Strings of formatted table
	 */
	public static List<String> format(List<StudentRecord> list) {
		List<String> returnList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int longestName = 0, longestLastName = 0, longestJmbag, longestGrade;
		
		longestJmbag = list.get(0).getJmbag().length(); //all jmbags are supposed to be the same size
		longestGrade = 1; 
		
		OptionalInt optIntName = list.stream()
						  .map(record -> record.getName())
						  .mapToInt(name -> name.length())
						  .max();
		if(optIntName.isPresent()) {
			longestName = optIntName.getAsInt();
		}
		
		OptionalInt optIntLastName = list.stream()
									 .map(record -> record.getSurname())
									 .mapToInt(lastname -> lastname.length())
									 .max();
		
		if(optIntLastName.isPresent()) {
			longestLastName = optIntLastName.getAsInt();
		}
		
		appendFrame(sb, longestName,longestLastName, longestGrade, longestJmbag);
		returnList.add(sb.toString());
		sb.setLength(0);
		
		for(StudentRecord student : list) {
			sb.append("| ")
			  .append(student.getJmbag() + " | ")
			  .append(student.getSurname());
			for(int i = 0; i < longestLastName - student.getSurname().length() + 1; i++) {
				sb.append(" ");
			}
			sb.append("| " + student.getName());
			for(int i = 0; i < longestName - student.getName().length() + 1; i++) {
				sb.append(" ");
			}
			sb.append("| " + student.getGrade() + " |");
			returnList.add(sb.toString());
			sb.setLength(0);
		}
		
		appendFrame(sb, longestName, longestLastName, longestGrade, longestJmbag);
		returnList.add(sb.toString());
		return returnList;
		
	}

	/**
	 * Appends the frame at the top and the bottom of our visualization.
	 * @param sb
	 * @param longestName
	 * @param longestLastName
	 * @param longestGrade
	 * @param longestJmbag
	 */
	private static void appendFrame(StringBuilder sb, int longestName, int longestLastName, int longestGrade, int longestJmbag) {
		
		sb.append("+");
		for(int i = 0; i < longestJmbag + 2; i++) {
			sb.append("=");
		}
		
		sb.append("+");
		for(int i = 0; i < longestLastName + 2; i++) {
			sb.append("=");
		}
		
		sb.append("+");
		for(int i = 0; i < longestName + 2; i++) {
			sb.append("=");
		}
		
		sb.append("+");
		for(int i = 0; i < longestGrade + 2; i++) {
			sb.append("=");
		}
		
		sb.append("+");
	}

	
}
