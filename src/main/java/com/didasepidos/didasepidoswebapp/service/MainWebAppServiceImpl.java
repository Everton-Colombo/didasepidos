package com.didasepidos.didasepidoswebapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.didasepidos.didasepidoswebapp.dao.InstitutionDAO;
import com.didasepidos.didasepidoswebapp.dao.LocationDAO;
import com.didasepidos.didasepidoswebapp.dao.OrderByField;
import com.didasepidos.didasepidoswebapp.dao.ReviewDAO;
import com.didasepidos.didasepidoswebapp.dao.SubjectDAO;
import com.didasepidos.didasepidoswebapp.entity.Institution;
import com.didasepidos.didasepidoswebapp.entity.Location;
import com.didasepidos.didasepidoswebapp.entity.Review;
import com.didasepidos.didasepidoswebapp.entity.ReviewComponent;
import com.didasepidos.didasepidoswebapp.entity.ReviewVote;
import com.didasepidos.didasepidoswebapp.entity.Subject;
import com.didasepidos.didasepidoswebapp.type.AverageRatingRow;
import com.didasepidos.didasepidoswebapp.type.InstitutionRatingSummary;
import com.didasepidos.didasepidoswebapp.type.PageResult;

@Service
@PropertySource("classpath:application.properties")
public class MainWebAppServiceImpl implements MainWebAppService {

	@Autowired
	private InstitutionDAO institutionDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private SubjectDAO subjectDAO;
		
	@Override
	@Transactional
	public PageResult<Institution> queryInstitutionsLikeName(String name, int page, OrderByField orderField) {
		return this.institutionDAO.queryInstitutionsByName(name, page, orderField);
	}

	@Override
	@Transactional
	public Institution getInstitution(int id) {
		return this.institutionDAO.getInstitution(id);
	}

	@Override
	@Transactional
	public List<Institution> queryInstitutionsByLocationLikeCity(String city) {
		// Returns an empty ArrayList if no match is found
		
		List<Location> locations = this.locationDAO.queryLocationsByCity(city);
		List<Institution> result = new ArrayList<>();
		for(Location location : locations) {
			result.add(location.getInstitution());
		}
		
		return result;
	}

	@Override
	@Transactional
	public List<Institution> queryInstitutionsByLocationLikeDivisionUnit(String divisionUnitName) {
		// Returns an empty ArrayList if no match is found
		
		List<Location> locations = this.locationDAO.queryLocationsByDivisionUnit(divisionUnitName);
		List<Institution> result = new ArrayList<>();
		for(Location location : locations) {
			result.add(location.getInstitution());
		}
		
		return result;
	}

	@Override
	@Transactional
	public PageResult<Review> getInstitutionReviews(int institutionId, int page, OrderByField orderField) {
		return this.reviewDAO.queryReviewsByTargetInstitution(institutionId, page, orderField);
	}	

	@Override
	@Transactional
	public List<ReviewComponent> getInstitutionReviewsOfSubject(int institutionId, int subjectId) {
		// Returns an empty ArrayList if no match is found
		
		List<ReviewComponent> result = new ArrayList<>();
		for(Review review : this.reviewDAO.queryReviewsByTargetInstitution(institutionId) ) {
			for(ReviewComponent component : review.getReviewComponents()) {
				if(component.getSubject().getId() == subjectId) {
					result.add(component);
				}
			}
		}
		
		return result;
	}

	@Override
	@Transactional
	public Review getReview(int id) {
		return this.reviewDAO.getReview(id);
	}

	@Override
	@Transactional
	public PageResult<Review> getReviewsFromAuthor(String authorId, int page, OrderByField orderField) {
		return this.reviewDAO.queryReviewsByAuthor(authorId, page, orderField);
	}

	@Override
	@Transactional
	public void saveReview(Review review) {
		this.reviewDAO.saveReview(review);
	}

	@Override
	@Transactional
	public void loadInstitutionReviews(Institution... institutions) {
		this.institutionDAO.loadReviews(Arrays.asList(institutions));
	}

	@Override
	@Transactional
	public void loadInstitutionReviews(Iterable<Institution> institutions) {
		this.institutionDAO.loadReviews(institutions);
	}

	@Override
	@Transactional
	public long getTotalInstitutionCount() {
		return this.institutionDAO.getInstitutionCount();
	}

	@Override
	@Transactional
	public long getTotalReviewCount() {
		return this.reviewDAO.getReviewCount();
	}

	@Override
	@Transactional
	public long getInstitutionReviewCount(Institution institution) {
		try {
			return institution.getReviews().size();
		} catch (LazyInitializationException e) {
			// Reviews not yet loaded, therefore do so.
			
			this.loadInstitutionReviews(institution);
			return institution.getReviews().size();
		}
	}

