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


			//Recuperar el id del estudiante de la sesion
			String idEstudianteStr = request.getParameter("idEstudiante");
			//Convertir el id a long
			long idEstudiante = 0;
			try {
				idEstudiante = Long.parseLong(idEstudianteStr);
			} catch (NumberFormatException e) {
				// Error al obtener el id del estudiante
				System.out.println("Error al obtener el id del usuario logueado");
				e.printStackTrace();
			}


			String titulo = request.getParameter("titulo");
			String detalle = request.getParameter("detalle");
			//obtener fecha string
			String fechaReclamoStr= request.getParameter("fechaReclamo");
			//formato de fecha
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			//convertir a java.util.date
			Date fechaReclamo = null; 
			try {
				fechaReclamo = formato.parse(fechaReclamoStr);
			}catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Error al convertir la fecha");
			}
			//obtener evento
			String idEvento = request.getParameter("idEvento");
			//cpnvertir a long
			Long idEventoLong = Long.parseLong(idEvento);
			//obtener entidad
			Evento evento =  eventoService.obtenerEvento(idEventoLong);
			Estudiante estudiante = (Estudiante) usuarioService.obtenerUsuario(idEstudiante);

			//se lo seteamos en 1 que es ingresado
			RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(1);

			Reclamo reclamo = new Reclamo();

			//setear atributos

			reclamo.setDetalle(detalle);
			reclamo.setTituloReclamo(titulo);
			reclamo.setEvento(evento);
			reclamo.setEstudiante(estudiante);
			reclamo.setFechaHoraReclamo(fechaReclamo);
			reclamo.setRegistroAccione(registroAccion);						
			
			try {
				reclamoService.crearReclamo(reclamo);
				request.getSession().setAttribute("ingreso exitoso", "Su reclamo ha sido ingresado con éxito");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error al ingresar el Reclamo");
			}			
			response.sendRedirect("listadoReclamos.jsp");
		}else {
			// Si el formulario no ha sido enviado, redirige al doGet para cargar la página
			doGet(request, response);
		}
	}
}
