package com.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entidades.Evento;
import com.entidades.Reclamo;
import com.servicios.EventoService;
import com.servicios.ReclamoService;
import com.servicios.RegistroAccionService;
import com.servicios.UsuarioService;


@WebServlet("/SvEditarReclamo")
public class SvEditarReclamo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ReclamoService reclamoService; 

	@EJB
	private EventoService eventoService;

	@EJB
	private UsuarioService usuarioService;

	@EJB
	private RegistroAccionService registroAccionService;


	public SvEditarReclamo() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idReclamoStr = request.getParameter("idReclamo");
	    if (idReclamoStr != null) {
	        try {
	            long idReclamo = Long.parseLong(idReclamoStr);
	            Reclamo reclamo = reclamoService.obtenerReclamo(idReclamo);
	            if (reclamo != null) {
	                request.setAttribute("reclamo", reclamo);
	                List<Evento> eventos = eventoService.obtenerEventosTodos();
	                request.setAttribute("eventos", eventos);
	                request.getRequestDispatcher("/editarReclamo.jsp").forward(request, response);
	            } else {
	                response.sendRedirect("listadoReclamos.jsp");
	            }
	        } catch (NumberFormatException e) {
	            response.sendRedirect("listadoReclamos.jsp");
	        }
	    } else {
	        response.sendRedirect("listadoReclamos.jsp");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String formSubmitted = request.getParameter("formSubmitted");
	    if ("true".equals(formSubmitted)) {
	        String idReclamoStr = request.getParameter("idReclamo");
	        long idReclamo = Long.parseLong(idReclamoStr);
	        
	        // Cargar el reclamo existente
	        Reclamo reclamo = reclamoService.obtenerReclamo(idReclamo);

	        // Actualizar datos del reclamo con la información del formulario
	        String titulo = request.getParameter("titulo");
	        String detalle = request.getParameter("detalle");
	        
	        // Establecer la fecha actual como la fecha del reclamo
	        Date fechaReclamo = new Date();  // Fecha actual

	        String idEvento = request.getParameter("idEvento");
	        Long idEventoLong = Long.parseLong(idEvento);
	        Evento evento = eventoService.obtenerEvento(idEventoLong);

	        // Actualiza los atributos del reclamo
	        reclamo.setTituloReclamo(titulo);
	        reclamo.setDetalle(detalle);
	        reclamo.setFechaHoraReclamo(fechaReclamo); // Usar la nueva fecha
	        reclamo.setEvento(evento);

	        try {
	            // Actualizar el reclamo en la base de datos
	            reclamoService.actualizarReclamo(reclamo);
	            request.getSession().setAttribute("mensaje", "Reclamo actualizado con éxito");
	            response.sendRedirect("SvListarReclamos");
	        } catch (Exception e) {
	            request.getSession().setAttribute("mensaje", "Error al actualizar el reclamo");
	            e.printStackTrace();
	            response.sendRedirect("editarReclamo.jsp?idReclamo=" + idReclamo); // En caso de error, redirige a editar de nuevo
	        }
	    } else {
	        // Si el formulario no ha sido enviado, redirige al doGet para cargar la página
	        doGet(request, response);
	    }
	}
}
