package com.didasepidos.didasepidoswebapp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.didasepidos.didasepidoswebapp.entity.Subject;

@Repository
public class SubjectDAOImpl implements SubjectDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Subject> getSubjects() {
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Subject ORDER BY name", Subject.class)
					.getResultList();
	}

	@Override
	public Subject getSubject(int id) {
		return this.sessionFactory.getCurrentSession().get(Subject.class, id);
	}

	@Override
	public void saveSubject(Subject subject) {
		this.sessionFactory.getCurrentSession().save(subject);
	}

	@Override
	public void deleteSubject(int id) {
		this.sessionFactory.getCurrentSession()
			.createQuery("DELETE FROM Subject WHERE id=:subjectId")
			.setParameter("subjectId", id)
			.executeUpdate();
		
	}

	@Override
	public List<Subject> querySubjectsByName(String name) {
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Subject WHERE name LIKE '%:subjectName%'", Subject.class)
					.setParameter("subjectName", name)
					.getResultList();
	}

}
