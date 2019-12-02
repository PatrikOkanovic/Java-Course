package hr.fer.zemris.java.hw11.jnotepadpp.local;


/**
 * Used as a decorator for some other {@link ILocalizationProvider}.
 * This class offers two additional methods: connect() and disconnect(), and it
 * manages a connection status (so that you can not connect if you are already connected). Here is the idea: this
 * class is ILocalizationProvider which, when asked to resolve a key delegates this request to wrapped
 * (decorated) ILocalizationProvider object. When user calls connect() on it, the method will register an
 * instance of anonimous ILocalizationListener on the decorated object. When user calls disconnect(),
 * this object will be deregistered from decorated object.
 * 
 * @author Patrik Okanovic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	/**
	 * Boolean to check whether it is connected
	 */
	private boolean connected;
	
	/**
	 * Used in pattern decorator
	 */
	private ILocalizationProvider parent;
	/**
	 * Listener for changes
	 */
	private ILocalizationListener listener;
	/**
	 * The constructor of the class.
	 * 
	 * @param parent
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	/**
	 * Used for connecting with the provider and adding to the listeners list
	 */
	public void connect() {
		if(!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}
	/**
	 * Removing connection with the provider and removing from the listeners list.
	 */
	public void disconnect() {
		if(connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
	

}
