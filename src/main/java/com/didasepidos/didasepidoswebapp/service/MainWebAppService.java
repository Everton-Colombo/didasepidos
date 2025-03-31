package com.didasepidos.didasepidoswebapp.service;

import java.util.List;

import com.didasepidos.didasepidoswebapp.dao.OrderByField;
import com.didasepidos.didasepidoswebapp.entity.Institution;
import com.didasepidos.didasepidoswebapp.entity.Review;
import com.didasepidos.didasepidoswebapp.entity.ReviewComponent;
import com.didasepidos.didasepidoswebapp.entity.ReviewVote;
import com.didasepidos.didasepidoswebapp.entity.Subject;
import com.didasepidos.didasepidoswebapp.type.AverageRatingRow;
import com.didasepidos.didasepidoswebapp.type.InstitutionRatingSummary;
import com.didasepidos.didasepidoswebapp.type.PageResult;

public interface MainWebAppService {
	
	public void loadInstitutionReviews(Institution... institutions);
	public void loadInstitutionReviews(Iterable<Institution> institutions);
	
	public void loadReviewVotes(Review... reviews);
	public void loadReviewVotes(Iterable<Review> reviews);
	
	public PageResult<Institution> queryInstitutionsLikeName(String name, int page, OrderByField orderField);
	public Institution getInstitution(int id);
	public List<Institution> queryInstitutionsByLocationLikeCity(String city);
	public List<Institution> queryInstitutionsByLocationLikeDivisionUnit(String divisionUnitName);
	
	public PageResult<Review> getInstitutionReviews(int institutionId, int page, OrderByField orderField);
	public List<ReviewComponent> getInstitutionReviewsOfSubject(int institutionId, int subjectId);
	public Review getReview(int id);
	public PageResult<Review> getReviewsFromAuthor(String authorId, int page, OrderByField orderField);
	public List<Review> getTopReviews(int institutionId, int days, int count);
	public List<Integer> getUserVotesForReviews(List<Review> reviews, String authorId);
	public void saveReview(Review review);
	public void deleteReview(Review review);
	public int getReviewCountWithinLastXDaysByAuthor(String authorId, int x);
	public int getReviewCountWithinLastXDaysByAuthorByInstituion(String authorId, int institutionId, int x);
	public void deleteReviewVote(ReviewVote vote);
	
	public List<AverageRatingRow> getInstitutionAverageRatingRows(int institutionId);
	public float getInstitutionAverageReview(int institutionId);
	
	public InstitutionRatingSummary getInstitutionRatingSummary(Institution institution);
	public List<InstitutionRatingSummary> getTopXRatingSummary(int x);
	
	public long getTotalInstitutionCount();
	public long getTotalReviewCount();
	public long getInstitutionReviewCount(Institution institution);		// Convenience, will deal with loading lazily fetched fields
	
	public int getAverageDailyReviewCount(int institutionId);
	
	public Subject getSubject(int id);
}
