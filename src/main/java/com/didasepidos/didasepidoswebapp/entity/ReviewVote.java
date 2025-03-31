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
@Table(name="review_votes")
@IdClass(ReviewVoteId.class)
public class ReviewVote {
	
	@Id
	private String authorId;
	
	@Id
	private Review parentReview;
	
	@Column(name="isLike")
	private boolean isLike;
	
	public ReviewVote() {}

	public ReviewVote(String authorId, Review parentReview, boolean isLike) {
		super();
		this.authorId = authorId;
		this.parentReview = parentReview;
		this.isLike = isLike;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Review getParentReview() {
		return parentReview;
	}

	public void setParentReview(Review parentReview) {
		this.parentReview = parentReview;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + (isLike ? 1231 : 1237);
		result = prime * result + ((parentReview == null) ? 0 : parentReview.hashCode());
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
		ReviewVote other = (ReviewVote) obj;
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (isLike != other.isLike)
			return false;
		if (parentReview == null) {
			if (other.parentReview != null)
				return false;
		} else if (!parentReview.equals(other.parentReview))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReviewVote [authorId=" + authorId + ", parentReview=" + parentReview + ", isLike=" + isLike + "]";
	};
	
}

class ReviewVoteId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="author_id")
	private String authorId;
	
	@ManyToOne(cascade= {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	})
	@JoinColumn(name="review_id")
	private Review parentReview;
	
	public ReviewVoteId() {}

	public ReviewVoteId(String authorId, Review parentReview) {
		super();
		this.authorId = authorId;
		this.parentReview = parentReview;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + ((parentReview == null) ? 0 : parentReview.hashCode());
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
		ReviewVoteId other = (ReviewVoteId) obj;
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (parentReview == null) {
			if (other.parentReview != null)
				return false;
		} else if (!parentReview.equals(other.parentReview))
			return false;
		return true;
	}
	
}