package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
/**
 * Represents a model of document, with its path and its tfidf {@link Vector}.
 * 
 * @author Patrik Okanovic
 *
 */
public class Document {

	private Path path;
	
	private Vector tfidf;

	/**
	 * @return the tfidf
	 */
	public Vector getTfidf() {
		return tfidf;
	}

	/**
	 * @param tfidf the tfidf to set
	 */
	public void setTfidf(Vector tfidf) {
		this.tfidf = tfidf;
	}

	/**
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	

}
