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

import com.entidades.Analista;
import com.entidades.Departamento;
import com.entidades.Estado;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.entidades.Usuario;
import com.entidades.ValidacionUsuario;

import com.servicios.DepartamentoService;
import com.servicios.EstadoService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;
import com.validaciones.Validacion;

@WebServlet("/SvRegistroAnalista")
public class SvRegistroAnalista extends HttpServlet {
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
	
	private Validacion validacion;
	
	
    public SvRegistroAnalista() {
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
		
		request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
		
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
		//Validacion Nombre
		if (validacion.validacionNombre(nombre)) {
			request.setAttribute("error", validacion.RespuestaValidacionNombre());
			doGet(request, response);  // Cargar los datos necesarios
			request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
			return;
		}
		
		//Validacion Apellido
		if (validacion.validacionApellido(apellido)) {
			request.setAttribute("error", validacion.RespuestaValidacionAepllido());
			doGet(request, response);  // Cargar los datos necesarios
			request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
			return;
		}
		
		
		// validacion Nombre de Usuario
		if (validacion.validacionUsiario(nomUsuario, nombre, apellido)) {
			request.setAttribute("error", validacion.RespuestaValidacionUsiario());
			doGet(request, response);  // Cargar los datos necesarios
			request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
			return;
		}
		//validación de formato de la contraseña
		if (validacion.validacionContraseña(contrasenia)) {
			request.setAttribute("error", validacion.RespuestaValidacionContraseña());
			doGet(request, response);  // Cargar los datos necesarios
			request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
			return;
		}
	
		//Validación del formato del mail
		if (validacion.validacionMail(mail)) {
			request.setAttribute("error", validacion.RespuestaValidacionMail());
			doGet(request, response);  // Cargar los datos necesarios
			request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
			return;
		}
		//Validación del formato del telefono
		if (validacion.validacionTelefono(telefono)) {
			request.setAttribute("error", validacion.RespuestaValidacionTelefono());
			doGet(request, response);  // Cargar los datos necesarios
			request.getRequestDispatcher("/registroAnalista.jsp").forward(request, response);
			return;
		}
		
		
		

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
		
	    //obtener el genero seleccionado
		String generoSeleccionado = request.getParameter("genero");
		
		//Tomar solo el primer caracter
		char genero = generoSeleccionado.charAt(0); 
	    
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

       ValidacionUsuario usuEstadoSinValidar = validacionService.obtenerValidacionUsuario(2);
		
		Analista usuario = new Analista();

		//setear atributos
		usuario.setNombreUsuario(nomUsuario);
		usuario.setDocumento(documentoLong);
		usuario.setApellidos(apellido);
		usuario.setHashContraseña(contrasenia);
		usuario.setMailInstitucional(mailInst);
		usuario.setMail(mail);
		usuario.setNombres(nombre);
		usuario.setTelefono(telefono);
		
		//setear sin validar
		usuario.setValidacionUsuario(usuEstadoSinValidar);
				
	    // localidad e itr
		usuario.setLocalidad(localidad);
		usuario.setItr(itr);
	    
	    //setear genero
		usuario.setGenero(genero);
	    
	    // Establecer la fecha de nacimiento en el usuario
		usuario.setFechaNacimiento(fechaNacimiento);
		
		//setear estado
		Estado estadoActivo = estadoService.obtenerEstadoId(1);
		usuario.setEstado(estadoActivo);

		try {
			usuarioService.crearAnalista((Analista)usuario);
			// Agregar mensaje de éxito a la sesión
			request.getSession().setAttribute("registroExitoso", "Su usuario ha sido registrado. Por favor, espere a que un analista valide su cuenta.");

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error al crear Analista");
		}
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}else {
        // Si el formulario no ha sido enviado, redirige al doGet para cargar la página
        doGet(request, response);
    }
}

}
