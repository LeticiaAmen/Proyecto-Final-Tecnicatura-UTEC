package com.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Usuario;
import com.servicios.UsuarioService;

/**
 * Servlet implementation class MenuUsuarioServlet
 */
@WebServlet("/MenuUsuario")
public class MenuUsuarioServlet extends HttpServlet {
    @EJB
    private UsuarioService usuarioService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");

        // Determinar si el usuario es analista
        boolean esAnalista = usuarioService.esAnalista(usuarioLogeado.getIdUsuario());
        
        // Guardar este booleano como un atributo de solicitud
        request.setAttribute("esAnalista", esAnalista);
        
        // Despachar al JSP
        request.getRequestDispatcher("/menuUsuario.jsp").forward(request, response);
    }
}
