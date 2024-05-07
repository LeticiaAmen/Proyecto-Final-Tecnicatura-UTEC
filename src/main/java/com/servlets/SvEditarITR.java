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
import com.entidades.Itr;
import com.servicios.ItrService;

@WebServlet("/SvEditarITR")
public class SvEditarITR extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ItrService itrService;

    public SvEditarITR() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long idItr = Long.parseLong(request.getParameter("IdItr"));

        Itr itr = itrService.obtenerItr(idItr);

        request.setAttribute("itr", itr);

        request.getRequestDispatcher("editarItr.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long idItr = Long.parseLong(request.getParameter("IdItr"));
        String nombre = request.getParameter("nombre");
        Estado estado = Estado.valueOf(request.getParameter("estado"));
        

        Itr existente = itrService.obtenerItrDesdeBaseDeDatosNombre(nombre);
        if (existente != null && existente.getIdItr() != idItr) {
            String errorMessage = "Ya existe un ITR con este nombre";
            request.setAttribute("errorMessage", errorMessage);
            Itr itr = itrService.obtenerItr(idItr);
            request.setAttribute("itr", itr);
            request.getRequestDispatcher("editarItr.jsp").forward(request, response);
            return; 
        }

        Itr itr = itrService.obtenerItr(idItr);

        itr.setNombre(nombre);
        itr.getIdItr();

        itrService.actualizarItr(itr);

        List<Itr> listarITR = itrService.obtenerItrTodos();

        request.setAttribute("itrs", listarITR);
        
        request.getRequestDispatcher("listarITR.jsp").forward(request, response);
    }
}