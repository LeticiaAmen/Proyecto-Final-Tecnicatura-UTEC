package com.servlets;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entidades.Reclamo;
import com.entidades.Usuario;
import com.servicios.ReclamosService;
import com.servicios.UsuarioService;
import java.io.IOException;
import java.util.List;

@WebServlet("/SvListarReclamos")
public class SvListarReclamos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private ReclamosService reclamosService;
    @EJB
    private UsuarioService usuarioService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
        String filtroUsuario = request.getParameter("filtroUsuario");
        String estadoReclamo = request.getParameter("estadoReclamo");
        List<Reclamo> reclamos;
        if (usuarioService.esAnalista(usuarioLogeado.getIdUsuario())) {
        	reclamos = reclamosService.obtenerReclamosConFiltros(filtroUsuario, estadoReclamo);
        } else {
        	reclamos = reclamosService.obtenerReclamosPorUsuarioConFiltros(usuarioLogeado.getIdUsuario(), filtroUsuario, estadoReclamo);
        }
        request.setAttribute("reclamos", reclamos);
        request.getRequestDispatcher("/listadoReclamos.jsp").forward(request, response);
    }
}


