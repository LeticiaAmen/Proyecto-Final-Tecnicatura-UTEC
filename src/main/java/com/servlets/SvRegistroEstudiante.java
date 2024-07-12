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
import com.servicios.rest.LdapService;
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
	private LdapService ldapService;

	public SvRegistroEstudiante() {
		super();
		validacion = new Validacion();
		ldapService = new LdapService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Departamento> departamentos = departamentoService.obtenerTodosDepartamento();
		request.setAttribute("departamentos", departamentos);

		List<Localidad> localidades = localidadService.obtenerTodasLocalidades();
		request.setAttribute("localidades", localidades);

		List<Itr> itrs = itrService.obtenerItrsActivos();
		request.setAttribute("itrs", itrs);

		List<String> generosLista = Arrays.asList("Masculino", "Femenino", "Otros");
		request.setAttribute("generos", generosLista);

		List<String> semestresLista = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
		request.setAttribute("semestres", semestresLista);

		List<Generacion> generacionesLista = generacionService.obtenerGeneracionesTodas();
		request.setAttribute("generaciones", generacionesLista);

		request.getRequestDispatcher("/registroEstudiante.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formSubmitted = request.getParameter("formSubmitted");
		if ("true".equals(formSubmitted)) {
			String nomUsuario = request.getParameter("nomUsuario");
			String documento = request.getParameter("documento");

			// Limpiar el documento eliminando todos los caracteres no numéricos
			documento = documento.replaceAll("[^0-9]", "");
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

			String idDepartamento = request.getParameter("idDepartamento");
			List<Localidad> localidades = localidadService.obtenerLocalidadesPorDepartamento(Long.parseLong(idDepartamento));

			String idLocalidad = request.getParameter("idLocalidad");
			String idItr = request.getParameter("idItr");
			Long idDepartamentoLong = Long.parseLong(idDepartamento);
			Long idLocalidadLong = Long.parseLong(idLocalidad);
			Long idItrLong = Long.parseLong(idItr);
			Departamento departamento = departamentoService.obtenerPorId(idDepartamentoLong);
			Localidad localidad = localidadService.obtenerLocalidadPorId(idLocalidadLong);
			Itr itr = itrService.obtenerItr(idItrLong);

			String idGeneracion = request.getParameter("idGeneracion");
			Long idGeneracionLong = Long.parseLong(idGeneracion);
			Generacion generacion = generacionService.obtenerGeneracion(idGeneracionLong);

			String generoSeleccionado = request.getParameter("genero");
			char genero = generoSeleccionado.charAt(0);

			String semestreSeleccionado = request.getParameter("semestre");
			int semestreInt = Integer.parseInt(semestreSeleccionado);

			String fechaNacimientoStr = request.getParameter("fechaNacimiento");
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

			Date fechaNacimiento = null;
			try {
				fechaNacimiento = formato.parse(fechaNacimientoStr);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Error al convertir la fecha");
			}

			//validar si el nombre de usuario ya existe
			if(usuarioService.existeNombreUsuario(nomUsuario)) {
				setErrorAndReturn(request, response, "El nombre de usuario ya existe.", nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			// Validar si el correo ya existe
			if (usuarioService.existeCorreo(mail)) {
				setErrorAndReturn(request, response, "El mail ya se encuentra registrado.", nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}


			// Validar el formato del documento
			if (!validacion.validacionDocumento(documento)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionDocumento(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			// Validar si el documento ya existe
			if(usuarioService.existeDocumento(documentoLong)) {
				setErrorAndReturn(request, response, "El documento ya se encuentra registrado", nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			if (validacion.validacionNombre(nombre)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionNombre(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			if (validacion.validacionApellido(apellido)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionAepllido(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			if (validacion.validacionUsiario(nomUsuario, nombre, apellido)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionUsiario(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			// Validar las contraseñas
			if (contrasenia != null && !contrasenia.isEmpty()) {
				if (!contrasenia.equals(contrasenia)) {
					return;
				}

				if (!validacion.validacionContraseña(contrasenia)) {
					setErrorAndReturn(request, response, validacion.RespuestaValidacionMailEstudiantes(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
					return;
				}
			}

			if (!validacion.validacionMail(mail)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionMailEstudiantes(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}


			if (validacion.validacionMailEstudiantes(nomUsuario, mailInst)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionMailEstudiantes(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}



			if (validacion.validacionTelefono(telefono)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionTelefono(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			if (validacion.validacionEdad(fechaNacimiento)) {
				setErrorAndReturn(request, response, validacion.RespuestaValidacionEdad(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			ValidacionUsuario usuEstadoSinValidar = validacionService.obtenerValidacionUsuario(2);

			Estudiante estudiante = new Estudiante();

			estudiante.setNombreUsuario(nomUsuario);
			estudiante.setDocumento(documentoLong);
			estudiante.setApellidos(apellido);

			try {
				String salt = PasswordUtils.generateSalt();
				String hashedPassword = PasswordUtils.hashPassword(contrasenia, salt);

				estudiante.setSaltContraseña(salt);
				estudiante.setHashContraseña(hashedPassword);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				request.setAttribute("error", "Error al procesar la contraseña.");
				setErrorAndReturn(request, response, "Error al procesar la contraseña.", nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idGeneracion, semestreSeleccionado, fechaNacimientoStr);
				return;
			}

			estudiante.setMailInstitucional(mailInst);
			estudiante.setMail(mail);
			estudiante.setNombres(nombre);
			estudiante.setTelefono(telefono);
			estudiante.setValidacionUsuario(usuEstadoSinValidar);
			estudiante.setEstado(estadoService.obtenerEstadoId(1));
			estudiante.setLocalidad(localidad);
			estudiante.setItr(itr);
			estudiante.setGeneracion(generacion);
			estudiante.setGenero(genero);
			estudiante.setSemestre(semestreInt);
			estudiante.setFechaNacimiento(fechaNacimiento);

			try {               
				usuarioService.crearEstudiante(estudiante);
				request.getSession().setAttribute("registroExitoso", "Su usuario ha sido registrado. Por favor, espere a que un analista valide su cuenta.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error al crear Estudiante");
			}

			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} else {
			doGet(request, response);
		}
	}

	private void setErrorAndReturn(HttpServletRequest request, HttpServletResponse response, String errorMsg, String nombre, String apellido, String documento, String nomUsuario, String contrasenia, String mailInst, String mail, String telefono, String generoSeleccionado, String idDepartamento, String idLocalidad, String idItr, String idGeneracion, String semestreSeleccionado, String fechaNacimientoStr) throws ServletException, IOException {
		request.setAttribute("error", errorMsg);

		request.setAttribute("nombre", nombre);
		request.setAttribute("apellido", apellido);
		request.setAttribute("documento", documento);
		request.setAttribute("nomUsuario", nomUsuario);
		request.setAttribute("contrasenia", contrasenia);
		request.setAttribute("mailInst", mailInst);
		request.setAttribute("mail", mail);
		request.setAttribute("telefono", telefono);
		request.setAttribute("generoSeleccionado", generoSeleccionado);
		request.setAttribute("idDepartamento", idDepartamento);
		request.setAttribute("idLocalidad", idLocalidad);
		request.setAttribute("idItr", idItr);
		request.setAttribute("idGeneracion", idGeneracion);
		request.setAttribute("semestreSeleccionado", semestreSeleccionado);
		request.setAttribute("fechaNacimiento", fechaNacimientoStr);

		doGet(request, response);
		request.getRequestDispatcher("/registroEstudiante.jsp").forward(request, response);
	}
}
