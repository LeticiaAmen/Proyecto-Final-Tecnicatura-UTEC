<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%-- 	pageEncoding="UTF-8"%> --%>
<%-- <%@ page import="com.entidades.Itr"%> --%>
<%-- <%@page import="com.entidades.Usuario"%> --%>
<%-- <%@ page import="com.enumerados.EstadoItr"%> --%>

<!-- <!DOCTYPE html> -->
<!-- <html> -->
<!-- <head> -->
<!-- <meta charset="UTF-8"> -->
<!-- <title>Editar ITR</title> -->
<!-- <link rel="stylesheet" href="formularios.css"> -->
<!-- </head> -->
<!-- <body> -->
<!-- 	<header> -->
<!-- 		<div> -->
<!-- 			<a> <img alt="Logo de UTEC" -->
<!-- 				src="images/utec-removebg-preview.png" /> -->
<!-- 			</a> -->
<%-- 			<% --%>
// 			Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
<%-- 			%> --%>
<!-- 			<div id="usuario-dropdown"> -->
<%-- 				<h1><%=usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos()%> --%>
<!-- 				</h1> -->
<!-- 				<div id="dropdown-content"> -->
<!-- 					<form action="LoginServlet" method="get"> -->
<!-- 						<input type="submit" class="button" value="Datos Personales"> -->
<!-- 					</form> -->

<!-- 					<form action="index.jsp"> -->
<!-- 						<input type="submit" class="button" value="Cerrar Sesión"> -->
<!-- 					</form> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</header> -->
<!-- 	<h1>Editar ITR</h1> -->

<%-- 	<% --%>
// 	Itr itr = (Itr) request.getAttribute("itr");
<%-- 	%> --%>

<%-- 	<% --%>
// 	String errorMessage = (String) request.getAttribute("errorMessage");
// 	if (errorMessage != null) {
<%-- 	%> --%>
<!-- 	<div class="error-message"> -->
<%-- 		<%=errorMessage%> --%>
<!-- 	</div> -->
<%-- 	<% --%>
// 	}
<%-- 	%> --%>

<%-- 	<% --%>
// 	String successMessage = (String) request.getAttribute("successMessage");
// 	if (successMessage != null) {
<%-- 	%> --%>
<!-- 	<div class="success-message"> -->
<%-- 		<%=successMessage%> --%>
<!-- 	</div> -->
<%-- 	<% --%>
// 	}
<%-- 	%> --%>


<%-- 	<% --%>
// 	if (itr != null) {
<%-- 	%> --%>
<!-- 	<form action="SvEditarITR" method="POST"> -->

<%-- 		<input type="hidden" name="IdItr" value="<%=itr.getIdItr()%>"> --%>

<!-- 		<p> -->
<!-- 			<strong>Nombre: </strong> -->
<!-- 		</p> -->
<%-- 		<input type="text" name="nombre" value="<%=itr.getNombre()%>" required> --%>

<!-- 		<p> -->
<!-- 			<strong>Estado: </strong> -->
<!-- 		</p> -->
<!-- 		<select name="estado"> -->

<%-- 			<% --%>
// 			for (EstadoItr estado : EstadoItr.values()) {
<%-- 			%> --%>
<%-- 			<option value="<%=estado.name()%>" --%>
<%-- 				<%=(estado == itr.getEstado()) ? "selected" : ""%>><%=estado.name()%></option> --%>
<%-- 			<% --%>
// 			}
<%-- 			%> --%>
<!-- 		</select> <input type="submit" value="Guardar"> -->
<!-- 	</form> -->
<%-- 	<% --%>
// 	} else {
<%-- 	%> --%>
<!-- 	<p>No se encontró el ITR.</p> -->
<%-- 	<% --%>
// 	}
<%-- 	%> --%>

<!-- 	<form action="ListadoItr" method="get"> -->
<!-- 		<input type="submit" value="Cancelar"> -->
<!-- 	</form> -->
<!-- </body> -->
<!-- </html> -->
