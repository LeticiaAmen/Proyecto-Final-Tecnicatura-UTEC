<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Reclamos</title>
    <link rel="stylesheet" href="ListadoReclamos.css">
</head>
<body>
    <h1>Listado de Reclamos</h1>
    <form action="SvListarReclamos" method="get" class="filter-form">
        <c:if test="${esAnalista}"> <!-- Solo muestra el filtro si es un analista -->
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

        <input type="submit" value="Filtrar">
		
        <input type="submit" value="Limpiar filtros" onclick="window.location.href='SvListarReclamos';">	           
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
		                	<a href="ResponderReclamo?id=${reclamo.idReclamo}">Ver</a>
		            	</td>
		        	</tr>
		    	</c:forEach>
			</tbody>
		</table>   
    </div>
    <form action="${backUrl}" method="get">
    	<input type="submit" value="Atrás">
	</form>

</body>
</html>
