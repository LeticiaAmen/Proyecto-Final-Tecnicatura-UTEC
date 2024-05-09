package com.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Reclamo;
import com.servicios.ReclamoService;

@WebServlet("/accion")
public class SvAccion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ReclamoService reclamoService;

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
                
                request.setAttribute("reclamo", reclamo);
                request.setAttribute("tipoEvento", tipoEvento); 

                request.getRequestDispatcher("/accion.jsp").forward(request, response);
            } else {
                response.sendRedirect("errorPage.jsp");
            }
        } else {
            response.sendRedirect("errorPage.jsp");
        }

    }

}

