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
					<form action="datosPersonales" method="get">
						<input type="hidden" name="id"
							value="<%= usuarioLogeado.getIdUsuario() %>"> <input
							type="submit" class="button" value="Datos Personales">
					</form>

					<form action="LogoutServlet" method="post">
    					<input type="submit" class="button" value="Cerrar Sesión">
					</form>
				</div>
			</div>
		</div>
	</header>
</body>
</html>