package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.Usuario;

@Stateless
public class UsuarioDAO {

	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager; 

	public Usuario crearUsuario(Usuario usuario) {
		try {
			entityManager.persist(usuario);
			entityManager.flush();
			return usuario;
		}catch (Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Usuario obtenerUsuario(long id) {
		return entityManager.find(Usuario.class, id);
	}

	public List<Usuario> obtenerUSuarios(){
		return entityManager.createQuery("FROM Usuario", Usuario.class).getResultList();
	}

	public void actualizarUsuario(Usuario usuario) {
		entityManager.merge(usuario);
	}

	//listar todo

	public List<Usuario> obtenerTodosLosUsuarios() {
		return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
	}

	// validar usuario
	public boolean validarNombreUsuario(String nomUsuario, String hashContraseña) {
	    try {
	        long count = (Long) entityManager.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.nombreUsuario = :nomUsuario AND u.hashContraseña = :hashContraseña")
	            .setParameter("nomUsuario", nomUsuario)
	            .setParameter("hashContraseña", hashContraseña)
	            .getSingleResult();
	        return count > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	//buscar usuario por nombre de usuario
	public Usuario obtenerUsuarioDesdeBaseDeDatosNombre(String nomUsuario) {
		try {
			Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nomUsuario = :nomUsuario", Usuario.class);
			query.setParameter("nomUsuario", nomUsuario);

			List<Usuario> usuarios = query.getResultList();

			if (!usuarios.isEmpty()) {
				return usuarios.get(0); 
			} else {
				System.out.println("Usuario con el nobre : " + nomUsuario + " no encontrado en la BD");
			}
		} catch (Exception e) {
			System.err.println("Error recuperando usuario desde la base de datos con el nombre: " + nomUsuario);
			e.printStackTrace();
		}

		return null;
	}
	
	//faltan mas metodos de los filtros.. 
	



	public UsuarioDAO() {
	}

}
