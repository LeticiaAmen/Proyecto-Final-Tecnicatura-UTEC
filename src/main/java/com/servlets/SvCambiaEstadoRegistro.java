package com.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Estado;
import com.entidades.RegistroAccione;
import com.servicios.EstadoService;
import com.servicios.RegistroAccionService;


@WebServlet("/SvCambiaEstadoRegistro")
public class SvCambiaEstadoRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 @EJB
	 private RegistroAccionService registroAccionService;
	 @EJB
	 private EstadoService estadoService;

    public SvCambiaEstadoRegistro() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/SvListaAuxiliarEstado");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idRegistroStr = request.getParameter("registroSeleccionado");
	    String idEstadoStr = request.getParameter("nuevoEstado");
	    long idRegistro = Long.parseLong(idRegistroStr);
	    long idNuevoEstado = Long.parseLong(idEstadoStr);

	    RegistroAccione registro = registroAccionService.obtenerRegistroAccion(idRegistro);
	    Estado nuevoEstado = estadoService.obtenerEstadoId(idNuevoEstado);
	    
	    if (registro != null && nuevoEstado != null) {
	        registro.setEstado(nuevoEstado);
	        registroAccionService.actualizar(registro);
	        request.getSession().setAttribute("successMessage", "Estado cambiado con éxito para " + registro.getNombre());
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/SvListaAuxiliarEstado");
            dispatcher.forward(request, response);
	    } else {
	        request.getSession().setAttribute("errorMessage", "Error al cambiar el estado. Asegúrese de que los datos seleccionados son correctos.");
	    }   
	}
}
