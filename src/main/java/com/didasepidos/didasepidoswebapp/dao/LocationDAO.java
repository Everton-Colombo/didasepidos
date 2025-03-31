package com.didasepidos.didasepidoswebapp.dao;

import java.util.List;

import com.didasepidos.didasepidoswebapp.entity.Location;

public interface LocationDAO {
	
	public List<Location> getLocations();
	public Location getLocation(int id);
	public void saveLocation(Location location);
	public void deleteLocation(int id);
	
	public List<Location> queryLocationsByCountry(String countryCode);
	public List<Location> queryLocationsByDivisionUnit(String divisionUnitName);
	public List<Location> queryLocationsByCity(String city);
	public List<Location> queryLocationsByStreet(String street);
	
}
