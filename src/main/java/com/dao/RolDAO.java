package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.Rol;

@Stateless
public class RolDAO {

	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;

	public Rol crearRol(Rol rol) {
		try {
			entityManager.persist(rol);
			entityManager.flush();
			return rol;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void actualizarRol(Rol rol) {
		entityManager.merge(rol);
	}

	//obtener rol 
	public Rol obtenerRol(long id) {
		return entityManager.find(Rol.class, id);
	}

	//listar todo
	public List<Rol> obtenerRolTodos(){
		return entityManager.createQuery("SELECT r FROM Rol r", Rol.class).getResultList();
	}
	
	//buscar por nombre
	public Rol obtenerRolPorNombre(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombre", Rol.class);
			query.setParameter("nombre", nombre);

			List<Rol> roles= query.getResultList();

			if (!roles.isEmpty()) {
				return roles.get(0); 
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
