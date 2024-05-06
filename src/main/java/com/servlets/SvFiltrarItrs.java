package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.EstadoDAO;
import com.entidades.Estado;
import com.entidades.Itr;
import com.servicios.ItrService;

@WebServlet("/SvFiltrarItrs")
public class SvFiltrarItrs extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ItrService itrService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String estadoFiltrar = request.getParameter("estado");

        List<Itr> listaItrs;

        if (estadoFiltrar != null && !estadoFiltrar.isEmpty()) {
        	
            EstadoDAO estado = EstadoDAO.valueOf(estadoFiltrar);
            listaItrs = itrService.obtenerItrsPorEstado(estado);
        } else {
            listaItrs = itrService.obtenerItrTodos();
        }

        request.setAttribute("itrs", listaItrs);

        request.getRequestDispatcher("listarITR.jsp").forward(request, response);
    }
}