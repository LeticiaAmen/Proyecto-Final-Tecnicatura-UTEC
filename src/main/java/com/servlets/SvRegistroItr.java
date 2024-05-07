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
import com.entidades.Itr;
import com.servicios.ItrService;

@WebServlet("/SvRegistroItr")
public class SvRegistroItr extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ItrService itrService;

    public SvRegistroItr() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String estadoStr = request.getParameter("estado");

        if (nombre == null || nombre.trim().isEmpty() || estadoStr == null || estadoStr.trim().isEmpty()) {
            String errorMessage = "Por favor, completa todos los campos antes de crear el ITR.";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/registroItr.jsp");
            dispatcher.forward(request, response);
            return; 
        }

        Itr existente = itrService.obtenerItrDesdeBaseDeDatosNombre(nombre);
        Estado estado = Estado.valueOf(estadoStr);

        if (existente != null) {
            String errorMessage = "Ya existe un ITR con este nombre.";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/registroItr.jsp");
            dispatcher.forward(request, response);
        } else {
            Itr crearItr = new Itr();
            crearItr.setNombre(nombre);
            crearItr.setEstado(estado);

            try {
                itrService.crearItr(crearItr);
                String successMessage = "El ITR se ha creado exitosamente.";
                request.setAttribute("successMessage", successMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/registroItr.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al crear Itr");
            }
        }
    }
}
