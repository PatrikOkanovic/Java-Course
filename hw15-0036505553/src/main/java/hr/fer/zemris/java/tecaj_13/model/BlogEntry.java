package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * Represents a domain for our problem. A {@link BlogEntry} created by {@link BlogUser},
 * with its {@link BlogComment}. Stored in the database.
 * 
 * @author Patrik Okanovic
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
public class BlogEntry {

	/**
	 * The id
	 */
	private Long id;
	/**
	 * List of {@link BlogComment}
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * When it was last created
	 */
	private Date createdAt;
	/**
	 * When it was last modified
	 */
	private Date lastModifiedAt;
	/**
	 * The title
	 */
	private String title;
	/**
	 * The text
	 */
	private String text;
	/**
	 * The {@link BlogUser}
	 */
	private BlogUser creator;
	
	/**
	 * @return the creator
	 */
	@ManyToOne
    @JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}


	/**
	 * @param creator the creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}


	/**
	 * @return the id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to be set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns a list of {@link BlogComment}, {@link BlogEntry} can have
	 * many {@link BlogComment}
	 * @return
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	/**
	 * Sets the comments
	 * @param comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Gets the date when it was created.
	 * 
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the date when it was created
	 * 
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the date when it was modified
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	/**
	 * Sets the date when it was last modified
	 * 
	 * @param lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Gets the title
	 * @return
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text
	 * @return
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}