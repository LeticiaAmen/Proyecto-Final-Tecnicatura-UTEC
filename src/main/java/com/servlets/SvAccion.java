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
import com.entidades.RegistroAccione;
import com.servicios.EstadoService;
import com.servicios.ReclamoService;
import com.servicios.RegistroAccionService;

@WebServlet("/accion")
public class SvAccion extends HttpServlet {
    @EJB
    private ReclamoService reclamoService;
   
    @EJB
    private EstadoService estadoService;
    
    @EJB
    private RegistroAccionService registroAccionService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reclamoIdParam = request.getParameter("idReclamo");
        
        if (reclamoIdParam != null && !reclamoIdParam.isEmpty()) {
            long idReclamo = Long.parseLong(reclamoIdParam);
            Reclamo reclamo = reclamoService.obtenerReclamoConAcciones(idReclamo);
            Reclamo reclamoConAcciones = reclamoService.obtenerReclamo(idReclamo);
                      
            if (reclamo != null) {
            	 String tipoEvento = reclamo.getEvento().getTipoEvento().getNombre();
            	
            	 // Obtener la lista de estados activos
                 List<RegistroAccione> estadosActivos = registroAccionService.obtenerEstadosActivos();
              
            	request.setAttribute("reclamo", reclamo);
                request.setAttribute("tipoEvento", tipoEvento);
                request.setAttribute("estados", estadosActivos);
                request.setAttribute("reclamoConAcciones", reclamoConAcciones);
                request.getRequestDispatcher("/accion.jsp").forward(request, response);
            } else {
                response.sendRedirect("errorPage.jsp");
            }
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
