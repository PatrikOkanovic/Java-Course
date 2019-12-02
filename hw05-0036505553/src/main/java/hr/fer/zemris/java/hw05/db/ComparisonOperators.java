package hr.fer.zemris.java.hw05.db;
/**
 * Implements several comparison operators used in out Query Student Database
 * @author Patrik Okanovic
 *
 */
public class ComparisonOperators{

	/**
	 * Compares if the first string is less than the other
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;
	/**
	 * Compares if the first string is less or equal than the other
	 */												
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
	/**
	 * Compares if the first string is greater than the other
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
	/**
	 * Compares if the first string is greater or equal than the other
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
	/**
	 * Compares if the first string is equal to other
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.equals(value2);
	/**
	 * Compares if the first string is not equal to other
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> !value1.equals(value2);
	/**
	 * Tests if the string is like the pattern. * represents every other or zero characters at the given place
	 * '*' can be at the beginning, in the end or in the middle.
	 * @throws IllegalArgumentException if there are more than one '*'
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String s, String pattern) {
			if(!pattern.contains("*")) {
				return s.equals(pattern);
			} else {
				if(pattern.indexOf('*') != pattern.lastIndexOf('*')) {
					throw new IllegalArgumentException("More than one *");
				}
				int index = pattern.indexOf('*');
				String substring = null;
				
				if(pattern.startsWith("*")) {
					substring = pattern.substring(1);
					return s.endsWith(substring);
					
				} else if(pattern.endsWith("*")) {
					substring = pattern.substring(0, index);
					return s.startsWith(substring);
					
				} else {
					if(pattern.length()-1 > s.length()) {
						return false;
					}
					String firstSubstring = pattern.substring(0, index);
					String secondSubstring = pattern.substring(index+1, pattern.length());
					return s.startsWith(firstSubstring) && s.endsWith(secondSubstring);
				}
			}
		}
	};
			
	
}
