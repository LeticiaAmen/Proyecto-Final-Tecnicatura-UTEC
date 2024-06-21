package com.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Evento;
import com.entidades.Justificacion;
import com.servicios.EventoService;
import com.servicios.JustificacionService;


@WebServlet("/SvEditarJustificacion")
public class SvEditarJustificacion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private JustificacionService justificacionService; 

	@EJB
	private EventoService eventoService;


	public SvEditarJustificacion() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idJustificacionStr = request.getParameter("idJustificacion");
		if(idJustificacionStr != null) {
			try {
				long idJustificacion = Long.parseLong(idJustificacionStr);
				Justificacion justificacion = justificacionService.obtenerJustificacion(idJustificacion);
				if(justificacion != null ) {
					request.setAttribute("justificacion", justificacion);
					List<Evento> eventos = eventoService.obtenerEventosTodos();
					request.setAttribute("eventos", eventos);
					request.getRequestDispatcher("/editarJustificacion.jsp").forward(request, response);
				}else {
					response.sendRedirect("SvListarJustificaciones");
				}
			}catch(NumberFormatException e) {
				response.sendRedirect("SvListarJustificaciones");
			}
		} else {
			response.sendRedirect("SvListarJustificaciones");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formSubmitted = request.getParameter("formSubmitted");
		if("true".equals(formSubmitted)) {
			String idJustificacionStr = request.getParameter("idJustificacion");
			long idJustificacion = Long.parseLong(idJustificacionStr);
			
			Justificacion justificacion = justificacionService.obtenerJustificacion(idJustificacion);
			
//			String titulo = request.getParameter("titulo");
			String detalle = request.getParameter("detalle");
			
			Date fechaJustificacion = new Date();
			
			String idEvento = request.getParameter("idEvento");
			Long idEventoLong = Long.parseLong(idEvento);
            Evento evento = eventoService.obtenerEvento(idEventoLong);
            
            justificacion.setDetalle(detalle);
            justificacion.setFechaHora(fechaJustificacion);
            justificacion.setEvento(evento);
            
            try {
            	justificacionService.actualizarJustificacion(justificacion);
            	request.getSession().setAttribute("mensaje", "Justificación actualizada con éxito");
            	response.sendRedirect("SvListarJustificaciones");
            } catch (Exception e) {
            	 request.getSession().setAttribute("mensaje", "Error al actualizar la justificación");
            	 e.printStackTrace();
            	 response.sendRedirect("editarJustificacion.jsp?idJustificacion=" + idJustificacion);
            }
		} else {
			doGet(request, response);
		}
		
	}

}
