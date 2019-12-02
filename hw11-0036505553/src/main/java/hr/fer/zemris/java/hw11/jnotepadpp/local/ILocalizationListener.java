package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Used as a listener for {@link ILocalizationProvider}
 * 
 * @author Patrik Okanovic
 *
 */
public interface ILocalizationListener {
	/**
	 * Called every time a change happens
	 */
	void localizationChanged();
}
