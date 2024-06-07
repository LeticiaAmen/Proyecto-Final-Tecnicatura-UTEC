package com.servicios;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.dao.AnalistaDAO;
import com.dao.EstudianteDAO;
import com.dao.TutorDAO;
import com.dao.UsuarioDAO;
import com.entidades.Analista;
import com.entidades.Estudiante;
import com.entidades.Tutor;
import com.entidades.Usuario;

@Stateless
public class UsuarioService {
    
    @PersistenceContext(unitName="PFT2024")
    private EntityManager entityManager; 
    
    @EJB
    private UsuarioDAO usuarioDAO;
    
    @EJB 
    private AnalistaDAO analistaDAO;
    
    @EJB
    private EstudianteDAO estudianteDAO;
    
    @EJB
    private TutorDAO tutorDAO;
    
    public void crearUsuario(Usuario usuario) {
        usuarioDAO.crearUsuario(usuario);
    }
    
    public Usuario obtenerUsuario(Long id) {
        return usuarioDAO.obtenerUsuario(id);
    }
    
    public List<Usuario> obtenerUsuarios(){
        return usuarioDAO.obtenerUsuarios();
    }
    
    public void crearAnalista(Analista analista) {
        analistaDAO.crearAnalista(analista);
    }
    
    public void crearTutor(Tutor tutor) {
        tutorDAO.crearTutor(tutor);
    }
    
    public void crearEstudiante(Estudiante estudiante) {
        estudianteDAO.crearEstudiante(estudiante);
    }
    
    public boolean validarUsuario(String nomUsuario, String contrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return usuarioDAO.validarNombreUsuario(nomUsuario, contrasenia);
    }
    
 // Método para obtener el token JWT con el tipo de usuario
    public String determinarTipoYGenerarToken(Usuario usuario) {
        String tipoUsuario = determinarTipoUsuario(usuario);
        return generarTokenJWT(String.valueOf(usuario.getIdUsuario()), usuario.getNombreUsuario(), tipoUsuario);
    }
    
    public Usuario obtenerUsuarioDesdeBaseDeDatosNombre(String nomUsuario) {
        try {
            TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nomUsuario", Usuario.class);
            query.setParameter("nomUsuario", nomUsuario);
            List<Usuario> usuarios = query.getResultList();
            if (!usuarios.isEmpty()) {
                return usuarios.get(0);  
            } else {
                System.out.println("Usuario con el nombre : " + nomUsuario + " no encontrado en la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error recuperando el usuario con el nombre: " + nomUsuario);
            e.printStackTrace();
        }
        return null;
    }

    public String determinarTipoUsuario(Usuario usuario) {
        if (esAnalista(usuario.getIdUsuario())) {
            return "ANALISTA";
        } else if (esEstudiante(usuario.getIdUsuario())) {
            return "ESTUDIANTE";
        } else if (esTutor(usuario.getIdUsuario())) {
            return "TUTOR";
        }
        return "UNKNOWN";
    }

    // Generar TOKEN
    public String generarTokenJWT(String usuarioId, String nombreUsuario, String rol) {
        try {
            String claveSecreta = "tuClaveSecreta";
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("usuarioId", usuarioId)  
                    .withClaim("nombreUsuario", nombreUsuario)
                    .withClaim("rol", rol)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .sign(Algorithm.HMAC256(claveSecreta));
            return token;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public boolean esAnalista(long idUsuario) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(a) FROM Analista a WHERE a.idUsuario = :idUsuario", Long.class);
        query.setParameter("idUsuario", idUsuario);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public boolean esEstudiante(Long idUsuario) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(e) FROM Estudiante e WHERE e.idUsuario = :idUsuario", Long.class);
        query.setParameter("idUsuario", idUsuario);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return entityManager.find(Usuario.class, idUsuario);
    }
    
    public boolean esTutor(long idUsuario) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(t) FROM Tutor t WHERE t.idUsuario = :idUsuario", Long.class);
        query.setParameter("idUsuario", idUsuario);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public void actualizarUsuario(Usuario usuario) {
        try {
            entityManager.merge(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
        }
    }

    public Estudiante obtenerEstudiante(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario instanceof Estudiante) {
            return (Estudiante) usuario;
        }
        return null;
    }

    public boolean existeCorreo(String correo, Long idUsuario) {
        return usuarioDAO.existeCorreo(correo, idUsuario);
    }
    
    // Buscar por nombre o apellido
    public List<Usuario> buscarPorNombre(String nombre) {
        return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario LIKE :nombre", Usuario.class)
                            .setParameter("nombre", "%" + nombre + "%")
                            .getResultList();
    }
    
    public List<Estudiante> obtenerEstudiantes() {
        return entityManager.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
    }
    
    public boolean existeNombreUsuario(String nombreUsuario) {
    	return usuarioDAO.existeNombreUsuario(nombreUsuario);
    }
    
    public boolean existeCorreo(String correo) {
        return usuarioDAO.existeCorreo(correo);
    }

}
