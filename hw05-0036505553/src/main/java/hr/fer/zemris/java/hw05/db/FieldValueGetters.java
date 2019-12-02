package hr.fer.zemris.java.hw05.db;

/**
 * Represents the field getters after parsing the query.
 * @author Patrik Okanovic
 *
 */
public class FieldValueGetters {

	/**
	 * Getter for the first name
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getName();
	/**
	 * Getter for the last name or surname
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getSurname();
	/**
	 * Getter for the jmbag of student
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
			
}
