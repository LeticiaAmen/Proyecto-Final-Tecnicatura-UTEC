package com.servlets;


import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entidades.Usuario;
import com.servicios.UsuarioService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioService usuarioService;
	public Usuario usuarioLogeado;

	public LoginServlet() {
		super();
		usuarioLogeado = new Usuario();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
        Usuario usuarioLogeado = (Usuario) sesion.getAttribute("usuario");

        if (usuarioLogeado != null) {
            // Actualizar la información del usuario si es necesario
            usuarioLogeado = usuarioService.obtenerUsuario(usuarioLogeado.getIdUsuario());
            sesion.setAttribute("usuario", usuarioLogeado);

            // Redirigir a ModificarDatosPersonalesServlet para cargar los datos
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarDatosPersonalesServlet");
            dispatcher.forward(request, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String nomUsuario = request.getParameter("nomUsuario");
	    String contrasenia = request.getParameter("psw");
	    boolean usuarioValido = usuarioService.validarUsuario(nomUsuario, contrasenia);

	    if (usuarioValido) {
	        Usuario usuarioLogeado = usuarioService.obtenerUsuarioDesdeBaseDeDatosNombre(nomUsuario);
	        if (usuarioLogeado != null && usuarioLogeado.getValidacionUsuario().getIdValidacion() == 1) {
	            HttpSession sesion = request.getSession();
	            sesion.setAttribute("usuario", usuarioLogeado);

	            String tipoUsuario = usuarioService.determinarTipoUsuario(usuarioLogeado);
	            String token = usuarioService.generarTokenJWT(String.valueOf(usuarioLogeado.getIdUsuario()), usuarioLogeado.getNombreUsuario(), tipoUsuario);

	            Cookie tokenCookie = new Cookie("Authorization", "Bearer " + token);
	            tokenCookie.setHttpOnly(true);
	            tokenCookie.setPath("/"); 
	            response.addCookie(tokenCookie);

	            // Convierte el tipo de usuario para el nombre del archivo JSP
	            String redirectPage = "menu" + formatPageName(tipoUsuario) + ".jsp";
	            response.sendRedirect(redirectPage);
	        } else {
	            request.setAttribute("error", "Usuario no activado o credenciales incorrectas.");
	            request.getRequestDispatcher("index.jsp").forward(request, response);
	        }
	    } else {
	        request.setAttribute("error", "Nombre de usuario o contraseña incorrectos");
	        request.getRequestDispatcher("index.jsp").forward(request, response);
	    }
	}
	
	//Primera letra en mayúscula.
	private String formatPageName(String tipoUsuario) {
	    if (tipoUsuario == null || tipoUsuario.isEmpty()) return "Error";
	    return tipoUsuario.substring(0, 1).toUpperCase() + tipoUsuario.substring(1).toLowerCase();
	}




}