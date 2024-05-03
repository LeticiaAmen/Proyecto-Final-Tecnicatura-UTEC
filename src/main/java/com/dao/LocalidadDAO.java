package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entidades.Localidad;

@Stateless
public class LocalidadDAO {
	
	public LocalidadDAO() {
		
	}
	
	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager; 

	public Localidad obtenerLocalidadPorId(long IdLocalidad) {
		return entityManager.find(Localidad.class, IdLocalidad); 
	}
	
	public List<Localidad> obtenerTodasLocalidades(){
		TypedQuery<Localidad> query = entityManager.createNamedQuery("Localidad.obtenerTodasLocalidades", Localidad.class);
		return query.getResultList();
	}
	
	public Localidad actualizarLocalidad(Localidad localidad) {
	    if (localidad != null) {
	        localidad = entityManager.merge(localidad);
	        entityManager.flush(); 
	    }
	    return localidad;
	}

	
	//-------------------------------Listar por filtro-------------------------------------
			public List<Localidad> listarLocalidadFiltro(String nombre) {
				TypedQuery<Localidad> query = entityManager.createNamedQuery("Localidad.obtenerPorNombre", Localidad.class);
				query.setParameter("nombre", "%" + nombre + "%");
				return query.getResultList();
			}

			public Localidad obtenerLocalidadPorNombre(String nombre) {
				TypedQuery<Localidad> query = entityManager.createNamedQuery("Localidad.obtenerPorNombre", Localidad.class);
				query.setParameter("nombre", nombre);
				List<Localidad> localidades = query.getResultList();
				if (!localidades.isEmpty()) {
					return localidades.get(0);
				}
				return null;
			}
	
}
