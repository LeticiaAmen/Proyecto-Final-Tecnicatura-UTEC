package com.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.Analista;

@Stateless
public class AnalistaDAO {
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager; 
	
	public void crearAnalista (Analista analista) {
		entityManager.persist(analista);
		entityManager.flush();
	}
	
	

}
