<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
					<form action="LogoutServlet" method="post">
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

		<form id="form-itr" action="SvRegistroItr" method="POST">

			<p>
				<label>Nombre:</label>
			</p>
			<input type="text" name="nombre" required oninvalid="this.setCustomValidity('Por favor, ingrese el nombre del ITR.')" oninput="this.setCustomValidity('')" />


			<!-- Opción elegir estado -->
			<p>
				<label>Estado:</label>
			</p>

			<select name="idEstado" required oninvalid="this.setCustomValidity('Por favor, seleccione un estado.')" oninput="this.setCustomValidity('')">
			    <option value="">Seleccione un estado...</option>
			    <c:forEach var="estado" items="${estados}">
			        <option value="${estado.idEstado}">${estado.nombre}</option>
			    </c:forEach>
			</select>
			
			<!-- Campo oculto para indicar el envío del formulario -->
			<input type="hidden" name="formSubmitted" value="true">
			<button type="submit" class="submit-btn">Enviar</button>
		</form>

		<form action="ListadoItr" method="get">
			<input type="submit" value="Cancelar">
		</form>
	</div>
	<script>
		document.getElementById("form-itr").onsubmit = function() {
    		var estado = document.querySelector("[name='idEstado']").value;
    		if (estado === "") {
        		alert("Por favor, seleccione un estado para el ITR.");
        		return false; // Detiene el envío del formulario
    		}
    		return true;
		};
</script>
	
</body>
</html>
