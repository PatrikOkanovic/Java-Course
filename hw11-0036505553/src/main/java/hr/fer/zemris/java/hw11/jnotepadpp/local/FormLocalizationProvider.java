package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class derived from {@link LocalizationProviderBridge}.
 * In its constructor it
 * registers itself as a WindowListener to its JFrame; when frame is opened, it calls connect and when
 * frame is closed, it calls disconnect. 
 * 
 * @author Patrik Okanovic
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructor of the class, adds a {@link WindowListener} to the {@link JFrame}.
	 * 
	 * @param parent {@link ILocalizationProvider}
	 * @param frame {@link JFrame}
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
			}
		});
	}

}
