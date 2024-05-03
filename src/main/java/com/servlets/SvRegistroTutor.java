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

import com.entidades.Area;
import com.entidades.Departamento;
import com.entidades.Estado;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.entidades.Rol;
import com.entidades.Tutor;
import com.entidades.ValidacionUsuario;
import com.servicios.AreaService;
import com.servicios.DepartamentoService;
import com.servicios.EstadoService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.RolService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;


@WebServlet("/SvRegistroTutor")
public class SvRegistroTutor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioService usuarioService;

	@EJB
	private DepartamentoService departamentoService; 

	@EJB
	private LocalidadService localidadService; 

	@EJB
	ValidacionUsuarioService validacionService; 

	@EJB
	private ItrService itrService; 
	
	@EJB 
	private EstadoService estadoService; 
	
	@EJB
	private AreaService areaService; 
	
	@EJB
	private RolService rolService;


	public SvRegistroTutor() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Departamento> departamentos = departamentoService.obtenerTodosDepartamento();
		request.setAttribute("departamentos", departamentos);

		List<Localidad> localidades = localidadService.obtenerTodasLocalidades();
		request.setAttribute("localidades", localidades);

		List<Itr> itrs = itrService.obtenerItrTodos();
		request.setAttribute("itrs", itrs);
		
		List<Area> areasLista = areaService.obtenerAreaTodas();
		request.setAttribute("areas", areasLista);
		
		List<Rol> roles = rolService.obtenerRolTodos();
		request.setAttribute("roles", roles);
		

		//Definimos las opciones ya que no tenemos enum ahora
		List<String> generosLista = Arrays.asList("Masculino", "Femenino", "Otros");
		request.setAttribute("generos", generosLista);


		request.getRequestDispatcher("/registroTutor.jsp").forward(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//-------------------------------------------------------------------------------------------      
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

				// ---------- obtener departamento y localidad e itr
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
				
				//Area y rol
				String idArea = request.getParameter("idArea");
				String idRol = request.getParameter("idRol");
				Long idAreaLong = Long.parseLong(idArea);
				Long idRolLong = Long.parseLong(idRol);
				Area area = areaService.obtenerArea(idAreaLong);
				Rol rol = rolService.obtenerRol(idRolLong);
				
				

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
			    Tutor tutor = new Tutor();
					
					
				//setear atributos
			    tutor.setNombreUsuario(nomUsuario);
			    tutor.setDocumento(documentoLong);
			    tutor.setApellidos(apellido);
			    tutor.setHashContraseña(contrasenia);
			    tutor.setMailInstitucional(mailInst);
			    tutor.setMail(mail);
			    tutor.setNombres(nombre);
			    tutor.setTelefono(telefono);
			    //setear sin validar
				tutor.setValidacionUsuario(usuEstadoSinValidar);
				
				//setear estado activo
				Estado estadoActivo = estadoService.obtenerEstadoId(1);
				tutor.setEstado(estadoActivo);
				
					
			    //setear y localidad e itr
			    tutor.setLocalidad(localidad);
			    tutor.setItr(itr);
			    			    
			    //setear genero 
			    tutor.setGenero(genero);
			    //setear  area
			    tutor.setArea(area);
			    //setear rol
			    tutor.setRol(rol);
				
			    
			    
			    // Establecer la fecha de nacimiento en el usuario
			    tutor.setFechaNacimiento(fechaNacimiento);

				try {
					usuarioService.crearTutor(tutor);
					// Agregar mensaje de éxito a la sesión
				    request.getSession().setAttribute("registroExitoso", "Su usuario ha sido registrado. Por favor, espere a que un analista valide su cuenta.");


				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("Error al crear el Tutor");
				}
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}else {
		        // Si el formulario no ha sido enviado, redirige al doGet para cargar la página
		        doGet(request, response);
		    }
		}
		}
			