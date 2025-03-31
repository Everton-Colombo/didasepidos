package com.didasepidos.didasepidoswebapp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.didasepidos.didasepidoswebapp.entity.Location;

@Repository
public class LocationDAOImpl implements LocationDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Location> getLocations() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Location ORDER BY country", Location.class)
				.getResultList();
	}

	@Override
	public Location getLocation(int id) {
		return this.sessionFactory.getCurrentSession().get(Location.class, id);
	}

	@Override
	public void saveLocation(Location location) {
		this.sessionFactory.getCurrentSession().save(location);
	}

	@Override
	public void deleteLocation(int id) {
		this.sessionFactory.getCurrentSession()
		.createQuery("DELETE FROM Location WHERE id=:locationId")
		.setParameter("locationId", id)
		.executeUpdate();
	}

	@Override
	public List<Location> queryLocationsByCountry(String countryCode) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Location WHERE country LIKE '%:code%'", Location.class)
				.setParameter("code", countryCode)
				.getResultList();
	}

	@Override
	public List<Location> queryLocationsByDivisionUnit(String divisionUnitName) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Location WHERE division_unit LIKE '%:name%'", Location.class)
				.setParameter("name", divisionUnitName)
				.getResultList();
	}

	@Override
	public List<Location> queryLocationsByCity(String city) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Location WHERE city LIKE '%:locationCity%'", Location.class)
				.setParameter("locationCity", city)
				.getResultList();
	}

	@Override
	public List<Location> queryLocationsByStreet(String street) {
		return this.sessionFactory.getCurrentSession()
				.createQuery("FROM Location WHERE street LIKE '%:locationStreet%'", Location.class)
				.setParameter("locationStreet", street)
				.getResultList();
	}
	
}
