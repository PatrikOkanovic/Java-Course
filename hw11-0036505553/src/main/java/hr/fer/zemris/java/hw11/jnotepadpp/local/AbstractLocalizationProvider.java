package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link ILocalizationProvider}  and adds it the
 * ability to register, de-register and inform (fire() method) listeners. It is an abstract class – it does not
 * implement getString(...) method.
 * 
 * @author Patrik Okanovic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	/**
	 * Storage for {@link ILocalizationListener}
	 */
	List<ILocalizationListener> listeners = new ArrayList<>();
	
	/**
	 * Default constructor
	 */
	public AbstractLocalizationProvider() {
		
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies every listener that there has been a change
	 */
	public void fire() {
		listeners.forEach(l->l.localizationChanged());
	}


}
