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
import com.servicios.EstadoService;
import com.servicios.ItrService;

@WebServlet("/ListadoItr")
public class ListadoItr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private ItrService itrService;
	@EJB 
	private EstadoService estadoService;
	

    public ListadoItr() {
        super();
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //obtiene los datos necesarios para el listado
    	List<Itr> listaItrs = itrService.obtenerItrTodos();
        List<Estado> listaEstados = estadoService.obtenerEstados();

        //pasarlee estados al comobox estado
        request.setAttribute("ListaEstados", listaEstados);
        
        //Lista de itrs
        request.setAttribute("itrs", listaItrs);
        
        request.getRequestDispatcher("/listarITR.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
