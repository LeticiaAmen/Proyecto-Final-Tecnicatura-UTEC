package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entidades.Estado;
import com.excepciones.ServiciosException;

@Stateless
public class EstadoDAO {
	
	public EstadoDAO() {
		
	}
	
	@PersistenceContext(unitName="PFT2024")
	private EntityManager entityManager; 
	
	public void actualizarEstado(Estado estado) throws ServiciosException{
		try {
			entityManager.persist(estado);
		}catch (Exception e) {
			throw new ServiciosException ("Error al crear el estado");
		}
	}
	
	public List<Estado> obtnerEstados(){
		TypedQuery<Estado> query = entityManager.createQuery("SELECT e FROM Estado e", 
				Estado.class);
		return query.getResultList();
	}
	
	public List<Estado> listarEstadosFiltro (String filtro){
		TypedQuery<Estado> query = entityManager.createQuery("SELECT e FROM Estado e",
				Estado.class);
		query.setParameter("filtro", "%" + filtro + "%");
		return query.getResultList();
	}
	
	public Estado obtenerEstadoId(long id) {
		return entityManager.find(Estado.class, id);
	}
	
	public Estado obtenerEstadoPorNombre(String nombre) {
		TypedQuery<Estado> query = entityManager.createNamedQuery("Estado.obtenerPorNombre",
				Estado.class);
		query.setParameter("nombre", nombre);
		List<Estado> estados = query.getResultList();
		if(!estados.isEmpty()) {
			return estados.get(0);
		}
		return null; 
	}

}
