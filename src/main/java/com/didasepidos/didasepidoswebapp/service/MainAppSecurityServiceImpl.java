package com.didasepidos.didasepidoswebapp.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import com.didasepidos.didasepidoswebapp.dao.GeneratedAuthorsDAO;
import com.didasepidos.didasepidoswebapp.entity.AuthorIdGenerationRecord;
import com.didasepidos.didasepidoswebapp.type.AuthorId;
import com.didasepidos.didasepidoswebapp.type.exception.AuthorIdGenerationRequestLimitExceededException;
import com.didasepidos.didasepidoswebapp.util.DidasepidosUM;

@Service
@PropertySource("classpath:application.properties")
public class MainAppSecurityServiceImpl implements MainAppSecurityService {

	@Value("${webapp.limits.authorIdsPerMonth}")
	private Integer authorIdLimit;
	
	@Value("${app.security.cookieNames.authorId}")
	private String authorIdCookieName;
	
	@Value("${app.security.cookieNames.authorIdCT}")
	private String authorIdCTCookieName;
	
	@Value("${app.security.cookieNames.authorIdOrigin}")
	private String authorIdOriginCookieName;
	
	@Autowired
	private GeneratedAuthorsDAO generatedAuthorsDAO;
	
	@Override
	@Transactional
	public int getRecentAuthorGenerationCount(String ip) {
		return this.generatedAuthorsDAO.
				getGeneratedAuthorIdCountFromOriginFromLastXDays(
						DidasepidosUM.hash(ip), 30
				);
	}

	@Override
	@Transactional
	public AuthorId generateAuthorId(String ip) {
		if(this.getRecentAuthorGenerationCount(ip) >= authorIdLimit) {
			throw new AuthorIdGenerationRequestLimitExceededException("Você apagou sua identidade muitas vezes. Se isso não foi intencional, "
					+ "não se preocupe: esse erro é uma medida de segurança e permanace por, no máximo, 30 dias. Para evitar que isto ocorra no "
					+ "futuro, evite limpar os cookies deste site.");
		}
		
		Date now = new Date(System.currentTimeMillis());
		String origin = DidasepidosUM.hash(ip);
		
		this.generatedAuthorsDAO.insertAuthorIdGenerationRecord(
					new AuthorIdGenerationRecord(origin, now)
				);
		
		return new AuthorId(DidasepidosUM.hash(origin, now), origin, now);
	}

	@Override
	public boolean idMatch(AuthorId id) {
		try {
			return DidasepidosUM.hash(id.getOrigin(), id.getTimeOfGeneration()).toString().equals(id.getAuthorString());
		} catch (NullPointerException e) {
			return false;
		}
	}

	@Override
	@Transactional
	public AuthorId handleCredentialChecking(HttpServletRequest request, HttpServletResponse response) {
		AuthorId authorId = new AuthorId();
		
		Cookie authorIdCookie = WebUtils.getCookie(request, authorIdCookieName);
		Cookie ctCookie = WebUtils.getCookie(request, authorIdCTCookieName);
		Cookie originCookie = WebUtils.getCookie(request, authorIdOriginCookieName);
		
		if(authorIdCookie != null) { authorId.setAuthorString(authorIdCookie.getValue()); } 
		if(ctCookie != null) { authorId.setTimeOfGeneration(new Date(Long.parseLong(ctCookie.getValue()))); }
		if(originCookie != null) { authorId.setOrigin(originCookie.getValue()); } 
		
		if(!this.idMatch(authorId)) { 
			authorId = this.generateAuthorId(request.getRemoteAddr());
			
			Cookie aidCookie = new Cookie(authorIdCookieName, authorId.getAuthorString());
			aidCookie.setMaxAge(10 * 365 * 24 * 60 * 60);	// 10 years
			response.addCookie(aidCookie);
			
			Cookie aidCtCookie = new Cookie(authorIdCTCookieName, String.valueOf(authorId.getTimeOfGeneration().getTime()));
			aidCtCookie.setMaxAge(10 * 365 * 24 * 60 * 60); // 10 years
			response.addCookie(aidCtCookie);
			
			Cookie aidOriginCookie = new Cookie(authorIdOriginCookieName, authorId.getOrigin());
			aidOriginCookie.setMaxAge(10 * 365 * 24 * 60 * 60); // 10 years
			response.addCookie(aidOriginCookie);
		}
		
		return authorId;
	}

}
