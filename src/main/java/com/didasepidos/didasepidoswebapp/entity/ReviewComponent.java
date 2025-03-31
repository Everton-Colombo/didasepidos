package com.didasepidos.didasepidoswebapp.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="review_components")
@IdClass(ReviewComponentId.class)
public class ReviewComponent {

//	@ManyToOne(cascade= {
//			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
//	}) // All but delete
//	@JoinColumn(name="review_id")
	@Id
	private Review parentReview;	// Bi-directional link
	
//	@ManyToOne(cascade= {
//			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
//	}) // All but delete
//	@JoinColumn(name="subject_id")
	@Id
	private Subject subject;
	
	@Column(name="rating")
	private int rating;
	
	public ReviewComponent() {}

	public ReviewComponent(Review parentReview, Subject subject, int rating) {
		super();
		this.parentReview = parentReview;
		this.subject = subject;
		this.rating = rating;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Review getParentReview() {
		return parentReview;
	}

	public void setParentReview(Review parentReview) {
		this.parentReview = parentReview;
	}


	@Override
	public String toString() {
		return "ReviewComponent [subject=" + subject + ", rating=" + rating + "]";
	}
	
	
	
}

class ReviewComponentId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade= {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	}) // All but delete
	@JoinColumn(name="review_id")
	private Review parentReview;	// Bi-directional link
	
	@ManyToOne(cascade= {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	}) // All but delete
	@JoinColumn(name="subject_id")
	private Subject subject;
	
	public ReviewComponentId () {}

	public ReviewComponentId(Review parentReview, Subject subject) {
		super();
		this.parentReview = parentReview;
		this.subject = subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentReview == null) ? 0 : parentReview.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		ReviewComponentId other = (ReviewComponentId) obj;
		if (parentReview == null) {
			if (other.parentReview != null)
				return false;
		} else if (!parentReview.equals(other.parentReview))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	
}
