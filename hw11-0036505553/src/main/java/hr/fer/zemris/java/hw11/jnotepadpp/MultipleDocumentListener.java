package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface used as a listener for the {@link MultipleDocumentModel}
 * 
 * @author Patrik Okanovic
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Called when current document is changed.
	 * PreviousModel or currentModel can be null but not both
	 * 
	 * @param previousModel 
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when a document is added.
	 * 
	 * @param model {@link SingleDocumentModel}
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when a document is removed.
	 * 
	 * @param model {@link SingleDocumentModel}
	 */
	void documentRemoved(SingleDocumentModel model);
}
