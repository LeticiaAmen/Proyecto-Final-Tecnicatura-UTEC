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
import com.validaciones.Validacion;

import com.util.PasswordUtils;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

	private Validacion validacion;



	public SvRegistroEstudiante() {
		super();
		validacion = new Validacion();
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
			String mailInst = request.getParameter("mailInst");
			String mail = request.getParameter("mail");
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
			//Validacion Nombre
			if (validacion.validacionNombre(nombre)) {
				request.setAttribute("error", validacion.RespuestaValidacionNombre());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}

			//Validacion Apellido
			if (validacion.validacionApellido(apellido)) {
				request.setAttribute("error", validacion.RespuestaValidacionAepllido());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}
			//Validación del documento
			if (!validacion.validacionDocumento(documento)) {
				request.setAttribute("error", validacion.RespuestaValidacionDocumento());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}


			// validacion Nombre de Usuario
			if (validacion.validacionUsiario(nomUsuario, nombre, apellido)) {
				request.setAttribute("error", validacion.RespuestaValidacionUsiario());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}
			//validación de formato de la contraseña
			if (validacion.validacionContraseña(contrasenia)) {
				request.setAttribute("error", validacion.RespuestaValidacionContraseña());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}
			//Validación del formato del mailInstitucional
			if (validacion.validacionMailEstudiantes(nomUsuario, mailInst)) {
				request.setAttribute("error", validacion.RespuestaValidacionMailEstudiantes());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}
			//Validación del formato del mail
			if (validacion.validacionMail(mail)) {
				request.setAttribute("error", validacion.RespuestaValidacionMail());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}

			//Validación del formato del telefono
			if (validacion.validacionTelefono(telefono)) {
				request.setAttribute("error", validacion.RespuestaValidacionTelefono());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}
			//Validación del fecha nacimiento
			if (validacion.validacionEdad(fechaNacimiento)) {
				request.setAttribute("error", validacion.RespuestaValidacionEdad());
				doGet(request, response);  // Cargar los datos necesarios
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}



			//-------------------------------------------------------------------------------------------


			ValidacionUsuario usuEstadoSinValidar = validacionService.obtenerValidacionUsuario(2);
			//-------------------------------------------------------------------------------------------   


			//crear el estudiante y procesar la contraseña

			Estudiante estudiante = new Estudiante();


			//setear atributos
			estudiante.setNombreUsuario(nomUsuario);
			estudiante.setDocumento(documentoLong);
			estudiante.setApellidos(apellido);


			try {
				// Generar salt y hash para la contraseña
				String salt = PasswordUtils.generateSalt();
				String hashedPassword = PasswordUtils.hashPassword(contrasenia, salt);

				estudiante.setSaltContraseña(salt);
				estudiante.setHashContraseña(hashedPassword);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				request.setAttribute("error", "Error al procesar la contraseña.");
				doGet(request, response);
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}


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
				request.setAttribute("error", "Error al crear el usuario.");
				doGet(request, response);
				request.getRequestDispatcher("registroEstudiante.jsp").forward(request, response);
				return;
			}
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}else {
			// Si el formulario no ha sido enviado, redirige al doGet para cargar la página
			doGet(request, response);
		}
	}
}
