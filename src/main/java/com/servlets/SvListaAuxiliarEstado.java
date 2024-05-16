package com.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entidades.Estado;
import com.entidades.RegistroAccione;
import com.servicios.EstadoService;
import com.servicios.RegistroAccionService;

@WebServlet("/SvListaAuxiliarEstado")
public class SvListaAuxiliarEstado extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private EstadoService estadoService;

    @EJB
    private RegistroAccionService registroAccionService;

    public SvListaAuxiliarEstado() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<RegistroAccione> registros = registroAccionService.obtenerRegistrosAcciones();
        List<RegistroAccione> activos = new ArrayList<>();
        List<RegistroAccione> inactivos = new ArrayList<>();

        for (RegistroAccione registro : registros) {
            if (registro.getEstado().equals("ACTIVO")) {
                activos.add(registro);
            } else if (registro.getEstado().equals("INACTIVO")) {
                inactivos.add(registro);
            }
        }
    	
    	List<Estado> estados = estadoService.obtenerEstados();
        request.setAttribute("estados", estados);
        request.setAttribute("registros", registros);
        request.setAttribute("activos", activos);
        request.setAttribute("inactivos", inactivos);
        
        request.getRequestDispatcher("/listaAuxiliarEstados.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formSubmitted = request.getParameter("formSubmitted");
        if ("true".equals(formSubmitted)) {
            String nombre = request.getParameter("nombre");
            String idestadoStr = request.getParameter("idEstado");
            Long idEstado = Long.parseLong(idestadoStr);
            Estado estadoSeleccionado = estadoService.obtenerEstadoId(idEstado);

            HttpSession session = request.getSession();
            session.removeAttribute("errorMessage");
            session.removeAttribute("successMessage");

            if (registroAccionService.existeNombre(nombre)) {
                request.setAttribute("errorMessage", "El nombre '" + nombre + "' ya está registrado. Por favor, elige un nombre diferente para continuar.");
                doGet(request, response);
            } else {
                RegistroAccione registroNuevo = new RegistroAccione();
                registroNuevo.setNombre(nombre);
                registroNuevo.setEstado(estadoSeleccionado);
                registroAccionService.crear(registroNuevo);
                request.setAttribute("successMessage", "¡Estado '" + nombre + "' registrado con éxito!");
                doGet(request, response);
            }
        } else {
            doGet(request, response);
        }
    }
}
