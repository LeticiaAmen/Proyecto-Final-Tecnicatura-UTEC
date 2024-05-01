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
	public boolean validarNombreUsuario(String nomUsuario, String contrasenia) {
		try {
			// Se asume que contrasenia ya es un hash y se compara con el hash almacenado en la base de datos
			long count = (Long) entityManager
					.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.nomUsuario = :nomUsuario AND u.contrasenia = :contrasenia")
					.setParameter("nomUsuario", nomUsuario)
					.setParameter("contrasenia", contrasenia) // Esto debería ser el hash de la contraseña
					.getSingleResult();

			return count > 0;
		} catch (NoResultException e) {
			// No se encontró el usuario con esas credenciales
			return false;
		} catch (NonUniqueResultException e) {
			// Más de un usuario con el mismo nombre de usuario y contraseña (esto no debería ocurrir si los nombres de usuario son únicos)
			return false;
		} catch (Exception e) {
			// Manejar cualquier otra excepción que pueda ocurrir
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
				return usuarios.get(0); // Assuming 'nomUsuario' is unique and there's at most one result
			} else {
				// Log that no users were found with this username
				System.out.println("No user found with username: " + nomUsuario);
			}
		} catch (Exception e) {
			// Log the exception with more detail
			System.err.println("Error retrieving user from database with username: " + nomUsuario);
			e.printStackTrace();
		}

		return null;
	}
	
	//faltan mas metodos de los filtros.. 
	



	public UsuarioDAO() {
		// TODO Auto-generated constructor stub
	}

}
