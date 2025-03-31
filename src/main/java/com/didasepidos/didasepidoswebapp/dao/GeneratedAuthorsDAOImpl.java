package com.didasepidos.didasepidoswebapp.dao;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.didasepidos.didasepidoswebapp.entity.AuthorIdGenerationRecord;

@Repository
public class GeneratedAuthorsDAOImpl implements GeneratedAuthorsDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int getGeneratedAuthorIdCountFromOrigin(String origin) {
		return this.sessionFactory.getCurrentSession()
						.createQuery("SELECT COUNT(*) FROM AuthorIdGenerationRecord WHERE origin = :ori", Integer.class)
						.setParameter("ori", origin)
						.uniqueResult();
						
	}

	@Override
	public int getGeneratedAuthorIdCountFromOriginFromLastXDays(String origin, int x) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM AuthorIdGenerationRecord WHERE origin = :ori AND datetime > :dtm", Long.class)
				.setParameter("ori", origin)
				.setParameter("dtm", DateUtils.addDays(new Date(System.currentTimeMillis()), -x))
				.uniqueResult().intValue();
	}

	@Override
	public void insertAuthorIdGenerationRecord(AuthorIdGenerationRecord record) {
		this.sessionFactory.getCurrentSession().save(record);

	}

}
