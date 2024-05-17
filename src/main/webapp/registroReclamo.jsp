<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.entidades.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ingresar Reclamo</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
	<header>
		<div>
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
			</a>
			<%
				Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
			%>
			<div id="usuario-dropdown">
				<h1><%=usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos()%></h1>
				<div id="dropdown-content">
					<form action="datosPersonales" method="get">
						<input type="hidden" name="id" value="<%=usuarioLogeado.getIdUsuario()%>">
						<input type="submit" class="button" value="Datos Personales">
					</form>
					<form action="LogoutServlet" method="post">
    					<input type="submit" class="button" value="Cerrar Sesión">
					</form>         
				</div>
			</div>
		</div>
	</header>
	<h1>Ingresar Reclamo</h1>
	<form id="form-reclamo-evento" action="SvIngresarReclamo" method="post" onsubmit="return confirmarEliminacion();">

		<p>
			<label><strong>*Título del Reclamo</strong></label>
		</p>
		<input type="text" name="titulo" required oninvalid="this.setCustomValidity('Por favor, introduce un título entre 3 y 20 caracteres')" 
       		oninput="validateLength(this)">

		<p>
			<label><strong>*Detalle</strong></label>
		</p>
		<textarea name="detalle" rows="6" cols="80" required></textarea>

		<p>
			<label><strong>*Fecha del Reclamo</strong></label>
		</p>
		<input type="date" name="fechaReclamo" required>


		<p>
			<label><strong>*Evento</strong></label>
		</p>
		<select name="idEvento" required oninvalid="this.setCustomValidity('Por favor, seleccione un evento.')" oninput="this.setCustomValidity('')">
			<option value="">Seleccione un evento...</option>
			<c:forEach var="evento" items="${eventos}">
				<option value="${evento.idEvento}">${evento.tituloEvento}</option>
			</c:forEach>
		</select>


		<p
			style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos
				obligatorios</span>
		</p>

		<%-- Recuperar el usuario logueado desde la sesion --%>
		<% 
            if (usuarioLogeado != null) {
        %>
		<input type="hidden" name="idEstudiante"
			value="<%= usuarioLogeado.getIdUsuario() %>" />
		<% 
            } 
        %>
		<!-- Campo oculto para indicar el envío del formulario -->
		<input type="hidden" name="formSubmitted" value="true">
		<button type="submit">Enviar</button>
	</form>
	
	<form action="SvListarReclamos" method="get" onsubmit="return cancelar();">
		<button type="submit">Cancelar</button>
	</form>
	
	<script>
	    function confirmarEliminacion() {
	        if(confirm("¿Enviar reclamo?")) {
		        alert("¡Reclamo registrado con éxito!")
		        return true;
		    }else {
			    return false;
			}       
	    }
	</script>
	<script>
	    function cancelar() {
	        return confirm("¿Cancelar y volver al listado de reclamos?");
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
</body>
</html>