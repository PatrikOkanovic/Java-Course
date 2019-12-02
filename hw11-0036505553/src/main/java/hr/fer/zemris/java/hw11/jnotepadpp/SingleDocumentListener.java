package hr.fer.zemris.java.hw11.jnotepadpp;
/**
 * Interface used as a listener for {@link SingleDocumentModel}
 * 
 * @author Patrik Okanovic
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Called every time a document has been modified
	 * 
	 * @param model {@link SingleDocumentModel}
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Called every time a file path has been updated.
	 * 
	 * @param model {@link SingleDocumentModel}
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
