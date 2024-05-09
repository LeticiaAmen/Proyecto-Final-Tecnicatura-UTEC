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

import com.entidades.Accion;
import com.entidades.Estado;
import com.entidades.Reclamo;
import com.entidades.RegistroAccione;
import com.servicios.AccionService;
import com.servicios.EstadoService;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String detalle = request.getParameter("detalle");
        long reclamoId = Long.parseLong(request.getParameter("reclamoId"));

        // Obtener el ID del registro de acción seleccionado en la combobox
        long registroAccionId = 0; // Valor predeterminado o algún valor válido en caso de que sea nulo
        String registroAccionIdStr = request.getParameter("registroAccionId");
        if (registroAccionIdStr != null && !registroAccionIdStr.isEmpty()) {
            registroAccionId = Long.parseLong(registroAccionIdStr);
        }

        // Obtener el registro de la acción seleccionada
        RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(registroAccionId);

        // Crear la acción
        Accion accion = new Accion();
        accion.setDetalle(detalle);

        // Obtener el reclamo
        Reclamo reclamo = reclamoService.obtenerReclamo(reclamoId);

        if (reclamo != null && registroAccion != null) {
            accion.setReclamo(reclamo);
            accion.setRegistroAccion(registroAccion); // Asignar el registro de acción a la acción

            // Guardar la acción
            accionService.guardarAccion(accion);
            
            // Obtener la lista actualizada de registros de acciones
            List<RegistroAccione> registrosAcciones = registroAccionService.obtenerRegistrosAcciones();
            // Pasar los registros de acciones al atributo del request
            request.setAttribute("registrosAcciones", registrosAcciones);

            // Redireccionar a la página de detalles del reclamo
            RequestDispatcher dispatcher = request.getRequestDispatcher("accion.jsp?idReclamo=" + reclamoId);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}