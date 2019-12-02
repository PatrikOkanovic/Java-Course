package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Represents a database of students loaded from the file. 
 * @author Patrik Okanovic
 *
 */
public class StudentDatabase {

	/**
	 * Stores the {@link StudentRecord}
	 */
	private List<StudentRecord> list;
	/**
	 * Maps jmbag and {@link StudentRecord}
	 */
	private Map<String, StudentRecord> map;
	
	/**
	 * Creates a database of students from the list of string lines
	 * @param data
	 */
	public StudentDatabase(List<String> data) {
		map = new HashMap<>();
		list = new ArrayList<>();
		
		for(String line : data) {
			String[] student = line.split("\\s+");
			if(student.length != 4 && student.length != 5) {
				continue;
			}
			String jmbag = student[0];
			String surname = student[1];
			String name = null;
			String grade = null;
			if(student.length == 5) {
				surname += " ";
				surname += student[2];
				name = student[3];
				grade = student[4];
				
			} else {
				name = student[2];
				grade = student[3];
			}
			try {
				int gradeInt = Integer.parseInt(grade);
				if(gradeInt < 1 || gradeInt > 5) {
					continue;
				}
			} catch(NumberFormatException exc) {
				//ignore
			}
			
			StudentRecord newStudent = new StudentRecord(name, surname, grade, jmbag);
			if(!map.containsKey(jmbag)) {
				map.put(jmbag, newStudent);
				list.add(newStudent);
			}
		}
		
	}
	
	/**
	 * Returns the {@link StudentRecord} of an jmbag in O(1)
	 * @param jmbag
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}
	
	/**
	 * Filters the database of students and returns the list of those students
	 * who satisfy the filter
	 * @param filter
	 * @return
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<StudentRecord>();
		for(StudentRecord student : list) {
			if(filter.accepts(student)) {
				filteredList.add(student);
			}
		}
		return filteredList;
	}

}
