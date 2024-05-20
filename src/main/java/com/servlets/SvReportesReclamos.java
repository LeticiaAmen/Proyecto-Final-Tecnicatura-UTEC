package com.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Reclamo;
import com.entidades.Usuario;
import com.servicios.ReclamoService;
import com.servicios.UsuarioService;

@WebServlet("/reporteReclamosEstudianteUsuario")
public class SvReportesReclamos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioService usuarioService;

    @EJB
    private ReclamoService reclamoService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdParam = request.getParameter("id");
        String anioParam = request.getParameter("anio");
        String mesParam = request.getParameter("mes");

		/*
		 * System.out.println("Parámetros recibidos: id=" + userIdParam + ", anio=" +
		 * anioParam + ", mes=" + mesParam);
		 */

        if (userIdParam != null && !userIdParam.isEmpty()) {
            try {
                Long userId = Long.parseLong(userIdParam);
                boolean esEstudiante = usuarioService.esEstudiante(userId);

				/*
				 * System.out.println("El usuario con id " + userId + " es estudiante: " +
				 * esEstudiante);
				 */

                if (esEstudiante) {
                    List<Reclamo> reclamos;
                    if (anioParam != null && !anioParam.isEmpty() && mesParam != null && !mesParam.isEmpty()) {
                        int anio = Integer.parseInt(anioParam);
                        int mes = Integer.parseInt(mesParam);
                        reclamos = reclamoService.obtenerReclamosPorEstudianteYFecha(userId, anio, mes);
						/*
						 * System.out.println("Reclamos obtenidos para el estudiante en el año " + anio
						 * + " y mes " + mes + ": " + reclamos.size());
						 */
                    } else {
                        reclamos = reclamoService.obtenerReclamosPorEstudiante(userId);
						/*
						 * System.out.
						 * println("Reclamos obtenidos para el estudiante sin filtro de fecha: " +
						 * reclamos.size());
						 */
                    }

                    Usuario usuario = usuarioService.obtenerUsuarioPorId(userId);
                    request.setAttribute("reclamos", reclamos);
                    request.setAttribute("selectedAnio", anioParam);
                    request.setAttribute("selectedMes", mesParam);
                    request.setAttribute("estudianteSeleccionado", usuario);
                    request.getRequestDispatcher("/reporteReclamosEstudiante.jsp").forward(request, response);
                } else {
					/*
					 * System.out.
					 * println("Redirigiendo a página de error, el usuario no es estudiante.");
					 */
                    response.sendRedirect("errorPage.jsp");
                }
            } catch (NumberFormatException e) {
				/* System.out.println("ID de usuario no válido: " + userIdParam); */
                response.sendRedirect("errorPage.jsp");
            }
        } else {
			/*
			 * System.out.
			 * println("Redirigiendo a página de error, id de usuario no proporcionado.");
			 */
            response.sendRedirect("errorPage.jsp");
        }
    }
}
