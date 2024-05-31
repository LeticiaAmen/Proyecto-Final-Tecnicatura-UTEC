<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.entidades.Usuario"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listado de Justificaciones</title>
<link rel="stylesheet" href="ListadoReclamos.css">
</head>
<body>
	<header>
		<div>
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
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

	<h1>Listado de Justificaciones</h1>
<form action="SvListarJustificaciones" method="get" class="filter-form">	
		<c:if test="${esAnalista}">
			<!-- Solo muestra el filtro si es un analista -->
			<label for="filtroUsuario">Filtrar por Usuario:</label>
			<input type="text" name="filtroUsuario" placeholder="Nombre de usuario...">
		</c:if>
				
		<label for="estadoJustificacion">Estado del Reclamo:</label>
        <select name="estadoJustificacion">
            <option value="">Todos los estados</option>
            <c:forEach items="${estadosActivos}" var="estado">
    			<option value="${estado.idRegistroAccion}">${estado.nombre}</option>
			</c:forEach>
        </select>
		
		<input type="submit" value="Filtrar" style="margin-left: 20px">
		<input type="submit" value="Limpiar filtros" onclick="window.location.href='SvListarJustificaciones';" style="margin-left: 20px">
	</form>
	
	<!-- Botón ingresar Justificacion, visible solo para estudiantes -->
	<c:if test="${not esAnalista}">
	    <form action="SvJustificarInasistencia" method="post" style="text-align: right; padding: 20px; margin-left: 4em">
	        <input type="submit" value="Ingresar Justificacion">
	    </form>
	</c:if>
	
	<!-- Botón Volver -->
	<form action="${backUrl}" method="get">
		<input type="submit" value="Volver" style="margin-left: 25em;">
	</form>
	
	<div class="container">
		<table>
			<thead>
				<tr>
					<th>Estudiante</th>
					<th>Fecha</th>
				
					<th>Detalle</th>
					<th>Evento</th>
					<th>Estado</th>
					<th>Ficha</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${justificaciones}" var="justificacion">
				    <tr>
				        <td>${justificacion.estudiante.nombres} ${justificacion.estudiante.apellidos}</td>
				        <td>${justificacion.fechaHora}</td>
				       
				        <td class="detail" ${justificacion.detalle}>${justificacion.detalle}</td>
				        <td ${justificacion.evento.tituloEvento}>${justificacion.evento.tituloEvento}</td>
				        <td>${justificacion.registroAccione.nombre}</td>
				        <td>
				            <form action="accion" method="get">
				                <input type="hidden" name="idJustificacion" value="${justificacion.idJustificacion}">
				                <input type="submit" value="Ver">
				            </form>
				             <!-- Mostrar botón de borrar solo si el usuario no es analista y el reclamo está 'Ingresado' -->
            				<c:if test="${not esAnalista and justificacion.registroAccione.nombre == 'Ingresado'}">
                				<form action="SvBorrarJustificacion" method="post" onsubmit="return confirmarEliminacion();">
                    				<input type="hidden" name="idJustificacion" value="${justificacion.idJustificacion}">
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
	       if(confirm("¿Borrar Justificación?")) {
				alert("¡Justificación borrada con éxito!")
				return true;
	       }else {
				return false;
			}
	    }
	</script>
</body>
</html>
