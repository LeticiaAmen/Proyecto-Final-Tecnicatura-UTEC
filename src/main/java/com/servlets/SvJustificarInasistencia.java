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

import com.entidades.Estado;
import com.entidades.Estudiante;
import com.entidades.Evento;
import com.entidades.Justificacion;
import com.entidades.RegistroAccione;
import com.servicios.EstadoService;
import com.servicios.EventoService;
import com.servicios.JustificacionService;
import com.servicios.RegistroAccionService;
import com.servicios.UsuarioService;


@WebServlet("/SvJustificarInasistencia")
public class SvJustificarInasistencia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private EventoService eventoService;
	@EJB
	private UsuarioService usuarioService;
	@EJB
	private EstadoService estadoService;
	@EJB
	private JustificacionService justificacionService;
	@EJB
	private RegistroAccionService registroAccionService;

	public SvJustificarInasistencia() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lista de eventos para asociar al select
		List<Evento> eventos = eventoService.obtenerEventosTodos();
		request.setAttribute("eventos", eventos);
		String backUrl = "SvListarJustificaciones"; // URL por defecto
		 request.setAttribute("backUrl", backUrl); // Enviamos la URL correcta al JSP

		request.getRequestDispatcher("justificarInasistencia.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formSubmitted = request.getParameter("formSubmitted");
		if ("true".equals(formSubmitted)) {
			//Recuperar el id del estudiante de la sesión
			String idEstudianteStr = request.getParameter("idEstudiante");
			long idEstudiante = Long.parseLong(idEstudianteStr);

			String detalle = request.getParameter("detalle");
			// Obtener la fecha actual en el servidor para el registro del reclamo
			Date fechaJustificacion = new Date();

			// Obtener el ID del evento seleccionado en el formulario
			String idEventoStr = request.getParameter("idEvento");
			Long idEvento = Long.parseLong(idEventoStr);
			Evento evento = eventoService.obtenerEvento(idEvento);

			// Obtener el estudiante de la base de datos usando el ID almacenado en la sesión
			Estudiante estudiante = (Estudiante) usuarioService.obtenerUsuario(idEstudiante);

			  // Se lo setea en 1 que es el estado 'Ingresado'
	        RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(1);		
			
			//obtener estado
			Estado estadoActivo = estadoService.obtenerEstadoId(1);

			//crear la justificacion
			Justificacion justificacion = new Justificacion();
			justificacion.setDetalle(detalle);
			justificacion.setFechaHora(fechaJustificacion);
			justificacion.setEvento(evento);
			justificacion.setEstudiante(estudiante);
			justificacion.setEstado(estadoActivo);
			justificacion.setRegistroAccione(registroAccion);

			try {
				justificacionService.crearJustificacion(justificacion);
				// Atributo de sesión para mostrar un mensaje de éxito en la interfaz de usuario
				request.getSession().setAttribute("ingresoExitoso", "Su justificacion ha sido enviada con éxito.");
				response.sendRedirect("menuEstudiante.jsp");

			}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("menuEstudiante.jsp");
			}
		} else {
			// Si no se envió el formulario, recargar la página de registro de reclamo
			doGet(request, response);
		}
	}

}
