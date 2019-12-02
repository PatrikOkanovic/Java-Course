package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * A program which represents working with streams.
 * 
 * @author Patrik Okanovic
 *
 */
public class StudentDemo {

	/**
	 * Main method of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					 Paths.get("studenti.txt"),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("Could not read the file");
			System.exit(1);
		}
		
		List<StudentRecord> records = convert(lines);
		
		System.out.println("Zadatak 1");
		System.out.println("=========");
		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);
		
		System.out.println("Zadatak 2");
		System.out.println("=========");
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5);
		
		System.out.println("Zadatak 3");
		System.out.println("=========");
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		odlikasi.forEach(s->System.out.println(s));
		
		
		System.out.println("Zadatak 4");
		System.out.println("=========");
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.forEach(s -> System.out.println(s));
		
		
		System.out.println("Zadatak 5");
		System.out.println("=========");
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		nepolozeniJMBAGovi.forEach(s -> System.out.println(s));
		
		System.out.println("Zadatak 6");
		System.out.println("=========");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		for(Integer key : mapaPoOcjenama.keySet()) {
			System.out.println(key + "=>");
			List<StudentRecord> list = mapaPoOcjenama.get(key);
			list.forEach(s -> System.out.println(s));
		}
		
		System.out.println("Zadatak 7");
		System.out.println("=========");
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		mapaPoOcjenama2.forEach((o1,o2)->System.out.println(o1+"=>"+o2));
		
		System.out.println("Zadatak 8");
		System.out.println("=========");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("prosli");
		prolazNeprolaz.get(true).forEach(s -> System.out.println(s));
		System.out.println("pali");
		prolazNeprolaz.get(false).forEach(s -> System.out.println(s));
		

	}
	/**
	 * Returns a number of students with points over 25
	 * 
	 * @param records
	 * @return number
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				   .filter(s -> s.getPointsLAB() + s.getPointsMI() + s.getPointsZI() > 25)
				   .count();
	}
	/**
	 * Number of students whose grade is 5.
	 * 
	 * @param records
	 * @return number
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
			    .filter(s -> s.getGrade() == 5)
			    .count();
	}
	
	/**
	 * Creates a list of students whose grade is 5.
	 * 
	 * @param records
	 * @return {@link List} of {@link StudentRecord}
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				  .filter(s -> s.getGrade() == 5)
				  .map(Function.identity())
				  .collect(Collectors.toList());
	}
	
	/**
	 * Returns a sorted list of students whose grade is 5.
	 * 
	 * @param records
	 * @return {@link List} of {@link StudentRecord}
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				   .filter(s -> s.getGrade() == 5)
				   .sorted((s1,s2)->(Double.valueOf(s2.getPointsLAB()+s2.getPointsMI()+s2.getPointsZI()).compareTo(Double.valueOf(s1.getPointsLAB()+s1.getPointsMI()+s1.getPointsZI()))))
				   .map(Function.identity())
				   .collect(Collectors.toList());
	}
	
	/**
	 * Returns a list of students who have failed, whose grade is 1.
	 * 
	 * @param records
	 * @return{@link List} of {@link StudentRecord}
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				      .filter(s -> (s.getGrade() == 1))
				      .map(s -> s.getJmbag())
				      .sorted()
				      .collect(Collectors.toList());

				    		  
				   
	}
	/**
	 * Returns a map which key is a grade, and value is an {@link List} of {@link StudentRecord} who got that grade
	 * 
	 * @param records
	 * @return mapping of grade with list of students
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				      .collect(Collectors.groupingBy(StudentRecord::getGrade));
	}
	
	/**
	 * Creates a map which key is a grade and value is number of students who got that grade.
	 * 
	 * @param records
	 * @return
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				     .collect(Collectors.toMap(StudentRecord::getGrade
				    		 					, s -> 1, 
				    		 					(oldValue,newValue) -> oldValue + newValue));
	}
	/**
	 * Creates a map which key true if students passed and false if students failed, and value
	 * is a list of students
	 * 
	 * @param records
	 * @return mapping of students that passed and those who failed
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				      .collect(Collectors.partitioningBy(s-> s.getGrade() >= 2));
	}

	/**
	 * Creates a list of {@link StudentRecord} from a list of string lines containing inforamtion
	 * about the student
	 * 
	 * @param lines
	 * @return list of {@link StudentRecord}
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list = new ArrayList<>();
		
		for(String line : lines) {
			String[] parts = line.split("\t");
			String jmbag = parts[0];
			String lastName = parts[1];
			String firstName = parts[2];
			double pointsMI,pointsZI,pointsLAB;
			int grade;
			
			try {
				pointsMI = Double.parseDouble(parts[3]);
				pointsZI = Double.parseDouble(parts[4]);
				pointsLAB = Double.parseDouble(parts[5]);
				grade = Integer.parseInt(parts[6]);
			} catch (NumberFormatException exc) {
				throw new IllegalArgumentException("There is a problem with a student in the database.");
			}
			
			list.add(new StudentRecord(jmbag, firstName, lastName, pointsMI, pointsZI, pointsLAB,grade));
			
		}
		
		return list;
		

	}

}
