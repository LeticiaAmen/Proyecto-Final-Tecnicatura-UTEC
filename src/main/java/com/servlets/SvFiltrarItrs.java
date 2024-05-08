package com.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.EstadoDAO;
import com.entidades.Estado;
import com.entidades.Itr;
import com.servicios.EstadoService;
import com.servicios.ItrService;

@WebServlet("/SvFiltrarItrs")
public class SvFiltrarItrs extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ItrService itrService;
    
    @EJB
    private EstadoService estadoService; 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//obtener estado seleccionado
    	String estadoId = request.getParameter("estado");
    	
    	List<Itr> listaFiltrada = itrService.obtenerItrTodos();
    	List<Estado> estados = estadoService.obtenerEstados();
  
    	
    
    	
    	
    	//filtrar por estado
    	if(!"todosLosEstados".equals(estadoId)) {
    		long idEstado = Long.parseLong(estadoId);
    		listaFiltrada = listaFiltrada.stream()
    				.filter(u -> u.getEstado() != null && u.getEstado().getIdEstado() == idEstado)
    				.collect(Collectors.toList());
    	}
    	
    	//le pasamos la lista de itrs filtrados por estado
    	request.setAttribute("itrs", listaFiltrada);
    	request.setAttribute("ListaEstados", estados);

       request.getRequestDispatcher("/listarITR.jsp").forward(request, response);
   }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
