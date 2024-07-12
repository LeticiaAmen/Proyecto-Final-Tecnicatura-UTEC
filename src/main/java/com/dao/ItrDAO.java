package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.entidades.Estado;
import com.entidades.Itr;

@Stateless
public class ItrDAO {

	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;

	public Itr crearItr(Itr itr) {
		try {
			entityManager.persist(itr);
			entityManager.flush();
			return itr; 
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null; 
		}
	}

	public void actualizarItr(Itr itr) {
		entityManager.merge(itr);
	}

	//obtener itr
	public Itr obtenerItr(long id) {
		return entityManager.find(Itr.class, id);
	}

	//listar todo

	public List<Itr> obtenerItrTodos() {
		return entityManager.createQuery("SELECT i FROM Itr i", Itr.class).getResultList();
	}

	//buscar por nombre
	public Itr obtenerItrDesdeBaseDeDatosNombre(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT i FROM Itr i WHERE i.nombre = :nombre", Itr.class);
			query.setParameter("nombre", nombre);

			List<Itr> Itrs= query.getResultList();

			if (!Itrs.isEmpty()) {
				return Itrs.get(0); 
			} else {

				System.out.println("No user found with nombre: " + nombre);
			}
		} catch (Exception e) {

			System.err.println("Error retrieving user from database with username: " + nombre);
			e.printStackTrace();
		}

		return null;
	}

	// Obtener por estado
	public List<Itr> obtenerItrsPorEstado(Estado estado) {
		try {
			Query query = entityManager.createQuery("SELECT i FROM Itr i WHERE i.estado = :estado", Itr.class);
			query.setParameter("estado", estado);
			return query.getResultList();
		} catch (Exception e) {
			System.err.println("Error retrieving ITRs with estado: " + estado);
			e.printStackTrace();
			return null;
		}
	}
	
	// Obtener itr con estado Activo
	 public List<Itr> obtenerItrsActivos() {
	        try {
	            Query query = entityManager.createQuery("SELECT i FROM Itr i WHERE i.estado.nombre = 'Activo'", Itr.class);
	            return query.getResultList();
	        } catch (Exception e) {
	            System.err.println("Error retrieving active ITRs.");
	            e.printStackTrace();
	            return null;
	        }
	    }



}
