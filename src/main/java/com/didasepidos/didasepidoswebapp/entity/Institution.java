package com.didasepidos.didasepidoswebapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="institutions")
public class Institution {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="is_private")
	private boolean inPrivateSector;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="location_id")
	private Location location;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		name="institutions_subjects",
		joinColumns=@JoinColumn(name="institution_id"),
		inverseJoinColumns=@JoinColumn(name="subject_id")
	)
	private List<Subject> subjectsTaught;
	
	@OneToMany(mappedBy="targetInstitution", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Review> reviews;
	
	public Institution() {}

	public Institution(String name, boolean inPrivateSector, Location location, List<Review> reviews) {
		super();
		this.name = name;
		this.inPrivateSector = inPrivateSector;
		this.location = location;
		this.reviews = reviews;
	}
	
	public void addReview(Review review) {
		if(this.reviews == null) { this.reviews = new ArrayList<>(); }
		this.reviews.add(review);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInPrivateSector() {
		return inPrivateSector;
	}

	public void setInPrivateSector(boolean inPrivateSector) {
		this.inPrivateSector = inPrivateSector;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Subject> getSubjectsTaught() {
		return subjectsTaught;
	}

	public void setSubjectsTaught(List<Subject> subjectsTaught) {
		this.subjectsTaught = subjectsTaught;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
//		return "Institution [id=" + id + ", name=" + name + ", isPrivate=" + isPrivate + ", location=" + location
//				+ ", reviews=" + reviews + "]";
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (inPrivateSector ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((reviews == null) ? 0 : reviews.hashCode());
		result = prime * result + ((subjectsTaught == null) ? 0 : subjectsTaught.hashCode());
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
		Institution other = (Institution) obj;
		if (id != other.id)
			return false;
		if (inPrivateSector != other.inPrivateSector)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reviews == null) {
			if (other.reviews != null)
				return false;
		} else if (!reviews.equals(other.reviews))
			return false;
		if (subjectsTaught == null) {
			if (other.subjectsTaught != null)
				return false;
		} else if (!subjectsTaught.equals(other.subjectsTaught))
			return false;
		return true;
	}
	
}
