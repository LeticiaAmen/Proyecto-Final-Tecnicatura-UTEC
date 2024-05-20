<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.entidades.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reportes</title>
<link rel="stylesheet" href="listados.css">
</head>
<body>
    <header>
        <div>
            <a><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
            <%
            Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
            %>
            <div id="usuario-dropdown">
                <h1><%= usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos() %></h1>
                <div id="dropdown-content">
                    <form action="datosPersonales" method="get">
                        <input type="hidden" name="id" value="<%= usuarioLogeado.getIdUsuario() %>">
                        <input type="submit" class="button" value="Datos Personales">
                    </form>
                    <form action="LogoutServlet" method="post">
                        <input type="submit" class="button" value="Cerrar Sesión">
                    </form>
                </div>
            </div>
        </div>
    </header>
    
    <h1>Reportes</h1>
    <form action="SvFiltrarReportes" method="get" class="filter-form">
        <input type="text" id="nombreUsuario" name="nombreUsuario" placeholder="Ingrese nombre o apellido.." style="margin-left: 17em">
        <input type="submit" value="Buscar">
        <!-- Filtrado de ITR -->
        <label for="itr" style="margin-left: 1em">ITR:</label>
        <select name="itr" id="itr">
            <option value="todosLosItr">TODOS</option>
            <% List<Itr> listaItr = (List<Itr>) request.getAttribute("itrList"); %>
            <% if (listaItr != null && !listaItr.isEmpty()) { %>
                <% for (Itr itr : listaItr) { %>
                    <option value="<%= itr.getIdItr() %>"><%= itr.getNombre() %></option>
                <% } %>
            <% } else { %>
                <option value="">No hay elementos</option>
            <% } %>
        </select>
        <label for="generacion">Generación:</label>
        <select name="generacion" id="generacion">
            <option value="">Seleccione una generación</option>
            <% List<Generacion> generaciones = (List<Generacion>) request.getAttribute("generaciones"); %>
            <% if (generaciones != null) { %>
                <% for (Generacion g : generaciones) { %>
                    <option value="<%= g.getIdGeneracion() %>"><%= g.getNombre() %></option>
                <% } %>
            <% } %>
        </select>
        <input type="submit" value="Filtrar" style="margin-left: 20px">       
    </form>

    <form action="SvReportes" method="get">
        <input type="submit" class="button" value="Limpiar Filtros">
    </form>
    <form action="menuAnalista.jsp" method="get">
        <input style="margin-left: 3em" type="submit" value="Volver">
    </form>
    
    <table border="1">
        <tr>
            <th>Estado</th>
            <th>Validación</th>
            <th>Nombre de Usuario</th>
            <th>Documento</th>
            <th>Correo Electrónico</th>
            <th>ITR</th>
            <th>Generación</th>
            <th>Acciones</th>
        </tr>
        <% List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios"); %>
        <% if (listaUsuarios != null && !listaUsuarios.isEmpty()) { %>
            <% for (Usuario usuario : listaUsuarios) { %>
                <% if (usuario instanceof Estudiante) { 
                    Estudiante estudiante = (Estudiante) usuario; %>
                    <tr>
                        <td><%= estudiante.getEstado().getNombre() %></td>
                        <td><%= usuario.getValidacionUsuario().getNombre() %></td>
                        <td><%= usuario.getNombreUsuario() %></td>
                        <td><%= usuario.getDocumento() %></td>
                        <td><%= estudiante.getMailInstitucional() %></td>
                        <td><%= usuario.getItr().getNombre() %></td>
                        <td><%= estudiante.getGeneracion() != null ? estudiante.getGeneracion().getNombre() : "No definida" %></td>
                        <td>
                            <a href="reporteReclamosEstudianteUsuario?id=<%= usuario.getIdUsuario() %>">Reclamos</a>
                            <a href="#" onclick="event.preventDefault(); alert('Esta funcionalidad no está disponible actualmente.');">Justificaciones</a>
                            <a href="#" onclick="event.preventDefault(); alert('Esta funcionalidad no está disponible actualmente.');">Constancias</a>                                             
                        </td>
                    </tr>
                <% } %>
            <% } %>
        <% } else { %>
            <tr>
                <td colspan="8">No hay estudiantes registrados.</td>
            </tr>
        <% } %>   
    </table>
</body>
</html>
