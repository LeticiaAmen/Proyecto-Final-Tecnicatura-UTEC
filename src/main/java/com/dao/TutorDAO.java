package com.dao;

import java.util.List;

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
	 // Obtener Tutor por ID
    public Tutor obtenerTutor(long id) {
        return entityManager.find(Tutor.class, id);
    }

    // Listar todos los Tutores
    public List<Tutor> obtenerTutoresTodos() {
        return entityManager.createQuery("SELECT t FROM Tutor t", Tutor.class).getResultList();
    }
}