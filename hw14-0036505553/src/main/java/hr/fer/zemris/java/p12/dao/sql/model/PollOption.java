package hr.fer.zemris.java.p12.dao.sql.model;

import java.util.Objects;
/**
 * A model which represents a simple implementation of a PollOption in a {@link Poll}
 * 
 * @author Patrik Okanovic
 *
 */
public class PollOption {
	/**
	 * The optionTitle
	 */
	private String optionTitle;
	/**
	 * The optionLink
	 */
	private String optionLink;
	/**
	 * Number of votes counted
	 */
	private long votesCount;
	/**
	 * ID of the poll
	 */
	private long pollID;
	/**
	 * Id of the {@link PollOption}
	 */
	private long id;
	/**
	 * Constructor of the class.
	 * 
	 * @param id
	 * @param optionTitle
	 * @param optionLink
	 * @param votesCount
	 * @param pollID
	 */
	public PollOption(long id,String optionTitle, String optionLink, int votesCount, long pollID) {
		super();
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
		this.pollID = pollID;
		this.id = id;
	}

	/**
	 * Basic constructor
	 */
	public PollOption() {
	}

	/**
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	/**
	 * @return the pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * @param pollID the pollID to set
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, optionLink, optionTitle, pollID, votesCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollOption other = (PollOption) obj;
		return id == other.id && Objects.equals(optionLink, other.optionLink)
				&& Objects.equals(optionTitle, other.optionTitle) && pollID == other.pollID
				&& votesCount == other.votesCount;
	}
	
	

}
