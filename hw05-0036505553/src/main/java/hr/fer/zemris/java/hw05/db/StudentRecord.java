package hr.fer.zemris.java.hw05.db;

import java.util.Objects;
/**
 * Represents a astudent for the {@link StudentDatabase}
 * @author Patrik Okanovic
 *
 */
public class StudentRecord {

	/**
	 * The first name.
	 */
	private String name;
	/**
	 * The last name
	 */
	private String surname;
	/**
	 * The final grade of the student
	 */
	private String grade;
	/**
	 * The jmbag
	 */
	private String jmbag;
	/** 
	 * Creates a student
	 * @param name
	 * @param surname
	 * @param grade
	 * @param jmbag
	 * @throws NullPointerException if anything is null
	 */
	public StudentRecord(String name, String surname, String grade, String jmbag) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(surname);
		Objects.requireNonNull(grade);
		Objects.requireNonNull(jmbag);

		this.name = name;
		this.surname = surname;
		this.grade = grade;
		this.jmbag = jmbag;
	}

	/**
	 * Returns the fist name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the last name
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Returns the grade
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * Returns the jmbag
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
	
	
	
}
