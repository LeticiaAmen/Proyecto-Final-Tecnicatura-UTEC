package com.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entidades.Usuario;
import com.servicios.UsuarioService;
import com.util.PasswordUtils;
import com.validaciones.Validacion;


@WebServlet("/cambiarContrasenia")
public class SvCambiarContrasenia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvCambiarContrasenia() {
		super();
	}

	@EJB 
	private UsuarioService usuarioService; 


	private Validacion validacion = new Validacion();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Redirigir a la página cambiarContrasenia.jsp
		request.getRequestDispatcher("/cambiarContrasenia.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		Long userId = Long.parseLong(request.getParameter("userId"));
		String contraseniaActual = request.getParameter("contraseniaActual");
		String nuevaContrasenia = request.getParameter("nuevaContrasenia");
		String confirmarContrasenia = request.getParameter("confirmarContrasenia");

		Usuario usuario = usuarioService.obtenerUsuario(userId);

		if (usuario != null) {
			try {
				//verificar la contraseña actual
				if(!PasswordUtils.verifyPassword(contraseniaActual, usuario.getSaltContraseña(), usuario.getHashContraseña())) {
					response.sendRedirect("cambiarContrasenia.jsp?id=" + userId +  "&mensajeError=" + java.net.URLEncoder.encode("Contraseña actual incorrecta", "UTF-8"));
					return;
				}
				// Verificar la nueva contraseña y su confirmación 
				if(!nuevaContrasenia.equals(confirmarContrasenia)) {
					response.sendRedirect("cambiarContrasenia.jsp?id=" + userId + "&mensajeError=" + java.net.URLEncoder.encode("Las contraseñas no coinciden", "UTF-8"));
					return;
				}
				if(!validacion.validacionContraseña(nuevaContrasenia)) {
					response.sendRedirect("cambiarContrasenia.jsp?id=" + userId + "&mensajeError=" + java.net.URLEncoder.encode(validacion.RespuestaValidacionContraseña(), "UTF-8"));
					return;
				}

				// Generar nuevo salt y hash para la nueva contraseña
				String nuevoSalt = PasswordUtils.generateSalt();
				String nuevoHashContrasenia = PasswordUtils.hashPassword(nuevaContrasenia, nuevoSalt);

				// Actualizar el salt y el hash de la contraseña en el usuario
				usuario.setSaltContraseña(nuevoSalt);
				usuario.setHashContraseña(nuevoHashContrasenia);
				usuarioService.actualizarUsuario(usuario);

				 response.sendRedirect("datosPersonales?id=" + userId + "&mensajeExito=" + java.net.URLEncoder.encode("Contraseña cambiada correctamente", "UTF-8"));
			}catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				response.sendRedirect("cambiarContrasenia.jsp?id=" + userId + "&mensajeError=" + java.net.URLEncoder.encode("Error al procesar la contraseña", "UTF-8"));
			}
		}else {
			response.sendRedirect("cambiarContrasenia.jsp?id=" + userId + "&mensajeError=Usuario no encontrado");
		}
	}
}
