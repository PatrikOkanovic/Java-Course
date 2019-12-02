package hr.fer.zemris.java.hw06.shell.massrename;

import java.util.List;
/**
 * Used for composing the {@link NameBuilder},  calls every execute of the builders.
 * 
 * @author Patrik Okanovic
 *
 */
public class NameBuilderComposite implements NameBuilder{

	/**
	 * List of {@link NameBuilder}
	 */
	private List<NameBuilder> list;
	
	/**
	 * Constructor of the class.
	 * @param list
	 */
	public NameBuilderComposite(List<NameBuilder> list) {
		this.list = list;
	}
	
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for(NameBuilder builder : list) {
			builder.execute(result, sb);
		}
		
	}

}
