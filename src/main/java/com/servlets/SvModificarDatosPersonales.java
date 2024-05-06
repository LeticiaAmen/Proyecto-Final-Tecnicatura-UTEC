package com.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Analista;
import com.entidades.Area;
import com.entidades.Departamento;
import com.entidades.Estudiante;
import com.entidades.Generacion;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.entidades.Rol;
import com.entidades.Tutor;
import com.entidades.Usuario;
import com.servicios.AreaService;
import com.servicios.DepartamentoService;
import com.servicios.GeneracionService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.RolService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;

@WebServlet("/datosPersonales")
public class SvModificarDatosPersonales extends HttpServlet {
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

	public SvModificarDatosPersonales() {
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
				request.setAttribute("departamentos", departamentos);
				request.setAttribute("localidades", localidades);
				request.setAttribute("itrs", itrs);

				request.getRequestDispatcher("/datosPersonales.jsp").forward(request, response);
			} else {
				response.sendRedirect("errorPage.jsp"); // Redirigir a una página de error si el usuario no existe.
			}
		}
	}
}