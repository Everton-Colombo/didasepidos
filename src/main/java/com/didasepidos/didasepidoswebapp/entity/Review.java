package com.didasepidos.didasepidoswebapp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="reviews")
public class Review {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade= {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	}) // All but delete
	@JoinColumn(name="institution_id")
	private Institution targetInstitution;
	
	@Column(name="author_id")
	private String authorId;
	
	@Column(name="datetime")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date datetime;
	
	@Column(name="note")
	private String comment;
	
	@OneToMany(mappedBy="parentReview", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<ReviewComponent> reviewComponents;
	
	@OneToMany(mappedBy="parentReview", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ReviewVote> votes;
	
	
	public Review() {}

	public Review(Institution targetInstitution, String authorId, Date datetime, String comment,
			List<ReviewComponent> reviewComponents) {
		super();
		this.targetInstitution = targetInstitution;
		this.authorId = authorId;
		this.datetime = datetime;
		this.comment = comment;
		this.reviewComponents = reviewComponents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Institution getTargetInstitution() {
		return targetInstitution;
	}

	public void setTargetInstitution(Institution targetInstitution) {
		this.targetInstitution = targetInstitution;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ReviewComponent> getReviewComponents() {
		return reviewComponents;
	}

	public void setReviewComponents(List<ReviewComponent> reviewComponents) {
		this.reviewComponents = reviewComponents;
	}

	public List<ReviewVote> getVotes() {
		return votes;
	}

	public void setVotes(List<ReviewVote> votes) {
		this.votes = votes;
	}
	
	public int getLikes() {
		int result = 0;
		for(ReviewVote rv : this.votes) {
			if(rv.isLike()) { result += 1; }
		}
		
		return result;
	}
	
	public int getDislikes() {
		int result = 0;
		for(ReviewVote rv : this.votes) {
			if(!rv.isLike()) { result += 1; }
		}
		
		return result;
	}

	public float getAverageRating() {
		float r = 0;
		for(ReviewComponent rc : this.reviewComponents) {
			r += rc.getRating();
		}
		
		return r / this.reviewComponents.size();
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", targetInstitution=" + targetInstitution + ", authorId=" + authorId
				+ ", datetime=" + datetime + ", comment=" + comment + ", reviewComponents=" + reviewComponents + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
		result = prime * result + id;
		result = prime * result + ((reviewComponents == null) ? 0 : reviewComponents.hashCode());
		result = prime * result + ((targetInstitution == null) ? 0 : targetInstitution.hashCode());
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
		Review other = (Review) obj;
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (datetime == null) {
			if (other.datetime != null)
				return false;
		} else if (!datetime.equals(other.datetime))
			return false;
		if (id != other.id)
			return false;
		if (reviewComponents == null) {
			if (other.reviewComponents != null)
				return false;
		} else if (!reviewComponents.equals(other.reviewComponents))
			return false;
		if (targetInstitution == null) {
			if (other.targetInstitution != null)
				return false;
		} else if (!targetInstitution.equals(other.targetInstitution))
			return false;
		return true;
	}
	
}
