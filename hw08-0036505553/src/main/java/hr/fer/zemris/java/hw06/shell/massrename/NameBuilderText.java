package hr.fer.zemris.java.hw06.shell.massrename;
/**
 * Implementation of {@link NameBuilder}. Writes the string to the {@link StringBuilder}
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderText implements NameBuilder{

	/**
	 * The text
	 */
	private String text;
	
	/**
	 * Constructor of the class
	 * @param text
	 */
	public  NameBuilderText(String text) {
		this.text = text;
	}
	
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(text);
		
	}

}
