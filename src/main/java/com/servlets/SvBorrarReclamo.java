package com.servlets;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Reclamo;
import com.servicios.ReclamosService;
import java.io.IOException;

@WebServlet("/SvBorrarReclamo")
public class SvBorrarReclamo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ReclamosService reclamosService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idReclamoStr = request.getParameter("idReclamo");
        if (idReclamoStr != null) {
            long idReclamo = Long.parseLong(idReclamoStr);
            Reclamo reclamo = reclamosService.obtenerReclamo(idReclamo);
            // Verifica si el reclamo está en estado 'Ingresado'
            if (reclamo != null && "Ingresado".equals(reclamo.getRegistroAccione().getNombre())) {
                try {
                    reclamosService.eliminarReclamo(idReclamo);
                    request.getSession().setAttribute("mensaje", "Reclamo eliminado correctamente");
                } catch (Exception e) {
                    request.getSession().setAttribute("mensaje", "Error al eliminar el reclamo");
                    e.printStackTrace();
                }
            } else {
                request.getSession().setAttribute("mensaje", "No se puede eliminar el reclamo a menos que esté en estado 'Ingresado'");
            }
        } else {
            request.getSession().setAttribute("mensaje", "ID del reclamo no proporcionado");
        }
        response.sendRedirect("SvListarReclamos");
    }
}
