package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SvRegistroOpciones
 */
@WebServlet("/SvRegistroOpciones")
public class SvRegistroOpciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SvRegistroOpciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipoRegistro = request.getParameter("tipoRegistro");

	    switch(tipoRegistro) {
	        case "ANALISTA":
	            request.getRequestDispatcher("/SvRegistroAnalista").forward(request, response);//esta se cambió para que cargué [rimero el servlet para que cargue los departamentos. 
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
