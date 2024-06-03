package com.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Estado;
import com.entidades.Reclamo;
import com.entidades.Justificacion;
import com.entidades.RegistroAccione;
import com.servicios.EstadoService;
import com.servicios.ReclamoService;
import com.servicios.JustificacionService;
import com.servicios.RegistroAccionService;

@WebServlet("/accion")
public class SvAccion extends HttpServlet {
    @EJB
    private ReclamoService reclamoService;

    @EJB
    private JustificacionService justificacionService;

    @EJB
    private EstadoService estadoService;
    
    @EJB
    private RegistroAccionService registroAccionService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        String idParam = tipo != null && tipo.equals("reclamo") ? request.getParameter("idReclamo") : request.getParameter("idJustificacion");

        if (idParam != null && !idParam.isEmpty()) {
            long id = Long.parseLong(idParam);
            if (tipo != null && tipo.equals("reclamo")) {
                Reclamo reclamo = reclamoService.obtenerReclamoConAcciones(id);
                if (reclamo != null) {
                    prepararVistaReclamo(request, reclamo);
                    request.getRequestDispatcher("/accion.jsp").forward(request, response);
                } else {
                    response.sendRedirect("errorPage.jsp");
                }
            } else if (tipo != null && tipo.equals("justificacion")) {
                Justificacion justificacion = justificacionService.obtenerJustificacionConAcciones(id);
                if (justificacion != null) {
                    prepararVistaJustificacion(request, justificacion);
                    request.getRequestDispatcher("/accion.jsp").forward(request, response);
                } else {
                    response.sendRedirect("errorPage.jsp");
                }
            } else {
                response.sendRedirect("errorPage.jsp");
            }
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }

    private void prepararVistaReclamo(HttpServletRequest request, Reclamo reclamo) {
        List<RegistroAccione> estadosActivos = registroAccionService.obtenerEstadosActivos();
        request.setAttribute("reclamo", reclamo);
        request.setAttribute("tipoEvento", reclamo.getEvento().getTipoEvento().getNombre());
        request.setAttribute("estados", estadosActivos);
        request.setAttribute("tipo", "reclamo");
    }

    private void prepararVistaJustificacion(HttpServletRequest request, Justificacion justificacion) {
        List<RegistroAccione> estadosActivos = registroAccionService.obtenerEstadosActivos();
        request.setAttribute("justificacion", justificacion);
        request.setAttribute("tipoEvento", justificacion.getEvento().getTipoEvento().getNombre());
        request.setAttribute("estados", estadosActivos);
        request.setAttribute("tipo", "justificacion");
    }
}
