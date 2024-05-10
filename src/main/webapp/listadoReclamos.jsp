<%@page import="com.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Listado de Reclamos</title>
<link rel="stylesheet" href="ListadoReclamos.css">
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
	<h1>Listado de Reclamos</h1>
	<form action="SvListarReclamos" method="get" class="filter-form">	
		<c:if test="${esAnalista}">
			<!-- Solo muestra el filtro si es un analista -->
			<label for="filtroUsuario">Filtrar por Usuario:</label>
			<input type="text" name="filtroUsuario" placeholder="Nombre de usuario...">
		</c:if>
		
		<label for="estadoReclamo">Estado del Reclamo:</label> 
		<select name="estadoReclamo">
			<option value="">Todos los estados</option>
			<option value="ingresado">Ingresado</option>
			<option value="en proceso">En proceso</option>
			<option value="finalizado">Finalizado</option>
		</select> 
		
		<input type="submit" value="Filtrar" style="margin-left: 20px">
		<input type="submit" value="Limpiar filtros" onclick="window.location.href='SvListarReclamos';" style="margin-left: 20px">
	</form>
	
	<!-- Botón ingresar Reclamo, visible solo para estudiantes -->
	<c:if test="${not esAnalista}">
	    <form action="SvIngresarReclamo" method="post" style="text-align: right; padding: 20px; margin-left: 4em">
	        <input type="submit" value="Ingresar Reclamo">
	    </form>
	</c:if>
	
	<!-- Botón Atrás -->
	<form action="${backUrl}" method="get">
		<input type="submit" value="Atrás" style="margin-left: 70px;">
	</form>
	
	<div class="container">
		<table>
			<thead>
				<tr>
					<th>Estudiante</th>
					<th>Fecha</th>
					<th>Título</th>
					<th>Detalle</th>
					<th>Evento</th>
					<th>Estado</th>
					<th>Ficha</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reclamos}" var="reclamo">
				    <tr>
				        <td>${reclamo.estudiante.nombres} ${reclamo.estudiante.apellidos}</td>
				        <td>${reclamo.fechaHoraReclamo}</td>
				        <td class="title" title="Título: ${reclamo.tituloReclamo}">${reclamo.tituloReclamo}</td>
				        <td class="detail" title="Detalle: ${reclamo.detalle}">${reclamo.detalle}</td>
				        <td title="Evento: ${reclamo.evento.tituloEvento}">${reclamo.evento.tituloEvento}</td>
				        <td>${reclamo.registroAccione.nombre}</td>
				        <td>
				            <form action="accion" method="get">
				                <input type="hidden" name="idReclamo" value="${reclamo.idReclamo}">
				                <input type="submit" value="Ver">
				            </form>
				             <!-- Mostrar botón de borrar solo si el usuario no es analista y el reclamo está 'Ingresado' -->
            				<c:if test="${not esAnalista and reclamo.registroAccione.nombre == 'Ingresado'}">
                				<form action="SvBorrarReclamo" method="post" onsubmit="return confirmarEliminacion();">
                    				<input type="hidden" name="idReclamo" value="${reclamo.idReclamo}">
                    				<input type="submit" value="Borrar">
                				</form>
           					 </c:if>
				        </td>
				    </tr>
				</c:forEach>						
			</tbody>
		</table>
	</div>
	<script>
	    function confirmarEliminacion() {
	        return confirm("¿Borrar reclamo?");
	    }
	</script>
</body>
</html>
