package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 * @version 2.0 Patrik Okanovic
 *
 */
public interface DAO {

	/**
	 * Gets the entry with the key <code>id</code>. If entry does not exist,
	 * returns <code>null</code>.
	 * 
	 * @param id key
	 * @return entry or <code>null</code> if does not exist
	 * @throws DAOException if there is error while returning
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns a list of current {@link BlogUser}
	 * 
	 * @return
	 */
	public List<BlogUser> getBlogUsers();

	/**
	 * Creates a new user in the database.
	 * 
	 * @param user
	 */
	public void saveUser(BlogUser user);

	/**
	 * Returns the user which is stored with the given nick and
	 * given hashPassword
	 * 
	 * @param nick
	 * @param hashPassword
	 * @return null if the user with the nick and hashPassword does not exist
	 */
	public BlogUser getUser(String nick, String hashPassword);

	/**
	 * Returns a list of {@link BlogEntry} for the given {@link BlogUser} the creator.
	 * 
	 * @param user
	 * @return
	 */
	public List<BlogEntry> getBlogEntries(BlogUser user);

	/**
	 * Returns a {@link BlogUser} with the given nick.
	 * 
	 * @param nickUrl
	 * @return {@link BlogUser}
	 */
	public BlogUser getUser(String nickUrl);

	/**
	 * Saves the given {@link BlogEntry} to the database.
	 * 
	 * @param blogEntry
	 */
	public void saveBlogEntry(BlogEntry blogEntry);

	/**
	 * Gets a list of {@link BlogComment} for the specified {@link BlogEntry}.
	 * If there is no blogComments return null.
	 * 
	 * @param blogEntry the key
	 * @return
	 */
	public List<BlogComment> getBlogComments(BlogEntry blogEntry);

	/**
	 * Method used to save blog comment.
	 * 
	 * @param blogComment the comment to be saved
	 */
	public void saveBlogComment(BlogComment blogComment);
	
}