package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 * An implementation of {@link SingleDocumentModel}.
 * Used to implement {@link JNotepadPP}
 * 
 * @author Patrik Okanovic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{
	/**
	 * The path
	 */
	private Path path;
	/**
	 * The text area component
	 */
	private JTextArea textComponent;
	/**
	 * Boolean to check if it has been modified
	 */
	private boolean isModified;
	/**
	 * List of {@link SingleDocumentListener}
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor of the class
	 * 
	 * @param path file location
	 * @param text text to be set to textComponent
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		
		this.path = path;
		
		textComponent = new JTextArea(text);
		
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				isModified = true;
				notifyListenersModifiedDocument();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				isModified = true;
				notifyListenersModifiedDocument();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				isModified = true;
				notifyListenersModifiedDocument();
			}
		});
		
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.path = path;
		notifyListenersPathUpdated();
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		notifyListenersModifiedDocument();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
		
	}
	
	/**
	 * Used to inform all {@link SingleDocumentListener} that the document has
	 * been modified.
	 */
	private void notifyListenersModifiedDocument() {
		listeners.forEach(l->l.documentModifyStatusUpdated(this));

	}
	
	/**
	 * Used to inform all {@link SingleDocumentListener} that file path
	 * has been updated
	 */
	private void notifyListenersPathUpdated() {
		listeners.forEach(l->l.documentFilePathUpdated(this));

	}
	

}
