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

        List<Itr> itrs = itrService.obtenerItrTodos();
       
        if (estadoFiltrar != null && !estadoFiltrar.isEmpty()) {
   
        	
        	//NO FUNCIONA EL FILTRO DEJE EL CODIGO VIEJO
            Estado estado = Estado.valueOf(estadoFiltrar);
            itrs = itrService.obtenerItrsPorEstado(estado);
        } else {
           itrs = itrService.obtenerItrTodos();
        }

        request.setAttribute("itrs", itrs);

       request.getRequestDispatcher("listarITR.jsp").forward(request, response);
   }
}
