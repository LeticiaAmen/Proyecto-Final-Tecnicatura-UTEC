package com.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Estudiante;
import com.servicios.GeneracionService;
import com.servicios.ItrService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;


@WebServlet("/SvFiltrarReportes")
public class SvFiltrarReportes extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioService usuarioService;

    @EJB
    private ItrService itrService;

    @EJB
    private ValidacionUsuarioService validacionService;

    @EJB
    private GeneracionService generacionService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String generacionId = request.getParameter("generacion");
        String itrId = request.getParameter("itr");
        String nombreUsuario = request.getParameter("nombreUsuario") != null ? request.getParameter("nombreUsuario").toLowerCase().trim() : "";

        List<Estudiante> estudiantes = usuarioService.obtenerEstudiantes(); 

        // Filtrar por nombre o apellido
        if (!nombreUsuario.isEmpty()) {
            estudiantes = estudiantes.stream()
                                     .filter(e -> e.getNombres().toLowerCase().contains(nombreUsuario) ||
                                                  e.getApellidos().toLowerCase().contains(nombreUsuario))
                                     .collect(Collectors.toList());
        }

        // Filtrar por ITR si el parámetro está presente y no es "todosLosItr"
        if (itrId != null && !itrId.isEmpty() && !"todosLosItr".equals(itrId)) {
            long idItr = Long.parseLong(itrId);
            estudiantes = estudiantes.stream()
                                     .filter(e -> e.getItr() != null && e.getItr().getIdItr() == idItr)
                                     .collect(Collectors.toList());
        }

        // Filtrar por generación si el parámetro está presente y no está vacío
        if (generacionId != null && !generacionId.isEmpty()) {
            long genId = Long.parseLong(generacionId);
            estudiantes = estudiantes.stream()
                                     .filter(e -> e.getGeneracion() != null && e.getGeneracion().getIdGeneracion() == genId)
                                     .collect(Collectors.toList());
        }

        request.setAttribute("itrList", itrService.obtenerItrTodos());
        request.setAttribute("generaciones", generacionService.obtenerGeneracionesTodas());
        request.setAttribute("usuarios", estudiantes); // Actualizar el atributo con solo estudiantes

        request.getRequestDispatcher("/reportes.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
