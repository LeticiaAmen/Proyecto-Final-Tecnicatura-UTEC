package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.Estado;
import com.entidades.Reclamo;

@Stateless
public class ReclamoDAO {
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;

	public Reclamo crearReclamo(Reclamo reclamo) {
		try {
			entityManager.persist(reclamo);
			entityManager.flush();
			return reclamo;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public void actualizarReclamo(Reclamo reclamo) {
		entityManager.merge(reclamo);
	}
	
	//obtener reclamo
	public Reclamo obtenerReclamo(long id) {
		return entityManager.find(Reclamo.class, id);
	}
	
	//listar todo
	public List<Reclamo> obtenerReclamosTodos(){
		return entityManager.createQuery("SELECT r FROM Reclamo r", Reclamo.class).getResultList();
	}
	
	
	//buscar por nombre
	public Reclamo obtenerReclamoDesdeBaseDeDatosNombre(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT r FROM Reclamo r WHERE r.nombre = :nombre", Reclamo.class);
			query.setParameter("nombre", nombre);
			
			List<Reclamo> reclamos = query.getResultList()	;
			
			if(!reclamos.isEmpty()) {
				return reclamos.get(0);
			}else {
				System.out.println("No user found with nombre: " + nombre);
			}
		}catch(Exception e) {
			System.err.println("Error retrieving user from database with username: " + nombre);
			e.printStackTrace();
		}
		return null;
	}
	
	//obtener por estado
	public List<Reclamo> obtenerReclamosPorEstado(Estado estado){
		try {
			Query query = entityManager.createQuery("SELECT r FROM Reclamo r WHERE r.estado = :estado", Reclamo.class);
			query.setParameter("estado", estado);
			return query.getResultList();
		}catch(Exception e) {
			System.err.println("Error retrieving Reclamos with estado: " + estado);
			e.printStackTrace();
			return null;
		}
	}
	

}
