package hr.fer.zemris.java.jsdemo.model;
/**
 * Class used to represent a domain of our problem, the photo.
 * 
 * @author Patrik Okanovic
 *
 */
public class Photo {

	/**
	 * title of the photo
	 */
	private String title;
	
	/**
	 * the tags of the photo
	 */
	private String[] tags;
	
	/**
	 * 
	 */
	private String fileName;

	/**
	 * Constructor of the class
	 * 
	 * @param title
	 * @param tags
	 * @param fileName
	 */
	public Photo(String title, String[] tags, String fileName) {
		super();
		this.title = title;
		this.tags = tags;
		this.fileName = fileName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	
}
