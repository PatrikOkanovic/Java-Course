package hr.fer.zemris.java.hw06.shell.massrename;
/**
 * Implementation of {@link NameBuilder}, appends to {@link StringBuilder} what is located
 * in {@link FilterResult} on the given index.
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderGroup implements NameBuilder{

	/**
	 * The index
	 */
	private int index;
	
	/**
	 * The constructor of the class
	 * @param index
	 */
	public NameBuilderGroup(int index) {
		this.index = index;
	}
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(result.group(index));
		
	}

}
