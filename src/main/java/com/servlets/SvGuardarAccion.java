package com.servlets;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entidades.Estado;
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
                        Accion accion = new Accion();
                        accion.setReclamo(reclamo);
                        accion.setRegistroAccion(registroAccion);
                        accion.setDetalle(request.getParameter("detalle")); // Guardar el detalle de la acción
                        
                     // Establecer la fecha y hora actual
                        accion.setFechaHora(new Date());
                    
                        // Establecer el estado siempre como 1 (activo)
                        Estado estado = new Estado();
                        estado.setIdEstado(1); // Cambia 1 por el ID correcto del estado
                        accion.setEstado(estado);
                        
                        
                        // Cambiar el registro accion en reclamo 
                        reclamo.setRegistroAccione(registroAccion);
                        reclamoService.actualizarReclamo(reclamo);

                        
                        // Guardar la acción
                        accionService.guardarAccion(accion);
                        request.getSession().setAttribute("successMessage", "Se registró la acción correctamente");
    					response.sendRedirect("ListadoReclamos");
                        return;
                    }
                }
            }
        }
        response.sendRedirect("errorPage.jsp"); // Si hay un error o no se cumple alguna condición, redirigir a una página de error
    }
}
