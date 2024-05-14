package com.servlets;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entidades.Analista;
import com.entidades.Estudiante;
import com.entidades.Tutor;
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
            // Actualizar la información del usuario
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

        try {
            boolean usuarioValido = usuarioService.validarUsuario(nomUsuario, contrasenia);

            if (usuarioValido) {
                Usuario usuarioLogeado = usuarioService.obtenerUsuarioDesdeBaseDeDatosNombre(nomUsuario);

                if (usuarioLogeado != null && usuarioLogeado.getValidacionUsuario().getIdValidacion() == 1) {
                    boolean estadoActivo = true;
                    if (usuarioLogeado instanceof Estudiante) {
                        estadoActivo = ((Estudiante) usuarioLogeado).getEstado().getIdEstado() != 2;
                    } else if (usuarioLogeado instanceof Tutor) {
                        estadoActivo = ((Tutor) usuarioLogeado).getEstado().getIdEstado() != 2;
                    } else if (usuarioLogeado instanceof Analista) {
                        estadoActivo = ((Analista) usuarioLogeado).getEstado().getIdEstado() != 2;
                        HttpSession sesion = request.getSession();
                        sesion.setAttribute("esAnalista", usuarioLogeado instanceof Analista);
                    }

                    if (estadoActivo) {
                        HttpSession sesion = request.getSession();
                        sesion.setAttribute("usuario", usuarioLogeado);

                        String tipoUsuario = usuarioService.determinarTipoUsuario(usuarioLogeado);
                        String token = usuarioService.generarTokenJWT(String.valueOf(usuarioLogeado.getIdUsuario()), usuarioLogeado.getNombreUsuario(), tipoUsuario);

                        // Aquí imprimimos el token generado en la consola del servidor
                        System.out.println("Generated JWT Token: " + token);

                        // Configura el token en una cookie segura y HttpOnly
                        Cookie authCookie = new Cookie("Authorization", "Bearer " + token);
                        authCookie.setHttpOnly(true);
                        authCookie.setSecure(true); // Asegúrate de que solo se envíe con HTTPS
                        authCookie.setPath("/");
                        response.addCookie(authCookie);

                        // Redirección
                        String redirectPage = "menu" + formatPageName(tipoUsuario) + ".jsp";
                        response.sendRedirect(redirectPage);
                    } else {
                        request.setAttribute("error", "Su usuario está inactivo.");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "Su usuario aún no ha sido activado.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Nombre de usuario o contraseña incorrectos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos al validar el usuario.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error de seguridad al validar la contraseña.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }


	private String formatPageName(String tipoUsuario) {
	    if (tipoUsuario == null || tipoUsuario.isEmpty()) return "Error";
	    return tipoUsuario.substring(0, 1).toUpperCase() + tipoUsuario.substring(1).toLowerCase();
	}




}
