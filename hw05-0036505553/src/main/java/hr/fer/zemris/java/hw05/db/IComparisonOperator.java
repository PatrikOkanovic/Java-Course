package hr.fer.zemris.java.hw05.db;
/**
 * Represents ComparisonOperator.Defines one method 
 * which tells us if this ComparsionOperator is satisfied.
 * @author Patrik Okanovic
 *
 */
public interface IComparisonOperator {

	/**
	 * Returns true if the two parameters are in the right relation.
	 * @param value1
	 * @param value2
	 * @return
	 */
	public boolean satisfied(String value1, String value2);
}
