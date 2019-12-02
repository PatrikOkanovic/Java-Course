package hr.fer.zemris.java.hw06.shell.massrename;
/**
 * Implementation of {@link NameBuilder}, appends what is located on index of the group from {@link FilterResult}.
 * Fills the {@link StringBuilder} with padding until the minWidth has been reached.
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderGroupWithPadding implements NameBuilder{

	/**
	 * The index
	 */
	private int index;
	/**
	 * The padding
	 */
	private char padding;
	/**
	 * The minWidth
	 */
	private int minWidth;
	/**
	 * Constructor of the class
	 * 
	 * @param index
	 * @param padding
	 * @param minWidth
	 */
	public NameBuilderGroupWithPadding(int index, char padding, int minWidth) {
		this.index = index;
		this.padding = padding;
		this.minWidth = minWidth;
	}
	
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		StringBuilder builder = new StringBuilder();
		builder.append(result.group(index));
		
		while(builder.length() < minWidth) {
			builder.insert(0,padding);
		}
		
		sb.append(builder.toString());
		
	}

}