	@Override
	@Transactional
	public List<AverageRatingRow> getInstitutionAverageRatingRows(int institutionId) {
		List<AverageRatingRow> result = new LinkedList<>();
		
		Institution i = this.institutionDAO.getInstitution(institutionId);
		for(Subject s : i.getSubjectsTaught()) {
			result.add(new AverageRatingRow(s.getName(),
					this.reviewDAO.getAverageRatingBySubjectTaught(institutionId, s.getId(), new Date(System.currentTimeMillis() - 30 * DateUtils.MILLIS_PER_DAY)),
					this.reviewDAO.getAverageRatingBySubjectTaught(institutionId, s.getId())));
		}
		
		return result;
	}

	@Override
	@Transactional
	public float getInstitutionAverageReview(int institutionId) {
		return this.reviewDAO.getAverageRatingByTargetInstitution(institutionId);
	}
	
	@Override
	@Transactional
	public InstitutionRatingSummary getInstitutionRatingSummary(Institution institution) {
		float avgRating = this.reviewDAO.getAverageRatingByTargetInstitution(institution.getId());
		long ratingCount = this.reviewDAO.getReviewCountByInstitution(institution.getId());
		
		int minReviews = (int) (this.reviewDAO.getReviewCountFromLastXDays(30) * 2/100);
		if(minReviews < 5) { minReviews = 5; }
		float score = 0;
		if(ratingCount > minReviews) {
			score = (float) (avgRating + ((float) ratingCount / Math.pow(10, ((int) (ratingCount/10)) + 2 )));
		}
		
		System.out.println("----- SCORE LOG -----");
		System.out.println("minReviews:\t" + minReviews);
		System.out.println("RevCount:\t" + ratingCount);
		System.out.println("score:\t\t" + score);
		System.out.println("Additive:\t" + ((float) ratingCount / Math.pow(10, ((int) (ratingCount/10)) + 3 )));
		System.out.println("---------------------");
		
		return new InstitutionRatingSummary(institution, avgRating, ratingCount, score);
	}

	@Override
	@Transactional
	public List<InstitutionRatingSummary> getTopXRatingSummary(int x) {
		List<InstitutionRatingSummary> result = new ArrayList<>();
		List<Institution> institutions = this.institutionDAO.getInstitutions();
		for(Institution inst : institutions) {
			InstitutionRatingSummary instSummary = this.getInstitutionRatingSummary(inst);
			for(int i = 0; i < x; i++) {
				InstitutionRatingSummary compSummary;
				try {
					compSummary = result.get(i);
				} catch (IndexOutOfBoundsException e) {
					result.add(instSummary);
					break;
				}
				if(instSummary.getScore() > compSummary.getScore()) {
					result.add(i, instSummary);
					break;
				}
			}
		}
		
		System.out.println(result);
		return result;
	}

	@Override
	@Transactional
	public Subject getSubject(int id) {
		return this.subjectDAO.getSubject(id);
	}

	@Override
	@Transactional
	public void loadReviewVotes(Review... reviews) {
		this.loadReviewVotes(Arrays.asList(reviews));
	}

	@Override
	@Transactional
	public void loadReviewVotes(Iterable<Review> reviews) {
		for(Review r : reviews) {
			this.reviewDAO.loadReviewVotes(r);
		}
	}

	@Override
	@Transactional
	public int getReviewCountWithinLastXDaysByAuthor(String authorId, int x) {
		return this.reviewDAO.getReviewCountFromAuthorLastXDays(authorId, x);
	}

	@Override
	@Transactional
	public int getReviewCountWithinLastXDaysByAuthorByInstituion(String authorId, int institutionId, int x) {
		return this.reviewDAO.getReviewCountFromAuthorByInstitutionLastXDays(authorId, institutionId, x);
	}

	@Override
	@Transactional
	public void deleteReviewVote(ReviewVote vote) {
		this.reviewDAO.deleteReviewVote(vote);
	}

	@Override
	@Transactional
	public List<Review> getTopReviews(int institutionId, int days, int count) {
		List<Review> result =  this.reviewDAO.getTopReviews(institutionId, days, count);
		this.loadReviewVotes(result);
		
		return result;
	}

	@Override
	public List<Integer> getUserVotesForReviews(List<Review> reviews, String authorId) {
		List<Integer> votes = new LinkedList<>();
		boolean empty;
		for(Review review : reviews) {
			empty = true;
			for(ReviewVote vote : review.getVotes()) {
				if(vote.getAuthorId().equals(authorId)) {
					votes.add(reviews.indexOf(review), vote.isLike() ? 1 : 0);
					empty = false;
					break;
				}
			}
			if(empty) {
				votes.add(reviews.indexOf(review), -1);
			}
		}
		
		return votes;
	}

	@Override
	@Transactional
	public int getAverageDailyReviewCount(int institutionId) {
		return this.reviewDAO.getAverageDailyReviewCount(institutionId);
	}

	@Override
	@Transactional
	public void deleteReview(Review review) {
		this.reviewDAO.deleteReview(review);
	}
}

