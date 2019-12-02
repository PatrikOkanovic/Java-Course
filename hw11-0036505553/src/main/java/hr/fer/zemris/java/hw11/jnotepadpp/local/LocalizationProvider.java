package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Extends {@link AbstractLocalizationProvider}. 
 * is a class that is singleton (so it has private constructor, private static
 * instance reference and public static getter method).
 * Constructor sets the language to “en” by default. It also loads the resource bundle for this language and
 * stores reference to it. Method getString uses loaded resource bundle to translate the requested key
 * 
 * @author Patrik Okanovic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/**
	 * Instance of {@link LocalizationProvider}
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * The {@link ResourceBundle}
	 */
	private ResourceBundle bundle;
	/**
	 * Current language
	 */
	private String language;
	/**
	 * Default constructor
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}		
	/**
	 * Returns an instance of {@link LocalizationProvider}. Pattern singleton.
	 * 
	 * @return {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
			
	@Override
	public String getString(String s) {
		return bundle.getString(s);
	}
	
	/**
	 * Used to set the current language
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
		
		Locale locale = Locale.forLanguageTag(language);
		
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale);
		this.fire();
	}
	

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
