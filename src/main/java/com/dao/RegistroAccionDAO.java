package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.entidades.RegistroAccione;

@Stateless
public class RegistroAccionDAO {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager entityManager;

    // Obtener RegistroAccion por ID
    public RegistroAccione obtenerRegistroAccion(long id) {
        return entityManager.find(RegistroAccione.class, id);
    }

    // Obtener todos los Registros de Acciones
    public List<RegistroAccione> obtenerRegistrosAcciones() {
        Query query = entityManager.createQuery("SELECT r FROM RegistroAccione r", RegistroAccione.class);
        return query.getResultList();
    }
    
    //buscar por nombre
  	public RegistroAccione obtenerNombre(String nombre) {
  		try {
  			Query query = entityManager.createQuery("SELECT i FROM RegistroAccione i WHERE i.nombre = :nombre", RegistroAccione.class);
  			query.setParameter("nombre", nombre);

  			List<RegistroAccione> RegistroAccione= query.getResultList();

  			if (!RegistroAccione.isEmpty()) {
  				return RegistroAccione.get(0); 
  			} else {
  				System.out.println("No user found with nombre: " + nombre);
  			}
  		} catch (Exception e) {

  			System.err.println("Error retrieving user from database with username: " + nombre);
  			e.printStackTrace();
  		}

  		return null;
  	}
  	
  		//Crear
	  	public RegistroAccione crear(RegistroAccione nuevo) {
			try {
				entityManager.persist(nuevo);
				entityManager.flush();
				return nuevo; 
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return null; 
			}
		}
	  	
	  	// Actualizar
	    public void actualizar(RegistroAccione registro) {
	        entityManager.merge(registro);
	    }
	    
	  //Obtener estados activos
		public List<RegistroAccione> obtenerEstadosActivos() {
		    TypedQuery<RegistroAccione> query = entityManager.createQuery("SELECT e FROM RegistroAccione e WHERE e.estado = 1", RegistroAccione.class);
		    return query.getResultList();
		}
}
