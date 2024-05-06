package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Itr;
import com.servicios.ItrService;

@WebServlet("/ListadoItr")
public class ListadoItr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private ItrService itrService;

    public ListadoItr() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Itr> listaItrs = itrService.obtenerItrTodos();
        System.out.println("hola");
        System.out.println(listaItrs.size());
        
        for (Itr itr : listaItrs) {
            System.out.println("ID: " + itr.getIdItr() + ", Nombre: " + itr.getNombre() + ", Estado: " + itr.getEstado());
        }

        request.setAttribute("itrs", listaItrs);
        
        request.getRequestDispatcher("/listarITR.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
