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
	<h1>Listado de Reclamos</h1>
	<form action="SvListarReclamos" method="get" class="filter-form">
		<c:if test="${esAnalista}">
			<!-- Solo muestra el filtro si es un analista -->
			<label for="filtroUsuario">Filtrar por Usuario:</label>
			<input type="text" name="filtroUsuario"
				placeholder="Nombre de usuario...">
		</c:if>
		<label for="estadoReclamo">Estado del Reclamo:</label> <select
			name="estadoReclamo">
			<option value="">Todos los estados</option>
			<option value="ingresado">Ingresado</option>
			<option value="en proceso">En proceso</option>
			<option value="finalizado">Finalizado</option>
		</select> <input type="submit" value="Filtrar"> <input type="submit"
			value="Limpiar filtros"
			onclick="window.location.href='SvListarReclamos';">
	</form>
	
	<!-- Botón ingresar Reclamo, visible solo para estudiantes -->
	<c:if test="${not esAnalista}">
	    <form action="SvIngresarReclamo" method="post" style="text-align: right; padding: 20px;">
	        <input type="submit" value="Ingresar Reclamo">
	    </form>
	</c:if>
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
	<form action="${backUrl}" method="get">
		<input type="submit" value="Atrás">
	</form>
	<script>
	    function confirmarEliminacion() {
	        return confirm("¿Eliminar reclamo?");
	    }
	</script>
</body>
</html>
