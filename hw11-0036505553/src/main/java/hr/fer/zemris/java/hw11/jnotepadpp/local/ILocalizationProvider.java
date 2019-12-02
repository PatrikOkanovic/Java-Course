package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface used to represent provider for translation.
 * Defines communication between listeners when they are added as listeners
 * 
 * @author Patrik Okanovic
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds the {@link ILocalizationListener}
	 * 
	 * @param l {@link ILocalizationListener}
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes the {@link ILocalizationListener}
	 * 
	 * @param l {@link ILocalizationListener}
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Returns the translation for the given key.
	 * 
	 * @param key key for the translation
	 * @return translated word for the given key
	 */
	String getString(String key);
	
	/**
	 * Returns the current language
	 * 
	 * @return current lanugage
	 */
	String getCurrentLanguage();
}
