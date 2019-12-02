package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 *  Represents a model capable of holding zero, one or more documents,
 *	where each document and having a concept of current document – the one which is shown to the
 * 	user and on which user works.
 * 
 * @author Patrik Okanovic
 * 
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates a new document.
	 * 
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns the current document.
	 * 
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Load the document from the given file.
	 * Path cannot be null.
	 * 
	 * @param path location of the document to be loaded
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the current document.
	 * NewPath can be null, if null, document should be saved using path associated from 
	 * document, otherwise, new path should be used and after saving is completed, document’s path should be
	 * updated to newPath
	 * 
	 * @param model {@link SingleDocumentModel} to be saved
	 * @param newPath path where the document will be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Close the document given as the parameter.
	 * Removes specified document (does not check modification status or ask any questions)
	 * 
	 * @param model to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds the {@link MultipleDocumentListener}
	 * 
	 * @param l {@link MultipleDocumentListener}
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes the {@link MultipleDocumentListener}
	 * 
	 * @param l {@link MultipleDocumentListener}
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of documents stored
	 * 
	 * @return number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns the document model from the given index.
	 * 
	 * @param index of the wanted {@link SingleDocumentModel}
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel getDocument(int index);
}

