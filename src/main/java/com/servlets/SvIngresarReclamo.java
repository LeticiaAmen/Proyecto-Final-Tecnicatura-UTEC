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

import com.entidades.Estudiante;
import com.entidades.Evento;
import com.entidades.Reclamo;
import com.entidades.RegistroAccione;
import com.servicios.EventoService;
import com.servicios.ReclamoService;
import com.servicios.RegistroAccionService;
import com.servicios.UsuarioService;


@WebServlet("/SvIngresarReclamo")
public class SvIngresarReclamo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ReclamoService reclamoService; 

	@EJB
	private EventoService eventoService;

	@EJB
	private UsuarioService usuarioService;

	@EJB
	private RegistroAccionService registroAccionService;


	public SvIngresarReclamo() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Lista de eventos para asociar al select
		List<Evento> eventos = eventoService.obtenerEventosTodos();
		request.setAttribute("eventos", eventos);


		request.getRequestDispatcher("/registroReclamo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String formSubmitted = request.getParameter("formSubmitted");
	    if ("true".equals(formSubmitted)) {
	        //Recuperar el id del estudiante de la sesión
	        String idEstudianteStr = request.getParameter("idEstudiante");
	        long idEstudiante = Long.parseLong(idEstudianteStr);

	        String titulo = request.getParameter("titulo");
	        String detalle = request.getParameter("detalle");

	        // Obtener la fecha actual en el servidor para el registro del reclamo
	        Date fechaReclamo = new Date();

	        // Obtener el ID del evento seleccionado en el formulario
	        String idEventoStr = request.getParameter("idEvento");
	        Long idEvento = Long.parseLong(idEventoStr);
	        Evento evento = eventoService.obtenerEvento(idEvento);

	        // Obtener el estudiante de la base de datos usando el ID almacenado en la sesión
	        Estudiante estudiante = (Estudiante) usuarioService.obtenerUsuario(idEstudiante);

	        // Se lo setea en 1 que es el estado 'Ingresado'
	        RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(1);

	        // Crear el nuevo reclamo con la información obtenida y completada
	        Reclamo reclamo = new Reclamo();
	        reclamo.setTituloReclamo(titulo);
	        reclamo.setDetalle(detalle);
	        reclamo.setFechaHoraReclamo(fechaReclamo);
	        reclamo.setEvento(evento);
	        reclamo.setEstudiante(estudiante);
	        reclamo.setRegistroAccione(registroAccion);

	        try {
	            reclamoService.crearReclamo(reclamo);
	            // Atributo de sesión para mostrar un mensaje de éxito en la interfaz de usuario
	            request.getSession().setAttribute("ingresoExitoso", "Su reclamo ha sido ingresado con éxito.");
	            response.sendRedirect("SvListarReclamos");
	        } catch (Exception e) {
	            // Si ocurre un error, se imprime en consola y se puede redirigir a una página de error
	            e.printStackTrace();
	            response.sendRedirect("error.jsp"); 
	        }
	    } else {
	        // Si no se envió el formulario, recargar la página de registro de reclamo
	        doGet(request, response);
	    }
	}

}
