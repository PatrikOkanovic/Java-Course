package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;
/**
 * Extends {@link AbstractAction}, adds a listener who translates
 * everything every time when an different language has been selected.
 * 
 * @author Patrik Okanovic
 *
 */
public abstract class LocalizableAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	/**
	 * Listener of the changes
	 */
	private ILocalizationListener listener;
	/**
	 * Instance of {@link ILocalizationProvider}
	 */
	private ILocalizationProvider flp;
	/**
	 * Key for translating the action
	 */
	@SuppressWarnings("unused")
	private String key;
	/**
	 * The constructor of the class.
	 * Adds a listener who sets the NAME and SHORT_DESCRIPTION for the
	 * selected language.
	 * 
	 * @param key for translating
	 * @param flp {@link ILocalizationProvider}
	 */
	public LocalizableAction(String key, ILocalizationProvider flp) {
		this.flp = flp;
		this.key = key;
		
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, flp.getString(key));
				putValue(Action.SHORT_DESCRIPTION, flp.getString(key + "DESC"));
				
			}
		};
		listener.localizationChanged();//used for initial setting
		this.flp.addLocalizationListener(listener);
	}
}
