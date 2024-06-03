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
import com.entidades.Analista;
import com.entidades.Reclamo;
import com.entidades.Justificacion;
import com.entidades.RegistroAccione;
import com.entidades.Usuario;
import com.servicios.AccionService;
import com.servicios.ReclamoService;
import com.servicios.JustificacionService;
import com.servicios.RegistroAccionService;

@WebServlet("/guardarAccion")
public class SvGuardarAccion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private AccionService accionService;

    @EJB
    private ReclamoService reclamoService;

    @EJB
    private JustificacionService justificacionService;

    @EJB
    private RegistroAccionService registroAccionService;

    public SvGuardarAccion() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String tipo = request.getParameter("tipo");
        String idParam = request.getParameter("id");
        String nuevoEstadoIdParam = request.getParameter("nuevoEstado");
        String detalle = request.getParameter("detalle");

        if (idParam != null && nuevoEstadoIdParam != null) {
            long id = Long.parseLong(idParam);
            long nuevoEstadoId = Long.parseLong(nuevoEstadoIdParam);
            RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(nuevoEstadoId);

            if (registroAccion != null) {
                Accion accion = new Accion();
                accion.setRegistroAccion(registroAccion);
                accion.setDetalle(detalle);
                accion.setFechaHora(new Date());

                // Obtener el usuario logeado de la sesión
                Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
                if (usuarioLogeado instanceof Analista) {
                    Analista analista = (Analista) usuarioLogeado;
                    accion.setAnalista(analista);
                }

                // Establecer el estado siempre como 1 (activo)
                Estado estado = new Estado();
                estado.setIdEstado(1);
                accion.setEstado(estado);

                if ("reclamo".equals(tipo)) {
                    Reclamo reclamo = reclamoService.obtenerReclamo(id);
                    if (reclamo != null) {
                        accion.setReclamo(reclamo);
                        reclamo.setRegistroAccione(registroAccion);
                        reclamoService.actualizarReclamo(reclamo);
                    }
                } else if ("justificacion".equals(tipo)) {
                    Justificacion justificacion = justificacionService.obtenerJustificacion(id);
                    if (justificacion != null) {
                        accion.setJustificacion(justificacion);
                        justificacion.setRegistroAccione(registroAccion);
                        justificacionService.actualizarJustificacion(justificacion);
                    }
                }

                // Guardar la acción
                accionService.guardarAccion(accion);
                request.getSession().setAttribute("successMessage", "Se registró la acción correctamente");

                if ("reclamo".equals(tipo)) {
                    response.sendRedirect("SvListarReclamos");
                } else if ("justificacion".equals(tipo)) {
                    response.sendRedirect("SvListarJustificaciones");
                }
                return;
            }
        }
        response.sendRedirect("errorPage.jsp"); // Si hay un error o no se cumple alguna condición, redirigir a una página de error
    }
}
