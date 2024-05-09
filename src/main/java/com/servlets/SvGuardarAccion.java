package com.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Accion;
import com.entidades.Reclamo;
import com.entidades.RegistroAccione;
import com.servicios.AccionService;
import com.servicios.ReclamoService;
import com.servicios.RegistroAccionService;

@WebServlet("/guardarAccion")
public class SvGuardarAccion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private AccionService accionService;

    @EJB
    private ReclamoService reclamoService;

    @EJB
    private RegistroAccionService registroAccionService;

    public SvGuardarAccion() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String detalle = request.getParameter("detalle");
        long reclamoId = Long.parseLong(request.getParameter("reclamoId"));
        long estadoId = Long.parseLong(request.getParameter("estado"));

        // Crear la acción
        Accion accion = new Accion();
        accion.setDetalle(detalle);

        // Obtener el reclamo
        Reclamo reclamo = reclamoService.obtenerReclamo(reclamoId);

        // Obtener el estado seleccionado
        RegistroAccione estado = registroAccionService.obtenerRegistroAccion(estadoId);

        if (reclamo != null && estado != null) {
            accion.setReclamo(reclamo);
            accion.setRegistroAccion(estado);

            // Guardar la acción
            accionService.guardarAccion(accion);

            // Redireccionar a la página de detalles del reclamo
            response.sendRedirect("accion.jsp?idReclamo=" + reclamoId);
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
