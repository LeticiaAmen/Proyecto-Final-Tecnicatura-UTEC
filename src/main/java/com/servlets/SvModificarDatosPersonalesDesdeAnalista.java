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
import javax.servlet.http.HttpSession;

import com.entidades.Analista;
import com.entidades.Area;
import com.entidades.Departamento;
import com.entidades.Estado;
import com.entidades.Estudiante;
import com.entidades.Generacion;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.entidades.Rol;
import com.entidades.Tutor;
import com.entidades.Usuario;
import com.entidades.ValidacionUsuario;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;
import com.servicios.AreaService;
import com.servicios.DepartamentoService;
import com.servicios.EstadoService;
import com.servicios.GeneracionService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.RolService;
import com.validaciones.Validacion;

@WebServlet("/datosPersonalesUsuario")
public class SvModificarDatosPersonalesDesdeAnalista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioService usuarioService;

	@EJB
	private DepartamentoService departamentoService;

	@EJB
	private LocalidadService localidadService;

	@EJB
	private ItrService itrService;

	@EJB
	private GeneracionService generacionService;

	@EJB
	private RolService rolService;

	@EJB
	private AreaService areaService;

	@EJB
	private ValidacionUsuarioService validacionUsuarioService;
	
	@EJB
	private EstadoService usuarioEstado;
	
	private Validacion validacion = new Validacion();

	public SvModificarDatosPersonalesDesdeAnalista() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userIdParam = request.getParameter("id");

		if (userIdParam != null && !userIdParam.isEmpty()) {
			Long userId = Long.parseLong(userIdParam);
			Usuario usuario = usuarioService.obtenerUsuario(userId);

			if (usuario != null) {
				request.setAttribute("usuarioAEditar", usuario);
				request.setAttribute("idItrUsuario", usuario.getItr().getIdItr());
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				String fechaNacimientoStr = formatoFecha.format(usuario.getFechaNacimiento());
				request.setAttribute("fechaNacimientoStr", fechaNacimientoStr);
				char generoActual = usuario.getGenero();
				request.setAttribute("generoActual", String.valueOf(generoActual));

				// Verificar si el usuario es un estudiante
				boolean isStudent = usuario instanceof Estudiante;
				request.setAttribute("isStudent", isStudent);

				// Verificar si el usuario es un tutor
				boolean isTutor = usuario instanceof Tutor;
				request.setAttribute("isTutor", isTutor);

				// Verificar si el usuario es un analista
				boolean isAnalista = usuario instanceof Analista;
				request.setAttribute("isAnalista", isAnalista);

				if (isAnalista) {
					Analista analista = (Analista) usuario;
					request.setAttribute("mailInstitucional", analista.getMailInstitucional());
				}

				if (isStudent) {
					Estudiante estudiante = (Estudiante) usuario;
					request.setAttribute("mailInstitucional", estudiante.getMailInstitucional());
					request.setAttribute("semestreActual", estudiante.getSemestre());
					request.setAttribute("generacionActual",
							estudiante.getGeneracion() != null ? estudiante.getGeneracion().getIdGeneracion() : null);
					List<Generacion> generaciones = generacionService.obtenerGeneracionesTodas();
					request.setAttribute("generaciones", generaciones);
				}

				if (isTutor) {
					Tutor tutor = (Tutor) usuario;
					request.setAttribute("mailInstitucional", tutor.getMailInstitucional());
					List<Rol> roles = rolService.obtenerRolTodos();
					List<Area> areas = areaService.obtenerAreaTodas();
					request.setAttribute("roles", roles);
					request.setAttribute("areas", areas);
					request.setAttribute("rolActual", tutor.getRol().getIdRol());
					request.setAttribute("areaActual", tutor.getArea().getIdArea());
				}

				// localidad y departamento
				Localidad localidad = usuario.getLocalidad();
				Departamento departamento = null;
				if (localidad != null) {
					departamento = localidad.getDepartamento();
					request.setAttribute("idLocalidadUsuario", localidad.getIdLocalidad());
					if (departamento != null) {
						request.setAttribute("idDepartamentoUsuario", departamento.getIdDepartamento());
					}
				}

				// Preparar listas de selección para el formulario
				List<Departamento> departamentos = departamentoService.obtenerTodosDepartamento();
				List<Localidad> localidades = localidadService.obtenerTodasLocalidades();
				List<Itr> itrs = itrService.obtenerItrTodos();
				List<Estado> estados = usuarioEstado.obtenerEstados();
				request.setAttribute("departamentos", departamentos);
				request.setAttribute("localidades", localidades);
				request.setAttribute("itrs", itrs);
				request.setAttribute("estados", estados);
				
				// Obtener mensaje de error si lo hay
                String mensajeError = request.getParameter("mensajeError");
                if (mensajeError != null && !mensajeError.isEmpty()) {
                    request.setAttribute("mensajeError", mensajeError);
                }

				request.getRequestDispatcher("/editarUsuariosDesdeAnalista.jsp").forward(request, response);
			} else {
				response.sendRedirect("errorPage.jsp"); // Redirigir a una página de error si el usuario no existe.
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    Long userId = Long.parseLong(request.getParameter("userId"));
	    Usuario usuarioModificado = usuarioService.obtenerUsuario(userId);
	    
	    if (usuarioModificado != null) {
	    	
	       
	    	//Leer atributos a modificar
	    	String documentoStr = request.getParameter("documento");
	    	long documento = Long.parseLong(documentoStr);
	    	String nombre = request.getParameter("nombre");
	    	String apellido = request.getParameter("apellido");
	    	String mailInstitucional = request.getParameter("mailInst");
	        String mail = request.getParameter("mail");
	        String telefono = request.getParameter("telefono");
	        Long departamentoId = Long.parseLong(request.getParameter("idDepartamento"));	       
	        Long localidadId = Long.parseLong(request.getParameter("idLocalidad"));
	        char genero = request.getParameter("genero").charAt(0);
		    Long itrId = Long.parseLong(request.getParameter("idItr")); 
		    String fechaNacimientoStr = request.getParameter("fechaNacimiento");
		    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		    Integer idValidacion = Integer.parseInt(request.getParameter("estadoUsuario"));		     
		    Long estadoId = Long.parseLong(request.getParameter("estadoUsuarioId"));		    
		    Localidad localidad = localidadService.obtenerLocalidadPorId(localidadId);
		    Departamento departamento = departamentoService.obtenerPorId(departamentoId);

		 // Verificar si el correo ya está en uso
            if (usuarioService.existeCorreo(mail, userId)) {
               // session.setAttribute("mensajeError", "El correo ya está en uso por otro usuario.");
               // response.sendRedirect("datosPersonalesUsuario?id=" + userId);

                response.sendRedirect("datosPersonalesUsuario?id=" + userId + "&mensajeError=" + "El correo ya está en uso por otro usuario");
                return;
            }
		    
		    Estado estado = usuarioEstado.obtenerEstadoId(estadoId);
		    
		 // Validar nombre
            if (validacion.validacionNombre(nombre)) {
                response.sendRedirect("datosPersonalesUsuario?id=" + userId + "&mensajeError=" + validacion.RespuestaValidacionNombre());
                return;
            }
		    
		    if (localidad != null && departamento != null) {
		        localidad.setDepartamento(departamento);
		        localidadService.actualizarLocalidad(localidad);
		    } else {
		        session.setAttribute("mensajeError", "Departamento o localidad no encontrada.");
		        response.sendRedirect("editarUsuario.jsp");
		        return;
		    }

		     Itr itr = itrService.obtenerItr(itrId);
		     
		     localidadService.actualizarLocalidad(localidad);
		     ValidacionUsuario validacion = validacionUsuarioService.obtenerValidacionUsuario(idValidacion);
    
		     // Actualizar atributos
	         try {
	             usuarioModificado.setDocumento(documento);
	         } catch (NumberFormatException e) {
	             session.setAttribute("mensajeError", "El formato del documento no es válido.");
	             response.sendRedirect("editarUsuario.jsp");
	             return;
	         }  
	         usuarioModificado.setNombres(nombre);
	         usuarioModificado.setApellidos(apellido);
	         
	         if (usuarioModificado instanceof Estudiante) {
	             ((Estudiante) usuarioModificado).setMailInstitucional(mailInstitucional);
	             ((Estudiante) usuarioModificado).setEstado(estado);
	         } else if (usuarioModificado instanceof Tutor) {
	             ((Tutor) usuarioModificado).setMailInstitucional(mailInstitucional);
	             ((Tutor) usuarioModificado).setEstado(estado);
	         } else if (usuarioModificado instanceof Analista) {
	             ((Analista) usuarioModificado).setMailInstitucional(mailInstitucional);
	             ((Analista) usuarioModificado).setEstado(estado);
	         }	 
	         
	         usuarioModificado.setMail(mail);
	         usuarioModificado.setTelefono(telefono);
	         localidad.setDepartamento(departamento);
	         usuarioModificado.setLocalidad(localidad);
	         usuarioModificado.setGenero(genero);
	         usuarioModificado.setItr(itr);
		    
	         try {
		            Date fechaNacimiento = formato.parse(fechaNacimientoStr);
		            usuarioModificado.setFechaNacimiento(fechaNacimiento);
		        } catch (ParseException e) {
		            e.printStackTrace();
		            session.setAttribute("mensajeError", "Formato de fecha incorrecto.");
		            response.sendRedirect("editarUsuario.jsp");
		            return;
		        }
	  
	         if (usuarioModificado instanceof Estudiante) {
	        	    Estudiante estudiante = (Estudiante) usuarioModificado;
	        	    String semestreStr = request.getParameter("semestre");
	        	    String generacionIdStr = request.getParameter("generacion");
	        	    try {
	        	        int semestre = Integer.parseInt(semestreStr);
	        	        Long generacionId = Long.parseLong(generacionIdStr);
	        	        Generacion generacion = generacionService.obtenerGeneracion(generacionId);
	        	        
	        	        if (generacion != null) {
	        	            estudiante.setSemestre(semestre);
	        	            estudiante.setGeneracion(generacion);
	        	        } else {
	        	            session.setAttribute("mensajeError", "Generación no encontrada.");
	        	            response.sendRedirect("editarUsuario.jsp");
	        	            return;
	        	        }
	        	    } catch (NumberFormatException e) {
	        	        session.setAttribute("mensajeError", "Formato numérico incorrecto.");
	        	        response.sendRedirect("editarUsuario.jsp");
	        	        return;
	        	    }
	        	}

	         if (usuarioModificado instanceof Tutor) {
	        	    Tutor tutor = (Tutor) usuarioModificado;
	        	    String rolIdStr = request.getParameter("rol");
	        	    String areaIdStr = request.getParameter("area");
	        	    try {
	        	        Long rolId = Long.parseLong(rolIdStr);
	        	        Long areaId = Long.parseLong(areaIdStr);
	        	        Rol rol = rolService.obtenerRol(rolId);
	        	        Area area = areaService.obtenerArea(areaId);
	        	        
	        	        if (rol != null && area != null) {
	        	            tutor.setRol(rol);
	        	            tutor.setArea(area);
	        	        } else {
	        	            session.setAttribute("mensajeError", "Rol o área no encontrados.");
	        	            response.sendRedirect("editarUsuario.jsp");
	        	            return;
	        	        }
	        	    } catch (NumberFormatException e) {
	        	        session.setAttribute("mensajeError", "Formato numérico incorrecto para rol o área.");
	        	        response.sendRedirect("editarUsuario.jsp");
	        	        return;
	        	    }
	        	}
   
		     usuarioModificado.setValidacionUsuario(validacion);
  
	        //Actualizar usuario
	        usuarioService.actualizarUsuario(usuarioModificado);
	        response.sendRedirect("datosPersonalesUsuario?id=" + userId + "&mensajeExito=Información actualizada correctamente.");
	    } else {
	    	response.sendRedirect("datosPersonalesUsuario?id=" + userId + "&mensajeError=No se encontró el usuario.");
	       // session.setAttribute("mensajeError", "No se encontró el usuario.");
	        response.sendRedirect("editarUsuario.jsp");
	    }
	}


}
