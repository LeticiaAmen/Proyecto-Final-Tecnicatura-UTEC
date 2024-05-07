package com.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.RegistroAccione;

@Stateless
public class RegistroAccionDAO {
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;
	
	//obtener RegistroAccion
	public RegistroAccione obtenerRegistroAccion(long id) {
		return entityManager.find(RegistroAccione.class, id);
	}

}
