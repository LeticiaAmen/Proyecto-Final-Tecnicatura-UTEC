package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entidades.Estado;
import com.entidades.Itr;
import com.servicios.EstadoService;
import com.servicios.ItrService;

@WebServlet("/SvEditarITR")
public class SvEditarITR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ItrService itrService;

	@EJB
	private EstadoService estadoService;

	public SvEditarITR() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long idItr = Long.parseLong(request.getParameter("IdItr"));

		Itr itr = itrService.obtenerItr(idItr);
		if (itr == null) {
			System.out.println("Error al obtener el itr para editar: ITR no encontrado");
			response.sendRedirect("errorPage.jsp"); // Redirige a una página de error si el ITR es null
			return;
		}
		
		List<Estado> estados = estadoService.obtenerEstados();
		request.setAttribute("estados", estados);
		request.setAttribute("itrParaEditar", itr);

		request.getRequestDispatcher("editarItr.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String formSubmitted = request.getParameter("formSubmitted");
		if ("true".equals(formSubmitted)) {
			long idItr = Long.parseLong(request.getParameter("IdItr"));
			String nombre = request.getParameter("nombre");
			String idestadoStr = request.getParameter("idEstado");
			Long idEstado = Long.parseLong(idestadoStr);  // convertir a long
			Estado estadoSeleccionado = estadoService.obtenerEstadoId(idEstado);  // obtener la entidad

			// Obtener el ITR actual para comparar el nombre
	        Itr itrActual = itrService.obtenerItr(idItr);
	        if (itrActual == null) {
	            System.out.println("Error al obtener Itr para la modificación");
	            response.sendRedirect("SvEditarITR");
	            return;
	        }
	        
	     // Verificar si el nombre ya existe en otro ITR
			Itr existente = itrService.obtenerItrDesdeBaseDeDatosNombre(nombre);
			if (existente != null && existente.getIdItr() != idItr) {
				String errorMessage = "Ya existe un ITR con este nombre.";
				request.setAttribute("errorMessage", errorMessage);
				request.getSession().setAttribute("errorMessage", "Ya existe un ITR con este nombre.");
				response.sendRedirect("ListadoItr");
			} else {
				//Actualizar campos del ITR
				itrActual.setNombre(nombre);
				itrActual.setEstado(estadoSeleccionado);
				try {
					itrService.actualizarItr(itrActual);
					request.getSession().setAttribute("successMessage", "El ITR se ha modificado exitosamente.");
					response.sendRedirect("SvListarReclamos");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error al modificar Itr");
					response.sendRedirect("ListadoItr");
				}
			}

		}else {
			// Si el formulario no ha sido enviado, redirige al doGet para cargar la página
			doGet(request, response);
		}


	}
}