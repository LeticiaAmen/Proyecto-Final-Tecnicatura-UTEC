package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Generacion;
import com.entidades.Itr;
import com.entidades.Usuario;
import com.entidades.ValidacionUsuario;
import com.servicios.GeneracionService;
import com.servicios.ItrService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;

@WebServlet("/SvReportes")
public class SvReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ValidacionUsuarioService validacionService;
	
	@EJB
	private ItrService itrService; 
	
	@EJB
	private UsuarioService usuarioService; 
	@EJB
	private GeneracionService generacionService; 
	
    public SvReportes() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	// Obtiene los datos necesarios para el listado
		List<Usuario> usuarios = usuarioService.obtenerUsuarios();
		List<Itr> itrs =  itrService.obtenerItrTodos();
		List<ValidacionUsuario> listaValidaciones = validacionService.obtenerValidaciones();
		List<Generacion> generaciones = generacionService.obtenerGeneracionesTodas();
		
		// Asigna los datos a atributos de solicitud para uso en JSP
		request.setAttribute("itrList", itrs);
		request.setAttribute("usuarios", usuarios);
		request.setAttribute("validacionesUsuario", listaValidaciones);
		request.setAttribute("generaciones", generaciones);
		
		// Reenvía la solicitud al JSP para mostrar la lista
		request.getRequestDispatcher("/reportes.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
