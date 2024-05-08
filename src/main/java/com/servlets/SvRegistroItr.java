package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entidades.Estado;
import com.entidades.Itr;
import com.servicios.EstadoService;
import com.servicios.ItrService;

@WebServlet("/SvRegistroItr")
public class SvRegistroItr extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ItrService itrService;
    @EJB
    private EstadoService estadoService;

    public SvRegistroItr() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Estado> estados = estadoService.obtenerEstados();
        request.setAttribute("estados", estados);
        
        request.getRequestDispatcher("/registroItr.jsp").forward(request, response);
    	
    }
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String formSubmitted = request.getParameter("formSubmitted");
        if ("true".equals(formSubmitted)) {
            String nombre = request.getParameter("nombre");
            String idestadoStr = request.getParameter("idEstado");
            Long idEstado = Long.parseLong(idestadoStr);  // convertir a long
            Estado estadoSeleccionado = estadoService.obtenerEstadoId(idEstado);  // obtener la entidad

            Itr existente = itrService.obtenerItrDesdeBaseDeDatosNombre(nombre);
            if (existente != null) {
                String errorMessage = "Ya existe un ITR con este nombre.";
                request.setAttribute("errorMessage", errorMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/registroItr.jsp");
                dispatcher.forward(request, response);
            } else {
                Itr itrNuevo = new Itr();
                itrNuevo.setNombre(nombre);
                itrNuevo.setEstado(estadoSeleccionado);
                try {
                    itrService.crearItr(itrNuevo);
                    String successMessage = "El ITR se ha creado exitosamente.";
                    request.setAttribute("successMessage", successMessage);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/registroItr.jsp");
                    dispatcher.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al crear Itr");
                }
            }
        } else {
            // Si el formulario no ha sido enviado, redirige al doGet para cargar la página
            doGet(request, response);
        }
    }
}