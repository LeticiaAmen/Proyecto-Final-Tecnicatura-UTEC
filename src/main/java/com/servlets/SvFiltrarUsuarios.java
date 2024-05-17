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
import com.entidades.Generacion;
import com.entidades.Itr;
import com.entidades.Usuario;
import com.entidades.ValidacionUsuario;
import com.servicios.GeneracionService;
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
	
	@EJB
	private GeneracionService generacionService;
       
    public SvFiltrarUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoUsuario = request.getParameter("tipoUsuario");
        String generacionId = request.getParameter("generacion");
        String itrId = request.getParameter("itr");
        String validacionId = request.getParameter("validacionUsuario");
        String nombreUsuario = request.getParameter("nombreUsuario") != null ? request.getParameter("nombreUsuario").toLowerCase().trim() : "";
       
        List<ValidacionUsuario> validaciones = validacionService.obtenerValidaciones();
        List<Itr> itrs = itrService.obtenerItrTodos();
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        List<Generacion> generaciones = generacionService.obtenerGeneracionesTodas();
       
        if (!nombreUsuario.isEmpty()) {
            usuarios = usuarioService.buscarPorNombre(nombreUsuario);
        }
        
        // Filtrar por nombre o apellido
        if (!nombreUsuario.isEmpty()) {
            usuarios = usuarios.stream()
                               .filter(u -> u.getNombres().toLowerCase().contains(nombreUsuario) ||
                                            u.getApellidos().toLowerCase().contains(nombreUsuario))
                               .collect(Collectors.toList());
        }

        // Filtrar por tipo de usuario
        if (!"todosLosUsuarios".equals(tipoUsuario)) {
            usuarios = usuarios.stream()
                               .filter(u -> u.getClass().getSimpleName().toUpperCase().equals(tipoUsuario))
                               .collect(Collectors.toList());
        }

        // Filtrar por ITR
        if (!"todosLosItr".equals(itrId) && itrId != null) {
            long idItr = Long.parseLong(itrId);
            usuarios = usuarios.stream()
                               .filter(u -> u.getItr() != null && u.getItr().getIdItr() == idItr)
                               .collect(Collectors.toList());
        }

        // Filtrar por validación
        if (!"todasLasValidaciones".equals(validacionId) && validacionId != null) {
            long idValidacion = Long.parseLong(validacionId);
            usuarios = usuarios.stream()
                               .filter(u -> u.getValidacionUsuario() != null && u.getValidacionUsuario().getIdValidacion() == idValidacion)
                               .collect(Collectors.toList());
        }

        // Filtrar por generación si el tipo de usuario es "Estudiante"
        if ("ESTUDIANTE".equals(tipoUsuario) && generacionId != null && !generacionId.isEmpty()) {
            long genId = Long.parseLong(generacionId);
            usuarios = usuarios.stream()
                               .filter(u -> u instanceof Estudiante && ((Estudiante) u).getGeneracion() != null && ((Estudiante) u).getGeneracion().getIdGeneracion() == genId)
                               .collect(Collectors.toList());
        }

        request.setAttribute("itrList", itrs);
        request.setAttribute("validacionesUsuario", validaciones);
        request.setAttribute("generaciones", generaciones);
        request.setAttribute("usuarios", usuarios);

        request.getRequestDispatcher("/listarusuario.jsp").forward(request, response);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
