<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.entidades.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Registro de Itr</title>
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
				<h1><%=usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos()%>
				</h1>
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
	<div class="container">
		<h1>Registro de ITR</h1>

		<%
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
		%>
		<div class="error-message">
			<%=errorMessage%>
		</div>
		<%
		}
		%>

		<%
		String successMessage = (String) request.getAttribute("successMessage");
		if (successMessage != null) {
		%>
		<div class="success-message">
			<%=successMessage%>
		</div>
		<%
		}
		%>

		<form action="SvRegistroItr" method="POST">

			<p>
				Nombre: <input type="text" name="nombre" />
			</p>
			<p>
				Estado: <select name="estado" class="form-control">
					<option value="" selected>Seleccione un estado</option>
					<!-- Opción vacía o por defecto -->
<%-- 					<% --%>
// 					for (com.enumerados.EstadoItr estado : com.enumerados.EstadoItr.values()) {
<%-- 					%> --%>
<%-- 					<option value="<%=estado.name()%>"><%=estado.name()%></option> --%>
<%-- 					<% --%>
// 					}
<%-- 					%> --%>
				</select>
			</p>
			<button type="submit" class="submit-btn">Enviar</button>
		</form>

		<form action="ListadoItr" method="get">
			<input type="submit" value="Cancelar">
		</form>
	</div>
</body>
</html>
