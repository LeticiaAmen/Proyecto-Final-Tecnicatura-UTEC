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
        <label for="filtroUsuario">Filtrar por Usuario:</label>
        <input type="text" name="filtroUsuario" placeholder="Nombre de usuario...">

        <label for="estadoReclamo">Estado del Reclamo:</label>
        <select name="estadoReclamo">
            <option value="">Todos los estados</option>
            <option value="ingresado">Ingresado</option>
            <option value="en proceso">En proceso</option>
            <option value="finalizado">Finalizado</option>
        </select>

        <input type="submit" value="Filtrar">
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
            		<th>Estado del reclamo</th>
            		<th>Acciones</th>
        		</tr>
    		</thead>
    		<tbody>
        		<c:forEach items="${reclamos}" var="reclamo">
            		<tr>
                		<td>${reclamo.estudiante.nombres} ${reclamo.estudiante.apellidos}</td>
                		<td>${reclamo.fechaHoraReclamo}</td>
                		<td class="title">${reclamo.tituloReclamo}</td>
                		<td class="detail">${reclamo.detalle}</td>
                		<td>${reclamo.evento.tituloEvento}</td>
                		<td>${reclamo.registroAccione.nombre}</td>
                		<td>
                    		<a href="ResponderReclamo?id=${reclamo.idReclamo}">Ver</a>
                		</td>
            		</tr>
        		</c:forEach>
    		</tbody>
		</table>
    </div>
    <form action="menuAnalista.jsp" method="get">
    	<input type="submit" value="Atrás">
	</form>   
</body>
</html>
