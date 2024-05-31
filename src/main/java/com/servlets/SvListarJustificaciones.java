package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Justificacion;
import com.entidades.RegistroAccione;
import com.entidades.Usuario;
import com.servicios.JustificacionService;
import com.servicios.RegistroAccionService;
import com.servicios.UsuarioService;


@WebServlet("/SvListarJustificaciones")
public class SvListarJustificaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	   @EJB
	    private UsuarioService usuarioService;
	    
	    @EJB
	    private RegistroAccionService registroAccionService;
	    @EJB
	    private JustificacionService justificacionService;
	
    public SvListarJustificaciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
        boolean esAnalista = usuarioService.esAnalista(usuarioLogeado.getIdUsuario());
        String filtroUsuario = request.getParameter("filtroUsuario");
        String estadoJustificacion = request.getParameter("estadoJustificacion");
        List<Justificacion> justificaciones;
        String backUrl = "menuEstudiante.jsp"; // URL por defecto
        
        List<RegistroAccione> estadosActivos = registroAccionService.obtenerEstadosActivos(); 
        request.setAttribute("estadosActivos", estadosActivos);
        
        if (usuarioService.esAnalista(usuarioLogeado.getIdUsuario())) {
            justificaciones = justificacionService.obtenerJustificacionesConFiltros(filtroUsuario, estadoJustificacion);
            backUrl = "menuAnalista.jsp"; // cambiamos la URL si es analista
        } else {
            justificaciones = justificacionService.obtenerJustificacionesPorUsuarioConFiltros(usuarioLogeado.getIdUsuario(), filtroUsuario, estadoJustificacion);
        }
        request.setAttribute("esAnalista", esAnalista);
        request.setAttribute("justificaciones", justificaciones);
        request.setAttribute("backUrl", backUrl); // Enviamos la URL correcta al JSP
        request.getRequestDispatcher("/listadoJustificaciones.jsp").forward(request, response);
    }
}

