package com.servicios;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	public void crearUsuario (Usuario usuario) {
		usuarioDAO.crearUsuario(usuario);
	}
	
	public Usuario obtenerUsuario(Long id) {
		return usuarioDAO.obtenerUsuario(id);
	}
	
	public List<Usuario> obtenerUsuarios(){
		return usuarioDAO.obtenerUSuarios();
	}
	
	
	public void crearAnalista(Analista analista) {
		analistaDAO.crearAnalista(analista);
	}
	
	public void crearTutor (Tutor tutor) {
		tutorDAO.crearTutor(tutor);
	}
	
	public void crearEstudiante (Estudiante estudiante) {
		estudianteDAO.crearEstudiante(estudiante);
	}
	
	public boolean validarUsuario(String nomUsuario, String contrasenia) {
		return usuarioDAO.validarNombreUsuario(nomUsuario, contrasenia);
	}
	
	public String determinarTipoYGenerarToken(Usuario usuario) {
	    String tipoUsuario = "UNKNOWN"; // Valor por defecto
	    if (esAnalista(usuario.getIdUsuario())) {
	        tipoUsuario = "ANALISTA";
	    } else if (esEstudiante(usuario.getIdUsuario())) {
	        tipoUsuario = "ESTUDIANTE";
	    } else if (esTutor(usuario.getIdUsuario())) {
	        tipoUsuario = "TUTOR";
	    }
	    
	    return generarTokenJWT(String.valueOf(usuario.getIdUsuario()), usuario.getNombreUsuario(), tipoUsuario);
	}
	
	public Usuario obtenerUsuarioDesdeBaseDeDatosNombre(String nomUsuario) {
	    try {
	        Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nomUsuario", Usuario.class);
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

	
	//generar TOKEN
	public String generarTokenJWT(String usuarioId, String nombreUsuario, String rol) {
        try {
            // Definir la clave secreta para firmar el token
            String claveSecreta = "tuClaveSecreta"; // clave secreta real

            // Crear el token
            String token = JWT.create()
                    .withIssuer("auth0") // string que identifique al emisor del token
                    .withClaim("usuarioId", usuarioId) // ID del usuario al token
                    .withClaim("nombreUsuario", nombreUsuario) // nombre de usuario
                    .withClaim("rol", rol) //nuevo campo
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // Tiempo de expiración (1 hora)
                    .sign(Algorithm.HMAC256(claveSecreta)); // Firma del token con la clave secreta y el algoritmo HMAC256

            return token;
        } catch (JWTCreationException exception){
            // Control del error en la creación del token
            return null;
        }
    }
	
	public boolean esAnalista(long idUsuario) {
	    List<Analista> result = entityManager.createQuery(
	        "SELECT a FROM Analista a WHERE a.idUsuario = :idUsuario", Analista.class)
	        .setParameter("idUsuario", idUsuario)
	        .getResultList();
	    return !result.isEmpty();
	}


	public boolean esEstudiante(long idUsuario) {
	    List<Estudiante> result = entityManager.createQuery(
	        "SELECT e FROM Estudiante e WHERE e.idUsuario = :idUsuario", Estudiante.class)
	        .setParameter("idUsuario", idUsuario)
	        .getResultList();
	    return !result.isEmpty();
	}


	public boolean esTutor(long idUsuario) {
	    List<Tutor> result = entityManager.createQuery(
	        "SELECT t FROM Tutor t WHERE t.idUsuario = :idUsuario", Tutor.class)
	        .setParameter("idUsuario", idUsuario)
	        .getResultList();
	    return !result.isEmpty();
	}

	public void actualizarUsuario(Usuario usuario) {
	    try {
	        entityManager.merge(usuario);
	    } catch (Exception e) {
	        throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
	    }
	}



	
	//FALTAN LOS MÉTODOS DE LOS FILTROS
	
	
	

}