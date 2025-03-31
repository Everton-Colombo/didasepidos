package com.didasepidos.didasepidoswebapp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.didasepidos.didasepidoswebapp.entity.Review;
import com.didasepidos.didasepidoswebapp.entity.ReviewVote;
import com.didasepidos.didasepidoswebapp.type.PageResult;

@Repository
public interface ReviewDAO {
	public List<Review> getReviews();
	public Review getReview(int id);
	public void saveReview(Review location);
	public void deleteReview(Review review);
	
	public List<Review> queryReviewsByTargetInstitution(int institutionId);
	public PageResult<Review> queryReviewsByTargetInstitution(int institutionId, int page, OrderByField orderField);
	public List<Review> getTopReviews(int institutionId, int days, int count);
	public List<Review> queryReviewsByAuthor(String authorId);
	public PageResult<Review> queryReviewsByAuthor(String authorId, int page, OrderByField orderByField);
	public List<Review> queryReviewsByDateEquals(Date date);
	public List<Review> queryReviewsBeforeDate(Date date);
	public List<Review> queryReviewsAfterDate(Date date);
	public List<Review> queryReviewsBetweenDates(Date start, Date end);
	public List<Review> queryReviewsByLikeDislikeRatioGreaterOrEqual(float ratio);
	public List<Review> queryReviewsByLikeDislikeRatioLowerOrEqual(float ratio);
	public List<Review> queryReviewsByFeedbackCountGreaterOrEqual(int count);
	public List<Review> queryReviewsByFeedbackCountLowerOrEqual(int count);
	public List<Review> queryReviewsByAbsoluteFeedbackGreaterOrEqual(int absoluteFeedback);
	public List<Review> queryReviewsByAbsoluteFeedbackLowerOrEqual(int absoluteFeedback);
	
	public float getAverageRatingByTargetInstitution(int institutionId);
	public float getAverageRatingByTargetInstitution(int institutionId, Date fromDate);
	public float getAverageRatingBySubjectTaught(int institutionId, int subjectId);
	public float getAverageRatingBySubjectTaught(int institutionId, int subjectId, Date fromDate);
	
	public int getReviewCountFromAuthorLastXDays(String authorId, int x);
	public int getReviewCountFromAuthorByInstitutionLastXDays(String authorId, int institutionId, int x);
	public int getAverageDailyReviewCount(int institutionId);
	
	
	public long getReviewCount();
	public long getReviewCountByInstitution(int institutionId);
	public long getReviewCountFromLastXDays(int x);
	public Review getFirstReviewFromLastXDaysByInstitution(int x, int institudionId);
	
	public void loadReviewVotes(Review review);
	public void deleteReviewVote(ReviewVote vote);
}
