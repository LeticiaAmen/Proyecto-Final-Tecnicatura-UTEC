<%@page import="com.entidades.RegistroAccione"%>
<%@page import="com.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Detalles del Reclamo</title>
	<link rel="stylesheet" href="accion.css">
</head>
<body>
	<header>
	    <div>
	        <a><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
	        <% Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
	        Boolean esAnalista = (Boolean) request.getSession().getAttribute("esAnalista");
	        if (esAnalista == null) {
	            esAnalista = Boolean.FALSE;
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
	<h1>Detalles del Reclamo</h1>
	<ul>
	    <li>Título del Reclamo: ${reclamo.tituloReclamo}</li>
	    <li>Título del Evento: ${reclamo.evento.tituloEvento}</li>
	    <li>Detalles del Evento: ${reclamo.evento.informacion}</li>
	    <li>Créditos del Evento: ${reclamo.evento.creditos}</li>
	    <li>Modalidad del Evento: ${reclamo.evento.modalidad}</li>
	    <li>Semestre del Evento: ${reclamo.evento.semestre}</li>
	    <li>Tipo de Evento: ${reclamo.evento.tipoEvento.nombre}</li>
	</ul>
	<% if (esAnalista) { %>
	    <h2>Acción</h2>
	    <form action="guardarAccion" method="POST" onsubmit="return confirmarEliminacion();">
	        <label for="nuevoEstado">Estado:</label>
	        <select name="nuevoEstado">
	            <option value="${reclamo.registroAccione.idRegistroAccion}" selected>${reclamo.registroAccione.nombre}</option>
	            <c:forEach items="${estados}" var="estado">
	                <option value="${estado.idRegistroAccion}">${estado.nombre}</option>
	            </c:forEach>
	        </select><br>
	        <input type="hidden" name="idReclamo" value="${reclamo.idReclamo}">
	        <input type="hidden" name="accionType" value="cambiarEstado">
	        <label for="detalle">Detalle de la Acción:</label><br>
	        <textarea id="detalle" name="detalle" rows="4" cols="50"></textarea><br>
	        <input type="submit" name="accion" value="Enviar" class="submit-btn" >
	    </form>
	<% } %>
	
	
	<!-- Botón Modificar Reclamo, visible solo para estudiantes y si el reclamo está 'Ingresado' -->
	<c:if test="${not esAnalista and reclamo.registroAccione.nombre == 'Ingresado'}">
	    <form action="SvEditarReclamo" method="get">
	        <input type="hidden" name="idReclamo" value="${reclamo.idReclamo}">
	        <input type="submit" value="Modificar">
	    </form>
	</c:if>

	
	<!-- Botón Cancelar -->
	<form action="SvListarReclamos" method="get" onsubmit="return confirmarCancelar();">
	    <input type="submit" value="Cancelar">
	</form>
	<script>
	    function confirmarEliminacion() {
	        return confirm("¿Actualizar estado y detalle del reclamo? Se enviará un email al estudiante con los detalles modificados");
	    }
	</script>
	<script>
	    function confirmarCancelar() {
	        return confirm("¿Cancelar registro de acción sobre el reclamo?");
	    }
	</script>
</body>
</html>
