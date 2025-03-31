package com.didasepidos.didasepidoswebapp.dao;

import java.util.List;

import com.didasepidos.didasepidoswebapp.entity.Institution;
import com.didasepidos.didasepidoswebapp.type.PageResult;

public interface InstitutionDAO {
	
	public List<Institution> getInstitutions();
	public List<Institution> getInstitutions(OrderByField orderField);
	public Institution getInstitution(int id);
	public void saveInstitution(Institution institution);
	public void deleteInstitution(int id);
	
	public List<Institution> queryInstitutionsByName(String name);
	public PageResult<Institution> queryInstitutionsByName(String name, int page, OrderByField orderByField);
	public List<Institution> queryInstitutionsBySystem(boolean isPrivate);
	public List<Institution> queryInstitutionsBySystem(boolean isPrivate, int page, OrderByField orderByField);
	
	public void loadReviews(Iterable<Institution> institutions);
	
	public long getInstitutionCount();
	public long getInstitutionCountByNameLike(String name);
	public long getInstitutionCountByIsPrivate(boolean isPrivate);
	
}
