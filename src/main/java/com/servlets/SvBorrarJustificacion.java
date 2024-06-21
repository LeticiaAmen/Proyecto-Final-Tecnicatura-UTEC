package com.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Justificacion;
import com.servicios.JustificacionService;


@WebServlet("/SvBorrarJustificacion")
public class SvBorrarJustificacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    private JustificacionService justificacionService; 
    
    public SvBorrarJustificacion() {
        super();
  
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idJustificacionStr = request.getParameter("idJustificacion");
		if(idJustificacionStr != null ) {
			long idJustificacion = Long.parseLong(idJustificacionStr);
			Justificacion justificacion = justificacionService.obtenerJustificacion(idJustificacion);
			
			//verificar si la justificación esta en estado ingresado
			if(justificacion != null && "Ingresado".equals(justificacion.getRegistroAccione().getNombre())) {
				try {
					justificacionService.eliminarJustificacion(idJustificacion);
					request.getSession().setAttribute("mensaje", "Error al eliminar la justificación");
					
				}catch(Exception e) {
                    request.getSession().setAttribute("mensaje", "Error al eliminar la justificación");
                    e.printStackTrace();
                }
			} else {
				request.getSession().setAttribute("mensaje", "No se puede eliminar la justificación a menos que esté en estado 'Ingresado'");
			}
		}else {
            request.getSession().setAttribute("mensaje", "ID de la justificacón no proporcionada");
        }
        response.sendRedirect("SvListarJustificaciones");
    }
}
