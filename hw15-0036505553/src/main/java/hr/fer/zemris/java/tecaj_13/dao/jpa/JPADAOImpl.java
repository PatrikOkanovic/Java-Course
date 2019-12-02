package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
/**
 * Implementation of {@link DAO}
 * 
 * @author Patrik Okanovic
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getBlogUsers() {
		try {
			List<BlogUser> list = JPAEMProvider.getEntityManager()
									.createQuery("SELECT b FROM BlogUser as b", BlogUser.class)
									.getResultList();
			return list;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public void saveUser(BlogUser user) {
		JPAEMProvider.getEntityManager().persist(user);
		JPAEMProvider.close();
		
	}

	@Override
	public BlogUser getUser(String nickName, String hashPassword) {
		try {
			BlogUser user = JPAEMProvider.getEntityManager()
							.createQuery("SELECT b FROM BlogUser as b "
										+ "WHERE b.nick=:nickName"
										+ " AND b.passwordHash=:hashPassword", BlogUser.class)
							.setParameter("nickName", nickName)
							.setParameter("hashPassword", hashPassword)
							.getSingleResult();
			return user;
		} catch(NoResultException e) {
			return null;
		}
	}


	@Override
	public BlogUser getUser(String nickName) {
		try {
			BlogUser user = JPAEMProvider.getEntityManager()
							.createQuery("SELECT b FROM BlogUser as b "
										+ "WHERE b.nick=:nickName"
										, BlogUser.class)
							.setParameter("nickName", nickName)
							.getSingleResult();
			return user;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) {
		List<BlogEntry> entries = null;
		try {
			entries =JPAEMProvider.getEntityManager().createQuery("select u from BlogEntry u where u.creator=:creator",BlogEntry.class)
				.setParameter("creator", user)
				.getResultList();
		} catch(NoResultException e){
			
		}
		JPAEMProvider.close();
		return entries;
	}
	

	@Override
	public void saveBlogEntry(BlogEntry blogEntry) {
		JPAEMProvider.getEntityManager().persist(blogEntry);
		JPAEMProvider.close();
	}

	@Override
	public List<BlogComment> getBlogComments(BlogEntry blogEntry) {
		try {
			List<BlogComment> list = JPAEMProvider.getEntityManager()
					.createQuery("SELECT b FROM BlogComment as b "
							+ "WHERE b.blogEntry=:blogEntry", BlogComment.class)
					.setParameter("blogEntry", blogEntry)
					.getResultList();
			return list;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public void saveBlogComment(BlogComment blogComment) {
		JPAEMProvider.getEntityManager().persist(blogComment);
		JPAEMProvider.close();
	}

}