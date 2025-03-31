package com.didasepidos.didasepidoswebapp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.didasepidos.didasepidoswebapp.type.AuthorId;

public interface MainAppSecurityService {
	public int getRecentAuthorGenerationCount(String ip);
	public AuthorId generateAuthorId(String ip);
	public boolean idMatch(AuthorId id);
	
	public AuthorId handleCredentialChecking(HttpServletRequest request, HttpServletResponse respose);
}
