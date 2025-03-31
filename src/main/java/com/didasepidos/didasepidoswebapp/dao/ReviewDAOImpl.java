package com.didasepidos.didasepidoswebapp.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.didasepidos.didasepidoswebapp.entity.Review;
import com.didasepidos.didasepidoswebapp.entity.ReviewVote;
import com.didasepidos.didasepidoswebapp.type.PageResult;
import com.didasepidos.didasepidoswebapp.type.exception.PageNotFoundException;
import com.didasepidos.didasepidoswebapp.util.DidasepidosUM;


@Repository
@PropertySource("classpath:application.properties")
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${webapp.pagination.pageSize.reviews}")
	private Integer pageSize;
	
	@Override
	public List<Review> getReviews() {
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Review ORDER BY date", Review.class)
					.getResultList();
	}

	@Override
	public Review getReview(int id) {
		return this.sessionFactory.getCurrentSession().get(Review.class, id);
	}

	@Override
	public void saveReview(Review review) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(review);
	}

	@Override
	public void deleteReview(Review review) {
		this.sessionFactory.getCurrentSession().delete(review);
	}

	@Override
	public List<Review> queryReviewsByTargetInstitution(int institutionId) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE targetInstitution.id=:institutionId", Review.class)
				.setParameter("institutionId", institutionId)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsByAuthor(String authorId) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE authorId=:authorId", Review.class)
				.setParameter("authorId", authorId)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsByDateEquals(Date date) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE datetime=:date", Review.class)
				.setParameter("date", date, TemporalType.TIMESTAMP)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsBeforeDate(Date date) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE datetime < :date", Review.class)
				.setParameter("date", date, TemporalType.TIMESTAMP)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsAfterDate(Date date) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE datetime > :date", Review.class)
				.setParameter("date", date, TemporalType.TIMESTAMP)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsBetweenDates(Date start, Date end) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE datetime BETWEEN :dateStart AND :dateEnd", Review.class)
				.setParameter("dateStart", start, TemporalType.TIMESTAMP)
				.setParameter("dateEnd", end, TemporalType.TIMESTAMP)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsByLikeDislikeRatioGreaterOrEqual(float ratio) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE (likes / dislikes) >= :ratio", Review.class)
				.setParameter("ratio", ratio)
				.getResultList();
	}
	
	@Override
	public List<Review> queryReviewsByLikeDislikeRatioLowerOrEqual(float ratio) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE (likes / dislikes) <= :ratio", Review.class)
				.setParameter("ratio", ratio)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsByFeedbackCountGreaterOrEqual(int count) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE (likes + dislikes) >= :count", Review.class)
				.setParameter("count", count)
				.getResultList();
	}
	
	@Override
	public List<Review> queryReviewsByFeedbackCountLowerOrEqual(int count) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE (likes + dislikes) <= :count", Review.class)
				.setParameter("count", count)
				.getResultList();
	}

	@Override
	public List<Review> queryReviewsByAbsoluteFeedbackGreaterOrEqual(int absoluteFeedback) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE (likes - dislikes) >= :absFb", Review.class)
				.setParameter("absFb", absoluteFeedback)
				.getResultList();
	}
	
	@Override
	public List<Review> queryReviewsByAbsoluteFeedbackLowerOrEqual(int absoluteFeedback) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE (likes - dislikes) <= :absFb", Review.class)
				.setParameter("absFb", absoluteFeedback)
				.getResultList();
	}

	@Override
	public long getReviewCount() {
		return (long) this.sessionFactory.getCurrentSession()
					.createQuery("SELECT COUNT(*) FROM Review")
					.uniqueResult();
	}

	@Override
	public PageResult<Review> queryReviewsByTargetInstitution(int institutionId, int page, OrderByField orderField) {
		PageResult<Review> result = new PageResult<>();
		
		long totalCount = this.getReviewCountByInstitution(institutionId);
		int lastPageNumber = DidasepidosUM.getPageCount(totalCount, this.pageSize);
		if (page < 1 || page > lastPageNumber) {
			throw new PageNotFoundException(String.format("Page resquested (%s) does not exist. Current Bounds: 1 <= page <= %s", page, lastPageNumber));
		}
		
		result.setCurrentPage(page);
		result.setTotalResultCount((int) totalCount);
		result.setPageCount(lastPageNumber);
		result.setPageResultList(this.sessionFactory.getCurrentSession()
										.createQuery("FROM Review WHERE targetInstitution.id=:institutionId" + orderField.sqlString, Review.class)
										.setParameter("institutionId", institutionId)
										.setFirstResult((page - 1) * this.pageSize)
										.setMaxResults(this.pageSize)
										.list());
		
		return result;
	}

	@Override
	public float getAverageRatingByTargetInstitution(int institutionId) {
		Double result = this.sessionFactory.getCurrentSession()
							.createQuery("SELECT AVG(rc.rating) FROM Review r JOIN ReviewComponent rc ON rc.parentReview.id = r.id WHERE r.targetInstitution.id = :instId",
										Double.class)
							.setParameter("instId", (int) institutionId)
							.uniqueResult();
		
		if (result != null) { return result.floatValue(); }
		else { return 0; }
	}

	@Override
	public float getAverageRatingByTargetInstitution(int institutionId, Date fromDate) {
		Double result = this.sessionFactory.getCurrentSession()
							.createQuery("SELECT AVG(rc.rating) FROM Review r JOIN ReviewComponent rc ON rc.parentReview.id = r.id WHERE r.targetInstitution.id = :instId "
									+ "AND r.datetime AFTER :fromDate",
									Double.class)
							.setParameter("instId", institutionId)
							.setParameter("fromDate", fromDate)
							.uniqueResult();
		
		if (result != null) { return result.floatValue(); }
		else { return 0; }
	}

	@Override
	public float getAverageRatingBySubjectTaught(int institutionId, int subjectId) {
		Double result = this.sessionFactory.getCurrentSession()
							.createQuery("SELECT AVG(rc.rating) FROM Review r "
									+ "JOIN ReviewComponent rc ON rc.parentReview.id = r.id JOIN Subject s ON s.id = rc.subject.id "
									+ "WHERE r.targetInstitution.id = :instId AND s.id = :subjectId",
									Double.class)
							.setParameter("instId", institutionId)
							.setParameter("subjectId", subjectId)
							.uniqueResult();
		
		if (result != null) { return result.floatValue(); }
		else { return 0; }
	}

	@Override
	public float getAverageRatingBySubjectTaught(int institutionId, int subjectId, Date fromDate) {
		Double result = this.sessionFactory.getCurrentSession()
						.createQuery("SELECT AVG(rc.rating) FROM Review r "
								+ "JOIN ReviewComponent rc ON rc.parentReview.id = r.id JOIN Subject s ON s.id = rc.subject.id "
								+ "WHERE r.targetInstitution.id = :instId AND s.id = :subject_id AND r.datetime > :fromDate",
								Double.class)
						.setParameter("instId", (int) institutionId)
						.setParameter("subject_id", (int) subjectId)
						.setParameter("fromDate", fromDate)
						.uniqueResult();
		
		if (result != null) { return result.floatValue(); }
		else { return 0; }
	}

	@Override
	public long getReviewCountByInstitution(int institutionId) {
		return this.sessionFactory.getCurrentSession()
					.createQuery("SELECT COUNT(*) FROM Review WHERE targetInstitution.id = :instId",
							Long.class)
					.setParameter("instId", institutionId)
					.uniqueResult();
	}

	@Override
	public void loadReviewVotes(Review review) {
		review.setVotes(this.sessionFactory.getCurrentSession()
							.createQuery("SELECT rv FROM ReviewVote rv WHERE rv.parentReview.id = :revId", ReviewVote.class)
							.setParameter("revId", review.getId())
							.getResultList());
	}

	@Override
	public int getReviewCountFromAuthorLastXDays(String authorId, int x) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM Review WHERE author_id = :aid AND datetime > :dt", Long.class)
				.setParameter("aid", authorId)
				.setParameter("dt", DateUtils.addDays(new Date(System.currentTimeMillis()), -x))
				.getSingleResult().intValue();
	}

	@Override
	public int getReviewCountFromAuthorByInstitutionLastXDays(String authorId, int institutionId, int x) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM Review WHERE authorId = :aid AND targetInstitution.id = :iid AND datetime > :dt", Long.class)
				.setParameter("aid", authorId)
				.setParameter("iid", institutionId)
				.setParameter("dt", DateUtils.addDays(new Date(System.currentTimeMillis()), -x))
				.getSingleResult().intValue();
	}

	@Override
	public void deleteReviewVote(ReviewVote vote) {
		this.sessionFactory.getCurrentSession()
			.createQuery("DELETE FROM ReviewVote WHERE authorId = :aid AND parentReview.id = :prid")
			.setParameter("aid", vote.getAuthorId())
			.setParameter("prid", vote.getParentReview().getId())
			.executeUpdate();
	}

	@Override
	public List<Review> getTopReviews(int institutionId, int days, int count) {
		return this.sessionFactory.getCurrentSession()
					.createQuery("SELECT r FROM Review r "
							+ "LEFT JOIN ReviewVote rv ON rv.parentReview.id = r.id AND rv.isLike = 1 "
							+ "WHERE r.targetInstitution.id = :instId AND r.datetime >= :dtime "
							+ "GROUP BY r.id "
							+ "ORDER BY COUNT(rv) DESC", Review.class)
					.setParameter("instId", institutionId)
					.setParameter("dtime", DateUtils.addDays(new Date(System.currentTimeMillis()), -days))
					.setMaxResults(count)
					.getResultList();
	}

	@Override
	public int getAverageDailyReviewCount(int institutionId) {
		return this.sessionFactory.getCurrentSession()
					.createQuery("SELECT (COUNT(r) / 7) FROM Review r WHERE targetInstitution.id = :instId AND datetime > :svnDaysAgo", Double.class)
					.setParameter("instId", institutionId)
					.setParameter("svnDaysAgo", DateUtils.addDays(new Date(System.currentTimeMillis()), -7))
					.getSingleResult().intValue();
	}

	@Override
	public PageResult<Review> queryReviewsByAuthor(String authorId, int page, OrderByField orderByField) {
		PageResult<Review> result = new PageResult<>();
		
		long totalCount = this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM Review WHERE authorId = :aid", Long.class)
				.setParameter("aid", authorId)
				.getSingleResult();
		int lastPageNumber = DidasepidosUM.getPageCount(totalCount, this.pageSize);
		if (page < 1 || page > lastPageNumber) {
			throw new PageNotFoundException(String.format("Page resquested (%s) does not exist. Current Bounds: 1 <= page <= %s", page, lastPageNumber));
		}
		
		result.setCurrentPage(page);
		result.setTotalResultCount((int) totalCount);
		result.setPageCount(lastPageNumber);
		result.setPageResultList(this.sessionFactory.getCurrentSession()
										.createQuery("FROM Review WHERE authorId=:aid" + orderByField.sqlString, Review.class)
										.setParameter("aid", authorId)
										.setFirstResult((page - 1) * this.pageSize)
										.setMaxResults(this.pageSize)
										.list());
		
		return result;
	}

	@Override
	public long getReviewCountFromLastXDays(int x) {
		System.out.println("This week start: " + DateUtils.addDays(new Date(System.currentTimeMillis()), -x));
		return this.sessionFactory.getCurrentSession()
					.createQuery("SELECT COUNT(*) FROM Review WHERE datetime > :dtime", Long.class)
					.setParameter("dtime", DateUtils.addDays(new Date(System.currentTimeMillis()), -x))
					.getSingleResult();
	}

	@Override
	public Review getFirstReviewFromLastXDaysByInstitution(int x, int institudionId) {
		Query<Review> query = this.sessionFactory.getCurrentSession()
				.createQuery("FROM Review WHERE targetInstitution.id = :tid" 
						+ (x < 0 ? " " : " AND datetime > :dtime ") + "ORDER BY datetime ASC", Review.class);
		
		if(x >= 0) {
			query.setParameter("dtime", DateUtils.addDays(new Date(System.currentTimeMillis()), -x));
		}
		query.setParameter("tid", institudionId);
		query.setMaxResults(1);
		
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
