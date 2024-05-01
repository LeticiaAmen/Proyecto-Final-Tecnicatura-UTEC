package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entidades.ValidacionUsuario;
import com.excepciones.ServiciosException;

@Stateless
public class ValidacionUsuarioDAO {

	public ValidacionUsuarioDAO() {

	}

	@PersistenceContext(unitName="PFT2024")
	private EntityManager entityManager; 

	public void actualizarValidacionUsuario (ValidacionUsuario validacionUsuario)throws ServiciosException{
		try {
			entityManager.persist(validacionUsuario);
		}catch(Exception e) {
			throw new ServiciosException ("Error al crear el estado"); 
		}
	}

	public List<ValidacionUsuario> obtenerValidaciones(){
		TypedQuery<ValidacionUsuario> query = entityManager.createQuery("SELECT u FROM ValidacionUsuario u",
				ValidacionUsuario.class);
		return query.getResultList();
	}
	
	public List<ValidacionUsuario> listarValidacionesFiltro(String filtro){
		TypedQuery<ValidacionUsuario> query = entityManager.createQuery("SELECT u FROM ValidacionUsuario u",
				ValidacionUsuario.class);
		query.setParameter("filtro", "%" + filtro + "%");
		return query.getResultList();
	}
	
	public ValidacionUsuario obtenerValidacionUsuario(long id ) {
		return entityManager.find(ValidacionUsuario.class, id);
	}
	
	public ValidacionUsuario obtenerValidacionPorNombre(String nombre) {
		TypedQuery<ValidacionUsuario> query = entityManager.createNamedQuery("ValidacionUsuario.obtenerPorNombre",
				ValidacionUsuario.class);
		query.setParameter("nombre", nombre);
		List<ValidacionUsuario> estados = query.getResultList();
		if (!estados.isEmpty()) {
			return estados.get(0);
		}
		return null;
	}



}
