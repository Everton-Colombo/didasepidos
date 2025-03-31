package com.didasepidos.didasepidoswebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.didasepidos.didasepidoswebapp.dao.InstitutionDAO;
import com.didasepidos.didasepidoswebapp.dao.LocationDAO;
import com.didasepidos.didasepidoswebapp.dao.SubjectDAO;
import com.didasepidos.didasepidoswebapp.entity.Institution;
import com.didasepidos.didasepidoswebapp.entity.Location;
import com.didasepidos.didasepidoswebapp.entity.Subject;

@Service
public class TestDatabasePopulationService {
	
	@Autowired
	LocationDAO locationDAO;
	
	@Autowired
	InstitutionDAO institutionDAO;
	
	@Autowired
	SubjectDAO subjectDAO;
	
	@Transactional
	public void populate() {
		this.locationDAO.saveLocation(new Location("BRA", "Pará", "Santarém", "Av. São Sebastião", "300"));			// CDA
		this.locationDAO.saveLocation(new Location("BRA", "Pará", "Santarém", "Av. Sérgio Henn", "584"));			// Adv
		this.locationDAO.saveLocation(new Location("BRA", "Pará", "Santarém", "Av. São Sebastião", "799"));			// CSC
		this.locationDAO.saveLocation(new Location("BRA", "Pará", "Santarém", "Av. Mal. Castelo Branco", "621"));	// IFPA
		this.locationDAO.saveLocation(new Location("BRA", "Pará", "Santarém", "Av. Mendonça Furtado", "1779"));		// Batis
		
		this.institutionDAO.saveInstitution(new Institution("Colégio dom Amando", true, locationDAO.getLocation(1), null));
		this.institutionDAO.saveInstitution(new Institution("Colégio Adventista de Santarém", true, locationDAO.getLocation(2), null));
		this.institutionDAO.saveInstitution(new Institution("Colégio Santa Clara", true, locationDAO.getLocation(3), null));
		this.institutionDAO.saveInstitution(new Institution("IFPA - Instituto Federal do Pará", true, locationDAO.getLocation(4), null));
		this.institutionDAO.saveInstitution(new Institution("Colégio Batista de Santarém", true, locationDAO.getLocation(5), null));
		
		this.subjectDAO.saveSubject(new Subject("Português"));
		this.subjectDAO.saveSubject(new Subject("Redação"));
		this.subjectDAO.saveSubject(new Subject("Literatura"));
		this.subjectDAO.saveSubject(new Subject("Inglês"));
		this.subjectDAO.saveSubject(new Subject("Espanhol"));
		this.subjectDAO.saveSubject(new Subject("Matemática"));
		this.subjectDAO.saveSubject(new Subject("Física"));
		this.subjectDAO.saveSubject(new Subject("Química"));
		this.subjectDAO.saveSubject(new Subject("Biologia"));
		this.subjectDAO.saveSubject(new Subject("História"));
		this.subjectDAO.saveSubject(new Subject("Geografia"));
		this.subjectDAO.saveSubject(new Subject("Filosofia"));
		this.subjectDAO.saveSubject(new Subject("Sociologia"));
		this.subjectDAO.saveSubject(new Subject("Artes"));
		this.subjectDAO.saveSubject(new Subject("Educação Física"));
		
		for(Institution institution : this.institutionDAO.getInstitutions()) {
			institution.setSubjectsTaught(this.subjectDAO.getSubjects());
		}
	}
}
