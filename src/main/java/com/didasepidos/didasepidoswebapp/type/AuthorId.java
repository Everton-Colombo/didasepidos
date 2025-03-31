package com.didasepidos.didasepidoswebapp.type;

import java.io.Serializable;
import java.util.Date;

public class AuthorId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String authorString;
	private String origin;
	private Date timeOfGeneration;
	
	public AuthorId() {}

	public AuthorId(String authorString, String origin, Date timeOfGeneration) {
		super();
		this.authorString = authorString;
		this.origin = origin;
		this.timeOfGeneration = timeOfGeneration;
	}

	public String getAuthorString() {
		return authorString;
	}

	public void setAuthorString(String authorString) {
		this.authorString = authorString;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Date getTimeOfGeneration() {
		return timeOfGeneration;
	}

	public void setTimeOfGeneration(Date timeOfGeneration) {
		this.timeOfGeneration = timeOfGeneration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorString == null) ? 0 : authorString.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((timeOfGeneration == null) ? 0 : timeOfGeneration.hashCode());
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
		AuthorId other = (AuthorId) obj;
		if (authorString == null) {
			if (other.authorString != null)
				return false;
		} else if (!authorString.equals(other.authorString))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (timeOfGeneration == null) {
			if (other.timeOfGeneration != null)
				return false;
		} else if (!timeOfGeneration.equals(other.timeOfGeneration))
			return false;
		return true;
	}
	
}
