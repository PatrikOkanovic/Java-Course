package hr.fer.zemris.java.hw05.db;

/**
 * Interface which filters the given {@link StudentRecord}
 * @author PAtrik Okanovic
 *
 */
public interface IFilter {

	/**
	 * Returns true if the student is accepted
	 * @param record
	 * @return
	 */
	public boolean accepts(StudentRecord record);
}
