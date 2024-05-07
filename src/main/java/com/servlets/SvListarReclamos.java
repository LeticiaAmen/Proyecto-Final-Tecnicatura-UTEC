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
        boolean esAnalista = usuarioService.esAnalista(usuarioLogeado.getIdUsuario());
        String filtroUsuario = request.getParameter("filtroUsuario");
        String estadoReclamo = request.getParameter("estadoReclamo");
        List<Reclamo> reclamos;
        String backUrl = "menuEstudiante.jsp"; // URL por defecto

        if (usuarioService.esAnalista(usuarioLogeado.getIdUsuario())) {
            reclamos = reclamosService.obtenerReclamosConFiltros(filtroUsuario, estadoReclamo);
            backUrl = "menuAnalista.jsp"; // cambiamos la URL si analista
        } else {
            reclamos = reclamosService.obtenerReclamosPorUsuarioConFiltros(usuarioLogeado.getIdUsuario(), filtroUsuario, estadoReclamo);
        }
        request.setAttribute("esAnalista", esAnalista);
        request.setAttribute("reclamos", reclamos);
        request.setAttribute("backUrl", backUrl); // Enviamos la URL correcta al JSP
        request.getRequestDispatcher("/listadoReclamos.jsp").forward(request, response);
    }
}