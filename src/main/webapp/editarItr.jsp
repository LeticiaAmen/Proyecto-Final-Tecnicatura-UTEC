<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.entidades.Itr"%>
<%@page import="com.entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar ITR</title>
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

	<div class="container">
		<h1>Editar ITR</h1>
		<%
			String errorMessage = (String) request.getAttribute("errorMessage");
			if (errorMessage != null) {	%>
				<div class="error-message">
					<%=errorMessage%>
				</div>
		<%	}	%>

		<%
			String successMessage = (String) request.getAttribute("successMessage");
			if (successMessage != null) { %>
				<div class="success-message">
					<%=successMessage%>
				</div>
		<%	}	%>

		<!-- Obtener ITr a editar -->
		<%
		Itr itrParaEditar = (Itr) request.getAttribute("itrParaEditar");
		if (itrParaEditar == null) {
			response.sendRedirect("errorPage.jsp");
			System.out.println("error al obtener el itr para editar");
			return;
		}
		%>

		<form action="SvEditarITR" method="post" onsubmit="return confirmarModificacion();">
			<input type="hidden" name="IdItr" value="<%=itrParaEditar.getIdItr()%>">
			
			<p>
				<label>Nombre:</label>
			</p>
			<input type="text" name="nombre" value="<%=itrParaEditar.getNombre()%>" required />

			<p>
				<label>Estado:</label>
			</p>

			<!-- Menú desplegable: estado del ITR -->
			<select name="idEstado">
				<c:forEach var="estado" items="${estados}"> <!-- Iterar sobre todos los estados disponibles para seleccionar uno -->
					<c:choose>
						<c:when test="${estado.idEstado == itrParaEditar.estado.idEstado}"><!-- Condición para verificar si el estado actual es el estado del ITR que estamos editando -->
							<option value="${estado.idEstado}" selected>${estado.nombre}</option>
						</c:when>
						<c:otherwise>
							<option value="${estado.idEstado}">${estado.nombre}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>

			<!-- Campo oculto para indicar el envío del formulario -->
			<input type="hidden" name="formSubmitted" value="true">

			<!--  Botón Modificar  -->
			<input type="submit" name="accion" value="Modificar" class="submit-btn">

		</form>
		
		<!--Botón cancelar  -->
		<form action="ListadoItr" method="get">
			<input type="submit" value="Cancelar">
		</form>
	</div>
	
	<script type="text/javascript">
		function confirmarModificacion() {
			return confirm('¿Modificar ITR?');
		}
	</script>
</body>
</html>
