package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * Represents a domain for our problem. The owner of a {@link BlogEntry}.
 * Stored in the database.
 * 
 * @author Patrik Okanovic
 *
 */
@Entity
@Table(name="blog_users")
public class BlogUser {
	
	/**
	 * ID of the blogUser
	 */
	private Long id;
	/**
	 * first name of the blogUser
	 */
	private String firstName;
	/**
	 * last name of the blogUser
	 */
	private String lastName;
	/**
	 * nick of the blogUser, must be unique
	 */
	private String nick;
	
	private String email;
	/**
	 * password of the blogUser, created with-MessageDigest
	 */
	private String passwordHash;
	
	/**
	 * Collection of blog entries of a blogUser, one user can have more blogEntries
	 * but a blogEntry can be with one blogUser
	 */
	private List<BlogEntry> blogEntries = new ArrayList<>();

	/**
	 * @return the blogEntries
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * @param blogEntries the blogEntries to set
	 */
	
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	@Column(length=100,nullable=false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(length=100,nullable=false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	@Column(length=100,nullable=false,unique=true)
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the email
	 */
	@Column(length=100,nullable=false)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwordHash
	 */
	@Column(length=100,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	

}
