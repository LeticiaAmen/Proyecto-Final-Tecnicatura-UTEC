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

import com.entidades.Estudiante;
import com.entidades.Itr;
import com.entidades.Usuario;
import com.entidades.ValidacionUsuario;
import com.servicios.ItrService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;


@WebServlet("/SvFiltrarUsuarios")
public class SvFiltrarUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioService usuarioService;
	@EJB
	private ItrService itrService; 
	@EJB
	private ValidacionUsuarioService validacionService; 
       
    public SvFiltrarUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String tipoUsuario = request.getParameter("tipoUsuario");
	String generacionId = request.getParameter("generacion");
	String itrId = request.getParameter("itr");
	String validacionId = request.getParameter("validacionUsuario");
	
	List<ValidacionUsuario> validaciones = validacionService.obtenerValidaciones();
	List<Itr> itrs = itrService.obtenerItrTodos();
	List<Usuario> listaFiltrada = usuarioService.obtenerUsuarios();
	request.setAttribute("validacionesUsuario", validaciones);
	
	
	//Filtramos por tipo con instance of
	if (!"todosLosUsuarios".equals(tipoUsuario)) {
		listaFiltrada = listaFiltrada.stream()
            .filter(u -> u.getClass().getSimpleName().toUpperCase().equals(tipoUsuario))
            .collect(Collectors.toList());
    }
	
	 // Filtrar por ITR
    if (!"todosLosItr".equals(itrId)) {
        long idItr = Long.parseLong(itrId);
        listaFiltrada = listaFiltrada.stream()
            .filter(u -> u.getItr() != null && u.getItr().getIdItr() == idItr)
            .collect(Collectors.toList());
    }
    
 // Filtrar por validación
    if (!"todasLasValidaciones".equals(validacionId)) {
        long idValidacion = Long.parseLong(validacionId);
        listaFiltrada = listaFiltrada.stream()
            .filter(u -> u.getValidacionUsuario() != null && u.getValidacionUsuario().getIdValidacion() == idValidacion)
            .collect(Collectors.toList());
    }
    
 // Filtrar por generación, solo aplicable a estudiantes
    if (tipoUsuario.equals("ESTUDIANTE") && generacionId != null && !generacionId.isEmpty()) {
        long genId = Long.parseLong(generacionId);
        listaFiltrada = listaFiltrada.stream()
            .filter(u -> u instanceof Estudiante && ((Estudiante) u).getGeneracion() != null && ((Estudiante) u).getGeneracion().getIdGeneracion() == genId)
            .collect(Collectors.toList());
    }
    
 // Configurar atributos para la vista JSP
    request.setAttribute("itrList", itrs);
    request.setAttribute("validacionesUsuario", validaciones);
    request.setAttribute("usuarios", listaFiltrada);

    // Reenviar a la página JSP para mostrar resultados
    request.getRequestDispatcher("/listarusuario.jsp").forward(request, response);
}
	
	
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
