package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;
/**
 * Represents a a student with some information
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
	private int grade;
	/**
	 * The jmbag
	 */
	private String jmbag;
	/**
	 * Points from midterm exam
	 */
	private double pointsMI;
	/**
	 * Points from final exam
	 */
	private double pointsZI;
	/**
	 * Points from laboratories
	 */
	private double pointsLAB;
	/** 
	 * Creates a student
	 * @param name
	 * @param surname
	 * @param grade
	 * @param jmbag
	 * @throws NullPointerException if anything is null
	 * @throws IllegalArgumentException if grade is not in 1 to 5
	 */
	public StudentRecord(String jmbag,String name, String surname, double pointsMI, double pointsZI, double pointsLAB,int grade) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(surname);
		Objects.requireNonNull(jmbag);
		
		if(grade < 1 || grade > 5) {
			throw new IllegalArgumentException("Grade must be in interval 1 to 5");
		}

		this.name = name;
		this.surname = surname;
		this.grade = grade;
		this.jmbag = jmbag;
		this.pointsMI = pointsMI;
		this.pointsZI = pointsZI;
		this.pointsLAB = pointsLAB;
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
	public int getGrade() {
		return grade;
	}

	/**
	 * Returns the points of midterm exam
	 * @return the pointsMI
	 */
	public double getPointsMI() {
		return pointsMI;
	}

	/**
	 * Returns the points of final exam
	 * @return the pointsZI
	 */
	public double getPointsZI() {
		return pointsZI;
	}

	/**
	 * Returns the points of laboratories
	 * @return the pointsLAB
	 */
	public double getPointsLAB() {
		return pointsLAB;
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
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag);
		sb.append('\t');
		sb.append(surname);
		sb.append('\t');
		sb.append(name);
		sb.append('\t');
		sb.append(pointsMI);
		sb.append('\t');
		sb.append(pointsZI);
		sb.append('\t');
		sb.append(pointsLAB);
		sb.append('\t');
		sb.append(grade);
		return sb.toString();
	}
	
}

