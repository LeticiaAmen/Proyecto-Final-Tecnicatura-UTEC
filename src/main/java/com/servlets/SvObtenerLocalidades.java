package com.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entidades.Localidad;
import com.servicios.LocalidadService;

@WebServlet("/obtenerLocalidades")
public class SvObtenerLocalidades extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private LocalidadService localidadService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long departamentoId = Long.parseLong(request.getParameter("departamentoId"));
        List<Localidad> localidades = localidadService.obtenerLocalidadesPorDepartamento(departamentoId);

        response.setContentType("text/html;charset=UTF-8");
        StringBuilder sb = new StringBuilder();

        for (Localidad localidad : localidades) {
            sb.append("<option value=\"").append(localidad.getIdLocalidad()).append("\">")
              .append(localidad.getNombre()).append("</option>");
        }

        response.getWriter().write(sb.toString());
    }
}
