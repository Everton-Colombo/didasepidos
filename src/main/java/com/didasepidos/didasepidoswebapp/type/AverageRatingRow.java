package com.didasepidos.didasepidoswebapp.type;

public class AverageRatingRow {
	private String subject;
	private float recent;
	private float allTime;
	
	public AverageRatingRow(String subject, float recent, float allTime) {
		super();
		this.subject = subject;
		this.recent = recent;
		this.allTime = allTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public float getRecent() {
		return recent;
	}

	public void setRecent(float recent) {
		this.recent = recent;
	}

	public float getAllTime() {
		return allTime;
	}

	public void setAllTime(float allTime) {
		this.allTime = allTime;
	}

	@Override
	public String toString() {
		return "AverageRatingRow [subject=" + subject + ", recent=" + recent + ", allTime=" + allTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(allTime);
		result = prime * result + Float.floatToIntBits(recent);
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
		AverageRatingRow other = (AverageRatingRow) obj;
		if (Float.floatToIntBits(allTime) != Float.floatToIntBits(other.allTime))
			return false;
		if (Float.floatToIntBits(recent) != Float.floatToIntBits(other.recent))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
}
