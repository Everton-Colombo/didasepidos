package com.didasepidos.didasepidoswebapp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.didasepidos.didasepidoswebapp.entity.Institution;
import com.didasepidos.didasepidoswebapp.entity.Review;
import com.didasepidos.didasepidoswebapp.type.PageResult;
import com.didasepidos.didasepidoswebapp.type.exception.PageNotFoundException;

@Repository
@PropertySource("classpath:application.properties")
public class InstitutionDAOImpl implements InstitutionDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${webapp.pagination.pageSize.institutions}")
	private Integer pageSize;
	
	@Override
	public List<Institution> getInstitutions() {
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Institution ORDER BY name", Institution.class)
					.getResultList();
	}
	
	@Override
	public List<Institution> getInstitutions(OrderByField orderField) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Institution" + orderField.sqlString, Institution.class)
				.getResultList();
	}


	@Override
	public Institution getInstitution(int id) {
		return this.sessionFactory.getCurrentSession().get(Institution.class, id);
	}

	@Override
	public void saveInstitution(Institution institution) {
		this.sessionFactory.getCurrentSession().save(institution);
	}

	@Override
	public void deleteInstitution(int id) {
		this.sessionFactory.getCurrentSession()
			.createQuery("DELETE FROM Institution WHERE id=:institutionId")
			.setParameter("institutionId", id)
			.executeUpdate();
	}

	@Override
	public List<Institution> queryInstitutionsByName(String name) {
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Institution WHERE name LIKE CONCAT('%', :nameProp, '%') ORDER BY name", Institution.class)
					.setParameter("nameProp", name)
					.getResultList();
	}

	@Override
	public List<Institution> queryInstitutionsBySystem(boolean isPrivate) {
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Institution WHERE is_private=:isPvt", Institution.class)
					.setParameter("isPvt", isPrivate)
					.getResultList();
	}

	@Override
	public void loadReviews(Iterable<Institution> institutions) {
		for (Institution institution : institutions) {
			institution.setReviews(
				this.sessionFactory.getCurrentSession()
					.createQuery("SELECT r FROM Review r WHERE r.targetInstitution.id = :instId", Review.class)
					.setParameter("instId", institution.getId())
					.getResultList()
			);
		}
	}

	@Override
	public long getInstitutionCount() {
		return (long) this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM Institution")
				.uniqueResult();
	}

	@Override
	public PageResult<Institution> queryInstitutionsByName(String name, int page, OrderByField orderByField) {
		long totalCount = this.getInstitutionCountByNameLike(name);
		int lastPageNumber = (int) Math.ceil((float) totalCount / this.pageSize);
		if (page < 1 || page > lastPageNumber) {
			throw new PageNotFoundException(String.format("A página requisitada (%s) não existe: só foram encontradas %s páginas de conteúdo.", page, lastPageNumber));
		}
		
		PageResult<Institution> result = new PageResult<>();
		result.setPageCount(lastPageNumber);
		result.setCurrentPage(page);
		result.setTotalResultCount((int) totalCount);
		result.setPageResultList(this.sessionFactory.getCurrentSession()
										.createQuery("FROM Institution WHERE name LIKE CONCAT('%', :instName, '%')" + orderByField.sqlString, Institution.class)
										.setParameter("instName", name)
										.setFirstResult((page - 1) * this.pageSize)
										.setMaxResults(this.pageSize)
										.list());
		
		return result;
	}

	@Override
	public List<Institution> queryInstitutionsBySystem(boolean isPrivate, int page, OrderByField orderByField) {
		int lastPageNumber = (int) Math.ceil((float) this.getInstitutionCountByIsPrivate(isPrivate) / this.pageSize);
		if (page < 1 || page > lastPageNumber) {
			throw new PageNotFoundException(String.format("Page resquested (%s) does not exist. Current Bounds: 1 <= page <= %s", page, lastPageNumber));
		}
		
		return this.sessionFactory.getCurrentSession()
					.createQuery("FROM Institution WHERE is_private = :isPvt" + orderByField.sqlString, Institution.class)
					.setParameter("isPvt", isPrivate)
					.setFirstResult((page - 1) * this.pageSize)
					.setMaxResults(this.pageSize)
					.list();
	}

	@Override
	public long getInstitutionCountByNameLike(String name) {
		return (long) this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM Institution WHERE name LIKE CONCAT('%', :instName, '%')")
				.setParameter("instName", name)
				.uniqueResult();
	}
	
	@Override
	public long getInstitutionCountByIsPrivate(boolean isPrivate) {
		return (long) this.sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(*) FROM Institution WHERE is_private = :isPvt")
				.setParameter("isPvt", isPrivate)
				.uniqueResult();
	}

}
