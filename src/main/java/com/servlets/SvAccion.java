package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Reclamo;
import com.entidades.RegistroAccione;
import com.servicios.RegistroAccionService;
import com.servicios.ReclamoService;

@WebServlet("/accion")
public class SvAccion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ReclamoService reclamoService;

    @EJB
    private RegistroAccionService registroAccionService;

    public SvAccion() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reclamoIdParam = request.getParameter("idReclamo");

        if (reclamoIdParam != null && !reclamoIdParam.isEmpty()) {
            long idReclamo = Long.parseLong(reclamoIdParam);
            Reclamo reclamo = reclamoService.obtenerReclamo(idReclamo);

            if (reclamo != null) {
                String tipoEvento = reclamo.getEvento().getTipoEvento().getNombre();

                // Obtener la lista de estados
                List<RegistroAccione> estados = registroAccionService.obtenerRegistrosAcciones(); 

                request.setAttribute("reclamo", reclamo);
                request.setAttribute("tipoEvento", tipoEvento);
                request.setAttribute("estados", estados); 

                request.getRequestDispatcher("/accion.jsp").forward(request, response);
            } else {
                response.sendRedirect("errorPage.jsp");
            }
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
