package hr.fer.zemris.java.hw05.db;
/**
 * Represents FieldValueGetter used as an strategy later for the database.
 * @author Patrik Okanovic
 *
 */
public interface IFieldValueGetter {

	/**
	 * Returns the wanted field.
	 * @param record
	 * @return
	 */
	public String get(StudentRecord record);

}
