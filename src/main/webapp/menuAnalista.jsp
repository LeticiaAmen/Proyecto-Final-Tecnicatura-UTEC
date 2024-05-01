<%@page import="com.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu de Usuario</title>
<link rel="stylesheet" href="menuUsuario.css">
</head>
<body>
    <header>
        <div>
            <a> <img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /> </a>
            <%
            Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
            String jwtToken = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("Authorization".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        if (jwtToken.startsWith("Bearer ")) {
                            jwtToken = jwtToken.substring(7); // Elimina "Bearer"
                        }
                        break;
                    }
                }
            }
            %>
            <div id="usuario-dropdown">
                <h1><%= usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos() %></h1>
                <%-- Muestra el token --%>
                <% if (jwtToken != null) { %>
                <% System.out.println(jwtToken);%>
                <% } else { %>
               <% System.out.println("No hay token disponible");%>
                <% } %>
                <div id="dropdown-content">
                    <form action="LoginServlet" method="get">
                        <input type="submit" class="button" value="Datos Personales">
                    </form>
                    <form action="index.jsp">
                        <input type="submit" class="button" value="Cerrar Sesión">
                    </form>
                </div>
            </div>
        </div>
    </header>
    <form action="ListadoItr" method="get">
        <input type="submit" class="button" value="Listar Itrs">
    </form>
    <form action="SvListadoDeUsuario" method="get">
        <input type="submit" class="button" value="Listar Usuarios">
    </form>
</body>
</html>
