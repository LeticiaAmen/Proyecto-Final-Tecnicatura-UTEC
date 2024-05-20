<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.entidades.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Reclamo</title>
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
                // Redirige al usuario a la página de login si no está logeado
                response.sendRedirect("login.jsp");
                return; // Detiene la ejecución adicional de la página
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
	<h1>Editar Reclamo</h1>
	<form action="SvEditarReclamo" method="post" onsubmit="return confirmarEliminacion();">
	    <input type="hidden" name="idReclamo" value="${reclamo.idReclamo}">
	
	    <p><label><strong>*Título del Reclamo:</strong></label></p>
	    <input type="text" name="titulo" value="${reclamo.tituloReclamo}"required oninvalid="this.setCustomValidity('Por favor, introduce un título entre 3 y 20 caracteres')" 
       		oninput="validateLength(this)">

		<p>
	    <p><label><strong>*Detalle:</strong></label></p>
	    <textarea name="detalle" rows="6" cols="80" required>${reclamo.detalle}</textarea>
	
	    <p><label><strong>*Evento:</strong></label></p>
	    <select name="idEvento">
	        <c:forEach var="evento" items="${eventos}">
	            <option value="${evento.idEvento}" ${evento.idEvento == reclamo.evento.idEvento ? 'selected' : ''}>${evento.tituloEvento}</option>
	        </c:forEach>
	    </select>
		<input type="hidden" name="idReclamo" value="${reclamo.idReclamo}">
	    <input type="hidden" name="formSubmitted" value="true">
	    <input type="submit" value="Modificar">
	</form>
	
	<form action="SvListarReclamos" method="get" onsubmit="return cancelar();">
		<button type="submit">Cancelar</button>
	</form>
	<script>
	    function confirmarEliminacion() {
	        if(confirm("¿Modificar reclamo?")) {
		        alert("¡Reclamo modificado con éxito!")
		        return true;
		    }else {
			    return false
			}     
	    }
	</script>
	<script>
		function validateLength(input) {
		    // Limpiar previamente cualquier mensaje de error establecido
		    input.setCustomValidity('');
		
		    // Verificar la longitud del valor del input
		    if (input.value.length < 3 || input.value.length > 20) {
		        input.setCustomValidity('Por favor, introduce un título entre 3 y 20 caracteres');
		    }
		}
</script>
	<script>
	    function cancelar() {
	        return confirm("¿Cancelar modificación y volver al listado de reclamos?");
	    }
	</script>
</body>
</html>