package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.Area;

@Stateless
public class AreaDAO {
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;
	
	public Area crearArea(Area area) {
		try {
			entityManager.persist(area);
			entityManager.flush();
			return area;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void actualizarArea (Area area) {
		entityManager.merge(area);
	}
	
	//obtener area
	public Area obtenerArea(long id) {
		return entityManager.find(Area.class, id);
	}
	
	//listar todo
	public List<Area> obtenerAreasTodas(){
		return entityManager.createQuery("SELECT a FROM Area a", Area.class).getResultList();
		
	}
	
	//buscar por nombre
	public Area obtenerAreaPorNombre(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT a FROM Area a WHERE a.nombre = :nombre", Area.class);
			query.setParameter("nombre", nombre);

			List<Area> areas= query.getResultList();

			if (!areas.isEmpty()) {
				return areas.get(0); 
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
