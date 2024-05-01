package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SvRegistroOpciones")
public class SvRegistroOpciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SvRegistroOpciones() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipoRegistro = request.getParameter("tipoRegistro");

	    switch(tipoRegistro) {
	        case "ANALISTA":
	            request.getRequestDispatcher("/SvRegistroAnalista").forward(request, response);
	            break;
	        case "TUTOR":
	            request.getRequestDispatcher("/SvRegistroTutor").forward(request, response);
	            break;
	        case "ESTUDIANTE":
	            request.getRequestDispatcher("/SvRegistroEstudiante").forward(request, response);
	            break;
	        default:
	            throw new IllegalArgumentException("Tipo de usuario no válido: " + tipoRegistro);
	    }
	}

}
