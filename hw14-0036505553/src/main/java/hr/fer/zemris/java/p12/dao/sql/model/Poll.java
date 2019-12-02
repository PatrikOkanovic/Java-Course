package hr.fer.zemris.java.p12.dao.sql.model;

import java.util.Objects;
/**
 * Model of data used to represent a poll.
 * 
 * @author Patrik Okanovic
 *
 */
public class Poll {

	/**
	 * ID of the poll
	 */
	private long id;
	/**
	 * The title
	 */
	private String title;
	/**
	 * The message
	 */
	private String message;
	/**
	 * Relative path where the {@link PollOption} definition textual file
	 * for this instance of poll is located
	 */
	private String definitionOfPollOptions;
	
	/**
	 * Constructor of the class.
	 * 
	 * @param id
	 * @param title
	 * @param message
	 * @param definitionOfPollOptions
	 */
	public Poll(int id, String title, String message,String definitionOfPollOptions ) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.definitionOfPollOptions = definitionOfPollOptions;
	}
	/**
	 * Basic constructor
	 */
	public Poll() {
		
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 
	 * @return the definitionOfPollOptions
	 */
	public String getDefinitionOfPollOptions() {
		return definitionOfPollOptions;
	}

	/**
	 * 
	 * @param definitionOfPollOptions the definitionOfPollOptions to set
	 */
	public void setDefinitionOfPollOptions(String definitionOfPollOptions) {
		this.definitionOfPollOptions = definitionOfPollOptions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, message, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poll other = (Poll) obj;
		return id == other.id && Objects.equals(message, other.message) && Objects.equals(title, other.title);
	}

	

	
	
	
}
