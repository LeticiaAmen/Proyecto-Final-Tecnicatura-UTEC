package com.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.Tutor;

@Stateless
public class TutorDAO {
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;
	
	public void crearTutor(Tutor tutor) {
		entityManager.persist(tutor);
		entityManager.flush();
		
	}

}
