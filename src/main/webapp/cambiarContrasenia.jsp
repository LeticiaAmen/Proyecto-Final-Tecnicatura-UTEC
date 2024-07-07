<%@page import="com.entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cambiar Contraseña</title>
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
				<h1><%=usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos()%></h1>

				<%-- Muestra el token --%>
				<%
				if (jwtToken != null) {
					System.out.println(jwtToken);
				} else {
					System.out.println("No hay token disponible");
				}
				%>

				<div id="dropdown-content">
					<form action="datosPersonales" method="get">
						<input type="hidden" name="id"
							value="<%=usuarioLogeado.getIdUsuario()%>"> <input
							type="submit" class="button" value="Datos Personales">
					</form>

					<form action="LogoutServlet" method="post">
						<input type="submit" class="button" value="Cerrar Sesión">
					</form>
				</div>
			</div>
		</div>
	</header>


	<h1>Cambiar Contraseña</h1>

	<%-- Mostrar mensajes de error y éxito --%>
	<c:if test="${not empty param.mensajeError}">
		<div class="alert alert-danger" role="alert" style="color: red;">
			${param.mensajeError}</div>
	</c:if>
	<c:if test="${not empty param.mensajeExito}">
		<div class="alert alert-success" role="alert" style="color: blue;">
			${param.mensajeExito}</div>
	</c:if>


	<form action="cambiarContrasenia" method="post">
		<input type="hidden" name="userId" value="${param.id}">

		<p>
			<strong>Contraseña Actual*</strong>
		</p>
		<input type="password" name="contraseniaActual" required>

		<p>
			<strong>Nueva Contraseña*</strong>
		</p>
		<input type="password" name="nuevaContrasenia" required>

		<p>
			<strong>Confirmar Nueva Contraseña*</strong>
		</p>
		<input type="password" name="confirmarContrasenia" required>

		<p
			style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos
				obligatorios</span>
		</p>
		<input type="submit" value="Cambiar Contraseña">
	</form>
	<form action="datosPersonales" method="get">
		<input type="hidden" name="id" value="${param.id}"> <input
			type="submit" value="Volver">
	</form>
</body>
</html>

