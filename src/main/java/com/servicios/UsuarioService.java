package com.servicios;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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
	
	public void actualizarUsuario(Usuario usuario) {
		usuarioDAO.actualizarUsuario(usuario);
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
	
	//obtener usuario por nombre de usuario
	public Usuario obtenerUsuarioDesdeBaseDeDatosNombre(String nomUsuario) {
		return usuarioDAO.obtenerUsuarioDesdeBaseDeDatosNombre(nomUsuario);	
		}
		
	//generar TOKEN
	public String generarTokenJWT(String usuarioId, String nombreUsuario, String rol) {//nuevo parametro 
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
	
	//FALTAN LOS MÉTODOS DE LOS FILTROS
	
	
	

}
