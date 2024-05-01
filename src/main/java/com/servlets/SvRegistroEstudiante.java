package com.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Departamento;
import com.entidades.Estado;
import com.entidades.Estudiante;
import com.entidades.Generacion;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.entidades.ValidacionUsuario;
import com.servicios.DepartamentoService;
import com.servicios.EstadoService;
import com.servicios.GeneracionService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;

@WebServlet("/SvRegistroEstudiante")
public class SvRegistroEstudiante extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioService usuarioService; 

	@EJB
	private DepartamentoService departamentoService; 

	@EJB
	private LocalidadService localidadService; 

	@EJB
	private ValidacionUsuarioService validacionService; 

	@EJB
	private ItrService itrService;
	
	@EJB 
	private EstadoService estadoService; 
	
	@EJB
	private GeneracionService generacionService; 



	public SvRegistroEstudiante() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Departamento> departamentos = departamentoService.obtenerTodosDepartamento();
		request.setAttribute("departamentos", departamentos);

		List<Localidad> localidades = localidadService.obtenerTodasLocalidades();
		request.setAttribute("localidades", localidades);

		List<Itr> itrs = itrService.obtenerItrTodos();
		request.setAttribute("itrs", itrs);

		//Definimos las opciones ya que no tenemos enum ahora
		List<String> generosLista = Arrays.asList("Masculino", "Femenino", "Otros");
		request.setAttribute("generos", generosLista);

		//Definimos las opciones ya que no tenemos enum ahora
		List<String> semestresLista = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
		request.setAttribute("semestres", semestresLista);

		//Esta se la puse a mano porque ahora que es tabla hay que crear todo para generaciones
		List<Generacion> generacionesLista = generacionService.obtenerGeneracionesTodas();
		request.setAttribute("generaciones", generacionesLista);
		
		

		request.getRequestDispatcher("/registroEstudiante.jsp").forward(request, response);


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formSubmitted = request.getParameter("formSubmitted");
		if ("true".equals(formSubmitted)) {

			String nomUsuario = request.getParameter("nomUsuario");
			String documento = request.getParameter("documento");
			long documentoLong = 0;
			if (documento != null && !documento.isEmpty()) {
				documentoLong = Long.parseLong(documento);
			}

			String apellido = request.getParameter("apellido");
			String contrasenia = request.getParameter("contrasenia");

			//validación de formato de la contraseña
			if (contrasenia.length() < 8 || !contrasenia.matches(".*[A-Za-z].*") || !contrasenia.matches(".*[0-9].*")) {
				request.setAttribute("error", "La contraseña debe tener al menos 8 caracteres y contener letras y números.");
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
				return;
			}

			String mailInst = request.getParameter("mailInst");
			String mail = request.getParameter("mail");

			//Validación del formato del mail
			if (!mail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}") || 
					!mailInst.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")) {
				request.setAttribute("error", "El formato del correo electrónico no es válido.");
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
				return;
			}

			String nombre = request.getParameter("nombre");
			String telefono = request.getParameter("telefono");

			// ---------- obtener departamento, localidad e ITR seleccionado
			String idDepartamento = request.getParameter("idDepartamento");
			String idLocalidad = request.getParameter("idLocalidad");
			String idItr = request.getParameter("idItr");
					
			

			// Convertir a Long 
			Long idDepartamentoLong = Long.parseLong(idDepartamento);
			Long idLocalidadLong = Long.parseLong(idLocalidad);
			Long idItrLong = Long.parseLong(idItr);	
			

			//Obtener las entidades
			Departamento departamento = departamentoService.obtenerPorId(idDepartamentoLong);
			Localidad localidad = localidadService.obtenerLocalidadPorId(idLocalidadLong);
			Itr itr = itrService.obtenerItr(idItrLong);
			
			//obtener y convertir generacion
			String idGeneracion = request.getParameter("idGeneracion");
			Long idGeneracionLong = Long.parseLong(idGeneracion);
			Generacion generacion = generacionService.obtenerGeneracion(idGeneracionLong);
			
			

			//obtener el genero seleccionado
			String generoSeleccionado = request.getParameter("genero");

			//Tomar solo el primer caracter
			char genero = generoSeleccionado.charAt(0); 


			//obtener semestre seleccionado
			String semestreSeleccionado = request.getParameter("semestre");
			//convierto el semestre en int
			int semestreInt = Integer.parseInt(semestreSeleccionado);

			//Obtener fecha String
			String fechaNacimientoStr = request.getParameter("fechaNacimiento");
			//formato de fecha
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

			// Convertir a java.util.Date
			Date fechaNacimiento = null;
			try {
				fechaNacimiento = formato.parse(fechaNacimientoStr);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Error al convertir la fecha");
			}
			//-------------------------------------------------------------------------------------------

			ValidacionUsuario usuEstadoSinValidar = validacionService.obtenerValidacionUsuario(2);
			//-------------------------------------------------------------------------------------------   

			Estudiante estudiante = new Estudiante();




			//setear atributos
			estudiante.setNombreUsuario(nomUsuario);
			estudiante.setDocumento(documentoLong);
			estudiante.setApellidos(apellido);
			estudiante.setHashContraseña(contrasenia);
			estudiante.setMailInstitucional(mailInst);
			estudiante.setMail(mail);
			estudiante.setNombres(nombre);
			estudiante.setTelefono(telefono);

			//setear sin validar
			estudiante.setValidacionUsuario(usuEstadoSinValidar);
			
			//setear estado activo
			Estado estadoActivo = estadoService.obtenerEstadoId(1);
			estudiante.setEstado(estadoActivo);
			

			//setear y localidad y itr
			estudiante.setLocalidad(localidad);
			estudiante.setItr(itr);
			
			//setear generacion
			estudiante.setGeneracion(generacion);

			//setear genero 
			estudiante.setGenero(genero);
			//setear semestre
			estudiante.setSemestre(semestreInt);


			// Establecer la fecha de nacimiento en el usuario
			estudiante.setFechaNacimiento(fechaNacimiento);

			try {
				usuarioService.crearEstudiante(estudiante);
				// Agregar mensaje de éxito a la sesión
				request.getSession().setAttribute("registroExitoso", "Su usuario ha sido registrado. Por favor, espere a que un analista valide su cuenta.");


			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error al crear Estudiante");
			}
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}else {
			// Si el formulario no ha sido enviado, redirige al doGet para cargar la página
			doGet(request, response);
		}
	}
}
