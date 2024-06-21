<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.entidades.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Justificación</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
 <header>
        <div>
            <a> <img alt="Logo de UTEC" src="images/utec-removebg-preview.png" />
            </a>
            <%
            Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
            if (usuarioLogeado == null) {
                response.sendRedirect("login.jsp");
                return;
            }
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
    
    <h1>Editar Justificación</h1>
    <form action="SvEditarJustificacion" method="post" onsubmit="return confirmarEliminacion();">
    <input type="hidden" name="idJustificacion" value="${justificacion.idJustificacion}">
    
    <p><label><strong>*Detalle:</strong></label></p>
        <textarea name="detalle" rows="6" cols="80" required>${justificacion.detalle}</textarea>
    
    <p><label><strong>*Evento:</strong></label></p>
        <select name="idEvento">
            <c:forEach var="evento" items="${eventos}">
                <option value="${evento.idEvento}" ${evento.idEvento == justificacion.evento.idEvento ? 'selected' : ''}>${evento.tituloEvento}</option>
            </c:forEach>
        </select>
        
        <input type="hidden" name="idJustificacion" value="${justificacion.idJustificacion}">
        <input type="hidden" name="formSubmitted" value="true">
        <input type="submit" value="Modificar">   
    </form>
    
    <form action="SvListarJustificaciones" method="get" onsubmit="return cancelar();">
    <button type="submit">Cancelar</button>
    </form>
    <script>
        function confirmarEliminacion() {
            if(confirm("¿Modificar Justificación?")) {
                alert("¡Justificación modificado con éxito!")
                return true;
            } else {
                return false;
            }     
        }
    </script>
   
    <script>
        function cancelar() {
            return confirm("¿Cancelar modificación y volver al listado de Justificaciones?");
        }
    </script>

</body>
</html>