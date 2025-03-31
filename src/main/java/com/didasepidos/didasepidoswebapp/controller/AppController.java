package com.didasepidos.didasepidoswebapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.didasepidos.didasepidoswebapp.dao.OrderByField;
import com.didasepidos.didasepidoswebapp.entity.Institution;
import com.didasepidos.didasepidoswebapp.entity.Review;
import com.didasepidos.didasepidoswebapp.entity.ReviewComponent;
import com.didasepidos.didasepidoswebapp.entity.ReviewVote;
import com.didasepidos.didasepidoswebapp.service.MainAppSecurityService;
import com.didasepidos.didasepidoswebapp.service.MainWebAppService;
import com.didasepidos.didasepidoswebapp.type.AuthorId;
import com.didasepidos.didasepidoswebapp.type.PageResult;
import com.didasepidos.didasepidoswebapp.type.VoteUpdateRequest;
import com.didasepidos.didasepidoswebapp.type.exception.InstitutionRatingLimitExceededException;
import com.didasepidos.didasepidoswebapp.type.exception.InvalidReviewException;
import com.didasepidos.didasepidoswebapp.type.exception.NotAllowedException;
import com.didasepidos.didasepidoswebapp.type.exception.PageNotFoundException;


@Controller
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private MainWebAppService mainWebAppService;
	
	@Autowired
	private MainAppSecurityService mainAppSecurityService;
	
	
	@GetMapping("/list")
	public String showResultsList(@RequestParam String keywords, @RequestParam(defaultValue="1") Integer page, Model model) {
		try {
			PageResult<Institution> result = this.mainWebAppService.queryInstitutionsLikeName(keywords, page, OrderByField.NAME_ASC);
			if(result.getPageCount() > 0) {
				model.addAttribute("found", true);
			}
			model.addAttribute("institutions", result.getPageResultList());
			model.addAttribute("resultCount", result.getTotalResultCount());
			model.addAttribute("pageCount", result.getPageCount());
		} catch (PageNotFoundException e) {
			model.addAttribute("resultCount", "Nenhum");
		}
		
		return "app/searchList";
	}
	
	@GetMapping("/institution")
	public String showInstitution(@RequestParam int institutionId, Model model, HttpServletRequest request, HttpServletResponse response) {
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		
		Institution i = this.mainWebAppService.getInstitution(institutionId);
		this.mainWebAppService.loadInstitutionReviews(i);
		model.addAttribute("institution", i);
		model.addAttribute("avgRatingRows", this.mainWebAppService.getInstitutionAverageRatingRows(institutionId));
		model.addAttribute("avgRating", this.mainWebAppService.getInstitutionAverageReview(institutionId));
		
		List<Review> topReviews = this.mainWebAppService.getTopReviews(institutionId, 30, 10);
		model.addAttribute("topReviews", topReviews);
		model.addAttribute("votes", this.mainWebAppService.getUserVotesForReviews(topReviews, authorId.getAuthorString()));
		
		return "app/institutionDetail";
	}
	
	@GetMapping("/rate")
	public String showRatingForm(@RequestParam int institutionId, HttpServletRequest request, Model model, HttpServletResponse response) {
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		Institution i = this.mainWebAppService.getInstitution(institutionId);
		
		if(this.mainWebAppService
				.getReviewCountWithinLastXDaysByAuthorByInstituion(
						authorId.getAuthorString(), i.getId(), 7
						) >= 1) {
			throw new InstitutionRatingLimitExceededException("Não é possível avaliar uma instituição mais do que 1 vez por semana.");
		}
		
		model.addAttribute("institution", i);
		
		return "app/institutionRating";
	}
	
	@PostMapping("/sendRating")
	public String postRating(@RequestParam Map<String, String> requestParams, Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		Institution institution = this.mainWebAppService.getInstitution(Integer.parseInt(requestParams.get("target")));
		if(this.mainWebAppService
				.getReviewCountWithinLastXDaysByAuthorByInstituion(
						authorId.getAuthorString(), institution.getId(), 7
						) >= 1) {
			throw new InstitutionRatingLimitExceededException("Não é possível avaliar uma instituição mais do que 1 vez por semana.");
		}
		
		Review r = new Review();
		List<ReviewComponent> components = new ArrayList<>();
		
		for(Map.Entry<String, String> param : requestParams.entrySet()) {
			System.out.println(param);
			if(param.getKey().equals("review-comment")) {
				r.setComment(param.getValue());
			} else if (!param.getKey().equals("target")){
				components.add(new ReviewComponent(r, this.mainWebAppService.getSubject(Integer.parseInt(param.getKey().split("_")[1])),
						Integer.parseInt(param.getValue())));
			}
		}
		
		if(components.size() == 0) { throw new InvalidReviewException(); }
		
		r.setAuthorId(authorId.getAuthorString());
		r.setDatetime(new Date(System.currentTimeMillis()));
		r.setTargetInstitution(institution);
		r.setReviewComponents(components);
		
		this.mainWebAppService.saveReview(r);
		
		return "redirect:/app";
	}
	
	@GetMapping("/reviews")
	public String getReviews(@RequestParam int institutionId, @RequestParam int page, Model model, HttpServletRequest request, HttpServletResponse response) {
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		
		PageResult<Review> pageResult = this.mainWebAppService.getInstitutionReviews(institutionId, page, OrderByField.DATETIME_ASC);
		
		this.mainWebAppService.loadReviewVotes(pageResult.getPageResultList());
		
		model.addAttribute("reviews", pageResult.getPageResultList());
		model.addAttribute("reviewCount", pageResult.getTotalResultCount());
		model.addAttribute("pageCount", pageResult.getPageCount());
		
		model.addAttribute("votes", this.mainWebAppService.getUserVotesForReviews(pageResult.getPageResultList(), authorId.getAuthorString()));
		
		return "app/reviewList";
	}
	
	@PostMapping("/voteReview")
	public void updateReviewVote(@RequestBody VoteUpdateRequest voteUpdateRequest,
								   HttpServletRequest request, HttpServletResponse response) {
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		
		Review review = this.mainWebAppService.getReview(voteUpdateRequest.getReviewId());
		this.mainWebAppService.loadReviewVotes(review);
		
		try {
			if(review.getDislikes() >= 10 && (review.getDislikes() / (review.getLikes() == 0 ? 1 : review.getLikes())) > 5) {
				this.mainWebAppService.deleteReview(review);
				return;
			}
		} catch (Exception e) {
			System.out.println("Couldn't perferm check or operation: DISLIKE_RATIO_FILTER.\n"
					+ "Message: " + e.getMessage());
		}
		
		
		for(ReviewVote vote : review.getVotes()) {
			if(vote.getAuthorId().equals(authorId.getAuthorString())) {
				if(voteUpdateRequest.isLikeChecked()) {
					vote.setLike(true);
				} else if(voteUpdateRequest.isDislikeChecked()) {
					vote.setLike(false);
				} else if(!voteUpdateRequest.isLikeChecked() && !voteUpdateRequest.isDislikeChecked()) {
					System.out.println("removing");
					review.getVotes().remove(vote);
					this.mainWebAppService.deleteReviewVote(vote);
				}
				
				this.mainWebAppService.saveReview(review);
				return;
			}
		}
		if(voteUpdateRequest.isLikeChecked() || voteUpdateRequest.isDislikeChecked()) {
			review.getVotes().add(new ReviewVote(authorId.getAuthorString(), review, voteUpdateRequest.isLikeChecked()));
		}
		
		this.mainWebAppService.saveReview(review);
	}
	
	@GetMapping("/user-reviews")
	public String showUserReviews(@RequestParam(defaultValue="1") Integer page, Model model, HttpServletRequest request, HttpServletResponse response) {
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		
		PageResult<Review> pageResult = this.mainWebAppService.getReviewsFromAuthor(authorId.getAuthorString(), page, OrderByField.DATETIME_DESC);
		
		this.mainWebAppService.loadReviewVotes(pageResult.getPageResultList());
		
		model.addAttribute("reviews", pageResult.getPageResultList());
		model.addAttribute("reviewCount", pageResult.getTotalResultCount());
		model.addAttribute("pageCount", pageResult.getPageCount());
		
		model.addAttribute("votes", this.mainWebAppService.getUserVotesForReviews(pageResult.getPageResultList(), authorId.getAuthorString()));
		
		model.addAttribute("mrev", true);
		
		return "app/reviewList";
	}
	
	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam int reviewId, Model model, HttpServletRequest request, HttpServletResponse response) {
		AuthorId authorId = this.mainAppSecurityService.handleCredentialChecking(request, response);
		
		Review review = this.mainWebAppService.getReview(reviewId);
		if(review.getAuthorId().equals(authorId.getAuthorString())) {
			this.mainWebAppService.deleteReview(review);
		} else {
			throw new NotAllowedException("Essa avaliação não é sua!");
		}
		
		return "redirect:/app/user-reviews";
	}
	
	@ExceptionHandler(PageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handlePageNotFound(Model model) {
		model.addAttribute("oops", true);
		model.addAttribute("message", "Essa área ainda não possui conteúdo...");
		model.addAttribute("explanation", "Nada foi enviado, portanto não há nada para ver aqui, ainda.");
		model.addAttribute("back", true);
		
		return "error";
	}
	
	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleBadData(Model model) {
		model.addAttribute("oops", true);
		model.addAttribute("message", "Mensagem muito longa");
		model.addAttribute("explanation", "Evite enviar mensagens muito logas para o servidor. Lembre-se: comentários podem ter, no máximo, 4096 caracteres.");
		model.addAttribute("back", true);
		
		return "error";
	}
}
