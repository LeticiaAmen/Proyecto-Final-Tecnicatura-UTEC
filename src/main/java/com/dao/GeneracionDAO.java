package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.Generacion;

@Stateless
public class GeneracionDAO {
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;
	
	public Generacion crearGeneracion(Generacion generacion) {
		try {
			entityManager.persist(generacion);
			entityManager.flush();
			return generacion; 
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void actualizarGeneracion(Generacion generacion) {
		entityManager.merge(generacion); 
	}
	
	//obtener generacion
	public Generacion obtenerGeneracion(long id) {
		return entityManager.find(Generacion.class, id);
	}
	
	//listar todo
	public List<Generacion> obtenerGeneracionesTodas(){
		return entityManager.createQuery("SELECT g FROM Generacion g", Generacion.class).getResultList();
	}
	
	//buscar por nombre
	public Generacion obtenerGeneracionPorNombre(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT g FROM Generacion g WHERE g.nombre = :nombre", Generacion.class);
			query.setParameter("nombre", nombre);

			List<Generacion> generaciones= query.getResultList();

			if (!generaciones.isEmpty()) {
				return generaciones.get(0); 
			} else {

				System.out.println("No user found with nombre: " + nombre);
			}
		} catch (Exception e) {

			System.err.println("Error retrieving user from database with username: " + nombre);
			e.printStackTrace();
		}

		return null;
	}

}
