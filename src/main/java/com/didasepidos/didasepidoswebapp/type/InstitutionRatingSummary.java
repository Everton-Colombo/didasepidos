package com.didasepidos.didasepidoswebapp.type;

import com.didasepidos.didasepidoswebapp.entity.Institution;

public class InstitutionRatingSummary {
	
	private Institution institution;
	private float avgRating;
	private long ratingCount;
	private float score;
	
	public InstitutionRatingSummary(Institution institution, float avgRating, long ratingCount, float score) {
		super();
		this.institution = institution;
		this.avgRating = avgRating;
		this.ratingCount = ratingCount;
		this.score = score;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public float getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(long ratingCoung) {
		this.ratingCount = ratingCoung;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(avgRating);
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		result = prime * result + (int) (ratingCount ^ (ratingCount >>> 32));
		result = prime * result + Float.floatToIntBits(score);
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
		InstitutionRatingSummary other = (InstitutionRatingSummary) obj;
		if (Float.floatToIntBits(avgRating) != Float.floatToIntBits(other.avgRating))
			return false;
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (ratingCount != other.ratingCount)
			return false;
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InstitutionRatingSummary [institution=" + institution + ", avgRating=" + avgRating + ", ratingCount="
				+ ratingCount + ", score=" + score + "]";
	}
	
	
	
}
