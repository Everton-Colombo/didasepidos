package com.didasepidos.didasepidoswebapp.aop;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.didasepidos.didasepidoswebapp.type.exception.AuthorIdGenerationRequestLimitExceededException;
import com.didasepidos.didasepidoswebapp.type.exception.InstitutionRatingLimitExceededException;
import com.didasepidos.didasepidoswebapp.type.exception.InvalidReviewException;
import com.didasepidos.didasepidoswebapp.type.exception.NotAllowedException;

@ControllerAdvice
public class MainWebAppExceptionHandlerAdvice {
	
	@ExceptionHandler(AuthorIdGenerationRequestLimitExceededException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleTooManyAuthorIds(Exception e, Model model) {
		e.printStackTrace();
		
		model.addAttribute("oops", true);
		model.addAttribute("message", "Você apagou sua identidade muitas vezes...");
		model.addAttribute("explanation", "Para evitar fraudes, nós estabelecemos um limite de identidades que podem ser geradas. "
				+ "Se isso não foi proposital, não se preocupe: este erro desaparecerá em, no máximo, 30 dias, e "
				+ "você ainda terá acesso a algumas partes deste site. "
				+ "Para contornar futuros transtornos, evite limpar os cookies desta página.");
		model.addAttribute("back", true);
		
		return "error";
	}
	
	@ExceptionHandler(InstitutionRatingLimitExceededException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleRatingLimitExceeded(Exception e, Model model) {
		e.printStackTrace();
		
		model.addAttribute("oops", true);
		model.addAttribute("message", "Você só pode avaliar esta instituição 1 vez por semana...");
		model.addAttribute("explanation", "Esse limite existe para evitar fraudes e sobrecarregamentos.");
		model.addAttribute("back", true);
		
		return "error";
	}
	
	@ExceptionHandler(NotAllowedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleActionNotAllowed(Exception e, Model model) {
		e.printStackTrace();
		
		model.addAttribute("oops", true);
		model.addAttribute("message", "Ação proíbida!");
		model.addAttribute("explanation", e.getMessage());
		model.addAttribute("back", true);
		
		return "error";
	}
	
	@ExceptionHandler(InvalidReviewException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleInvalidReview(Exception e, Model model) {
		e.printStackTrace();
		
		model.addAttribute("oops", true);
		model.addAttribute("message", "Avaliação Inválida!");
		model.addAttribute("explanation", "Você precisa avaliar, ao menos, 1 matéria.");
		model.addAttribute("back", true);
		
		return "error";
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleUnknownError(Exception e,  Model model) {
		e.printStackTrace();
		
		model.addAttribute("oops", true);
		model.addAttribute("message", "Erro desconhecido :/");
		model.addAttribute("explanation", String.format("<strong>%s: %s</strong><br>"
				+ "Se isso foi propisital, que feio! Se não, por favor nos avise.", e.getClass().getName(), e.getMessage()));
		model.addAttribute("back", true);
		
		return "error";
	}
}
