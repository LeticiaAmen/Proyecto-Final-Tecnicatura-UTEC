package com.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Evento;
import com.entidades.Reclamo;
import com.servicios.EventoService;
import com.servicios.ReclamoService;

@WebServlet("/SvEditarReclamo")
public class SvEditarReclamo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ReclamoService reclamoService;

    @EJB
    private EventoService eventoService;

    public SvEditarReclamo() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idReclamoStr = request.getParameter("idReclamo");
        if (idReclamoStr != null) {
            try {
                long idReclamo = Long.parseLong(idReclamoStr);
                Reclamo reclamo = reclamoService.obtenerReclamo(idReclamo);
                if (reclamo != null) {
                    request.setAttribute("reclamo", reclamo);
                    List<Evento> eventos = eventoService.obtenerEventosTodos();
                    request.setAttribute("eventos", eventos);
                    request.getRequestDispatcher("/editarReclamo.jsp").forward(request, response);
                } else {
                    response.sendRedirect("SvListarReclamos");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("SvListarReclamos");
            }
        } else {
            response.sendRedirect("SvListarReclamos");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formSubmitted = request.getParameter("formSubmitted");
        if ("true".equals(formSubmitted)) {
            String idReclamoStr = request.getParameter("idReclamo");
            long idReclamo = Long.parseLong(idReclamoStr);

            Reclamo reclamo = reclamoService.obtenerReclamo(idReclamo);

            String titulo = request.getParameter("titulo");
            String detalle = request.getParameter("detalle");

            Date fechaReclamo = new Date();

            String idEvento = request.getParameter("idEvento");
            Long idEventoLong = Long.parseLong(idEvento);
            Evento evento = eventoService.obtenerEvento(idEventoLong);

            reclamo.setTituloReclamo(titulo);
            reclamo.setDetalle(detalle);
            reclamo.setFechaHoraReclamo(fechaReclamo);
            reclamo.setEvento(evento);

            try {
                reclamoService.actualizarReclamo(reclamo);
                request.getSession().setAttribute("mensaje", "Reclamo actualizado con éxito");
                response.sendRedirect("SvListarReclamos");
            } catch (Exception e) {
                request.getSession().setAttribute("mensaje", "Error al actualizar el reclamo");
                e.printStackTrace();
                response.sendRedirect("editarReclamo.jsp?idReclamo=" + idReclamo);
            }
        } else {
            doGet(request, response);
        }
    }
}
