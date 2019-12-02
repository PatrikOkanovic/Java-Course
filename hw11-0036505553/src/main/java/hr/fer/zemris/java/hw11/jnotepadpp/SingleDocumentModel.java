package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 *  Represents a model of single document, having information about file path
 *	from which document was loaded (can be null for new document), document modification status
 * 	and reference to Swing component which is used for editing (each document has its own editor
 * 	component).
 * 
 * @author Patrik Okanovic
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns the text component of the {@link SingleDocumentModel}
	 * 
	 * @return {@link JTextArea}
	 */
	JTextArea getTextComponent();

	/**
	 * Returns the file path of the {@link SingleDocumentModel}
	 * 
	 * @return {@link Path}
	 */
	Path getFilePath();
	
	/**
	 * Sets the path file
	 * 
	 * @param path of the file
	 */
	void setFilePath(Path path);

	/**
	 * Returns true if the document has been modified
	 * Path cannot be null.
	 * 
	 * @return true if it has been modified
	 */
	boolean isModified();

	/**
	 * Sets the boolean modified.
	 * 
	 * @param modified boolean value
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds the {@link SingleDocumentListener}
	 * 
	 * @param l {@link SingleDocumentListener}
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes the {@link SingleDocumentListener}
	 * 
	 * @param l {@link SingleDocumentListener}
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
