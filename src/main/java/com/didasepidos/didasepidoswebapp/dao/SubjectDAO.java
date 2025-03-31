package com.didasepidos.didasepidoswebapp.dao;

import java.util.List;

import com.didasepidos.didasepidoswebapp.entity.Subject;

public interface SubjectDAO {
	
	public List<Subject> getSubjects();
	public Subject getSubject(int id);
	public void saveSubject(Subject subject);
	public void deleteSubject(int id);
	
	public List<Subject> querySubjectsByName(String name);
}
