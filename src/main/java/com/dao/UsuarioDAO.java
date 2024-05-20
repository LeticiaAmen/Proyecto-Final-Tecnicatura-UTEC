package com.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.Usuario;
import com.util.PasswordUtils;

@Stateless
public class UsuarioDAO {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager entityManager;

    public Usuario crearUsuario(Usuario usuario) {
        try {
            // Verificar unicidad del nombre de usuario
            if (existeNombreUsuario(usuario.getNombreUsuario())) {
                throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
            }
            // Verificar unicidad del correo institucional
            if (existeCorreoInstitucional(usuario.getMailInstitucional())) {
                throw new IllegalArgumentException("El correo institucional ya está en uso.");
            }

            // Generar salt y hash para la contraseña
            String salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(usuario.getHashContraseña(), salt);

            usuario.setSaltContraseña(salt);
            usuario.setHashContraseña(hashedPassword);

            entityManager.persist(usuario);
            entityManager.flush();
            return usuario;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Usuario obtenerUsuario(long id) {
        return entityManager.find(Usuario.class, id);
    }

    public List<Usuario> obtenerUsuarios() {
        return entityManager.createQuery("FROM Usuario", Usuario.class).getResultList();
    }

    public void actualizarUsuario(Usuario usuario) {
        entityManager.merge(usuario);
        entityManager.flush();
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public boolean validarNombreUsuario(String nomUsuario, String contrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            Usuario usuario = (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nomUsuario")
                .setParameter("nomUsuario", nomUsuario)
                .getSingleResult();

            if (usuario != null) {
                return PasswordUtils.verifyPassword(contrasenia, usuario.getSaltContraseña(), usuario.getHashContraseña());
            }
            return false;
        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario obtenerUsuarioDesdeBaseDeDatosNombre(String nomUsuario) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nomUsuario", Usuario.class);
            query.setParameter("nomUsuario", nomUsuario);

            List<Usuario> usuarios = query.getResultList();

            if (!usuarios.isEmpty()) {
                return usuarios.get(0);
            } else {
                System.out.println("Usuario con el nombre: " + nomUsuario + " no encontrado en la BD");
            }
        } catch (Exception e) {
            System.err.println("Error recuperando usuario desde la base de datos con el nombre: " + nomUsuario);
            e.printStackTrace();
        }

        return null;
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("nombreUsuario", nombreUsuario);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    public boolean existeCorreoInstitucional(String correoInstitucional) {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.mailInstitucional = :correoInstitucional";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("correoInstitucional", correoInstitucional);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    public boolean existeCorreo(String correo, Long idUsuario) {
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.mail = :correo AND u.idUsuario <> :idUsuario";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("correo", correo);
        query.setParameter("idUsuario", idUsuario);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    public UsuarioDAO() {
    }
}
