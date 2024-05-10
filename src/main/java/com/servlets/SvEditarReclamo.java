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
	                // Manejar error: reclamo no encontrado
	                response.sendRedirect("listadoReclamos.jsp");
	            }
	        } catch (NumberFormatException e) {
	            // Manejar error: ID no es un número
	            response.sendRedirect("listadoReclamos.jsp");
	        }
	    } else {
	        // Manejar error: ID de reclamo no proporcionado
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
	    String fechaReclamoStr = request.getParameter("fechaReclamo");
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    Date fechaReclamo = null;

	    try {
	        fechaReclamo = formato.parse(fechaReclamoStr);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        System.out.println("Error al convertir la fecha");
	    }

	    String idEvento = request.getParameter("idEvento");
	    Long idEventoLong = Long.parseLong(idEvento);
	    Evento evento = eventoService.obtenerEvento(idEventoLong);

	    // Actualiza los atributos del reclamo
	    reclamo.setTituloReclamo(titulo);
	    reclamo.setDetalle(detalle);
	    reclamo.setFechaHoraReclamo(fechaReclamo);
	    reclamo.setEvento(evento);

	    try {
	        // Actualizar el reclamo en la base de datos
	        reclamoService.actualizarReclamo(reclamo);
	        request.getSession().setAttribute("mensaje", "Reclamo actualizado con éxito");
	    } catch (Exception e) {
	        request.getSession().setAttribute("mensaje", "Error al actualizar el reclamo");
	        e.printStackTrace();
	    }

	    // Redirige al listado de reclamos o a la página de detalles del reclamo
	    response.sendRedirect("SvListarReclamos");
	} else {
	    // Si el formulario no ha sido enviado, redirige al doGet para cargar la página
	    doGet(request, response);
		}
	}
}
