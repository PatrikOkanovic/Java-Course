package hr.fer.zemris.java.p12.dao;

import java.util.List;


import hr.fer.zemris.java.p12.dao.sql.model.Poll;
import hr.fer.zemris.java.p12.dao.sql.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 * @version 2.0 Patrik Okanovic
 *
 */
public interface DAO {

	/**
	 * Gets all existing {@link Poll} in the database.
	 * Sets id, title and message
	 * 
	 * @return List of {@link Poll}
	 * @throws DAOException
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Gets selected Poll by the id. If {@link Poll} doesn't exist returns 
	 * <code>null</code>
	 * Sets id, title and message.
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Poll getPollByID(long id) throws DAOException;
	
	/**
	 * Gets all existing {@link PollOption} in the database.
	 * 
	 * @return List of {@link PollOption}
	 * @throws DAOException
	 */
	public List<PollOption> getPollOptions() throws DAOException;
	
	/**
	 * Gets selected PollOption by the id. If {@link PollOption} doesn't exist returns 
	 * <code>null</code>
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public PollOption getPollOptionByID(long id) throws DAOException;

	/**
	 * Updates attribute voteCount by one for the attribute with the 
	 * given id and updates the database.
	 * 
	 * @param id of the PollOption to be increased by one
	 */
	public void performVoting(long id);

	/**
	 * Method returns sorted pollOptions by most votesCount for the current
	 * pollID
	 * 
	 * @param pollID
	 * @return
	 */
	public List<PollOption> getSorted(long pollID);
	
}