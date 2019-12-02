package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;
/**
 * Extends {@link JMenu}, only purpose is to have a reference to {@link ILocalizationProvider}
 * to change title when language is changed
 * 
 * @author Patrik Okanovic
 *
 */
public class LJMenu extends JMenu{

	
	private static final long serialVersionUID = 1L;

	/**
	 * {@link ILocalizationProvider}
	 */
	@SuppressWarnings("unused")
	private ILocalizationProvider provider;
	/**
	 * {@link ILocalizationListener}
	 */
	private ILocalizationListener listener;
	/**
	 * Constructor of the class. Adds a listener to the provider, which sets 
	 * the text to the JMenu every time a language has been changed.
	 * 
	 * @param provider {@link ILocalizationProvider}
	 * @param key for the given name in each language
	 */
	public LJMenu(ILocalizationProvider provider,String key) {
		this.provider = provider;
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LJMenu.this.setText(provider.getString(key));
			}
		};
		listener.localizationChanged();//used for initial setting
		
		provider.addLocalizationListener(listener);
	}
}
