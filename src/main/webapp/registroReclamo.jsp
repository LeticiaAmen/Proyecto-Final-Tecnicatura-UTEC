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
					<form action="LoginServlet" method="get">
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
	<form action="SvIngresarReclamo" method="post">

		<p>
			<label><strong>*Título del Reclamo</strong></label>
		</p>
		<input type="text" name="titulo" required>

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
		<select name="idEvento">
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
		<!-- Campo oculto para indicar el envo del formulario -->
		<input type="hidden" name="formSubmitted" value="true">
		<button type="submit">Enviar</button>
	</form>
	<form action="SvListarReclamos" method="get">
		<button type="submit">Cancelar</button>
	</form>
</body>
</html>