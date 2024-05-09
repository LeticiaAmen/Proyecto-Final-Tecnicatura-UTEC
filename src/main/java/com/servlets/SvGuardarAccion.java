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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reclamoIdParam = request.getParameter("idReclamo");

        if (reclamoIdParam != null) {
            long reclamoId = Long.parseLong(reclamoIdParam);
            Reclamo reclamo = reclamoService.obtenerReclamo(reclamoId);

            if (reclamo != null) {
                String nuevoEstadoIdParam = request.getParameter("nuevoEstado");
                if (nuevoEstadoIdParam != null) {
                    long nuevoEstadoId = Long.parseLong(nuevoEstadoIdParam);
                    RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(nuevoEstadoId);
                    if (registroAccion != null) {
                        // Verificar si ya existe una acción para este reclamo y este estado
                        Accion accionExistente = accionService.obtenerAccionExistente(reclamoId, nuevoEstadoId);
                        if (accionExistente != null) {
                            // Si existe, crear otra acción manteniendo la misma ID del reclamo
                            Accion nuevaAccion = new Accion();
                            nuevaAccion.setReclamo(reclamo);
                            nuevaAccion.setRegistroAccion(registroAccion);
                            nuevaAccion.setDetalle(request.getParameter("detalle")); // Guardar el detalle de la nueva acción

                            // Guardar la nueva acción
                            accionService.guardarAccion(nuevaAccion);
                            response.sendRedirect("accion.jsp?idReclamo=" + reclamoId);
                            return;
                        } else {
                            // Si no existe, continuar como antes
                            Accion accion = new Accion();
                            accion.setReclamo(reclamo);
                            accion.setRegistroAccion(registroAccion);
                            accion.setDetalle(request.getParameter("detalle")); // Guardar el detalle de la acción

                            // Cambiar el estado del reclamo
                            reclamo.setRegistroAccione(registroAccion);
                            reclamoService.actualizarReclamo(reclamo);

                            // Guardar la acción
                            accionService.guardarAccion(accion);
                            response.sendRedirect("accion.jsp?idReclamo=" + reclamoId);
                            return;
                        }
                    }
                }
            }
        }
        response.sendRedirect("errorPage.jsp"); // Si hay un error o no se cumple alguna condición, redirigir a una página de error
    }
}
