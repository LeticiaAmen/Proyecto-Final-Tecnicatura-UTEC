package com.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.Estudiante;

@Stateless
public class EstudianteDAO {

	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;
	
	public void crearEstudiante(Estudiante estudiante) {
		entityManager.persist(estudiante);
		entityManager.flush();
	}

}
