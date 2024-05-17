<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.entidades.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Usuarios</title>
<link rel="stylesheet" href="listados.css">
<script >
window.onload = function() {
    var tipoUsuarioSelect = document.getElementById("tipoUsuario");
    var generacionSelect = document.getElementById("generacionSelect");

    // Función para alternar la visibilidad del selector de generación
    function toggleGeneracionVisibility() {
        if (tipoUsuarioSelect.value === 'ESTUDIANTE') {
            generacionSelect.style.display = 'block';
        } else {
            generacionSelect.style.display = 'none';
        }
    }

    // Asignar la función al evento onchange del selector de tipo de usuario
    tipoUsuarioSelect.onchange = toggleGeneracionVisibility;

    // Ejecutar la función al cargar la página para establecer la visibilidad inicial
    toggleGeneracionVisibility();
};
</script>
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
	<h1>Lista de Usuarios</h1>

	<form action="SvFiltrarUsuarios" method="get" class="filter-form">
    	<input type="text" id="nombreUsuario" name="nombreUsuario" placeholder="Ingrese nombre o apellido.." style="margin-left: 5em">
    	<input type="submit" value="Buscar">
	
		<label for="tipoUsuario" style="margin-left: 1em">Tipo de usuario:</label> 
		<select name="tipoUsuario" id="tipoUsuario">
		<option value="todosLosUsuarios">Todos</option>
		<option value="ESTUDIANTE">Estudiante</option>
        <option value="ANALISTA">Analista</option>
        <option value="TUTOR">Tutor</option>
		
		</select>
		
		 <!-- Div para seleccionar la generación, visible solo cuando se selecciona Estudiante -->
	    <div id="generacionSelect" style="display: none;">
	        <label for="generacion">Generación:</label>
	        <select name="generacion" id="generacion">
	            <option value="">Seleccione una generación</option>
	            <% List<Generacion> generaciones = (List<Generacion>) request.getAttribute("generaciones");
	            if (generaciones != null) {
	                for (Generacion g : generaciones) { %>
	                    <option value="<%= g.getIdGeneracion() %>"><%= g.getNombre() %></option>
	                <% }
	            } %>
	        </select>
	    </div>


		<!-- Filtrado de ITR -->
		<label for="itr" style="margin-left: 1em">ITR:</label> <select name="itr" id="itr">
			<option value="todosLosItr">TODOS</option>

			<% List<Itr> listaItr = (List<Itr>) request.getAttribute("itrList"); %>
			<% if (listaItr != null && !listaItr.isEmpty()) { %>
			<% for (Itr itr : listaItr) { %>
			<option value="<%= itr.getIdItr() %>"><%= itr.getNombre() %></option>
			<% } %>
			<% } else { %>
			<!-- listaItr es nula o vacía -->
			<option value="">No hay elementos</option>
			<% } %>
		</select> 
		
		
		<!-- Filtrado de Validaciones -->
		<label for="validacionUsuario" style="margin-left: 1em">Validación: </label> 
		<select
			name="validacionUsuario" id="validacionUsuario">
			<option value="todasLasValidaciones">TODOS</option>
			<% List<ValidacionUsuario> validaciones = (List<ValidacionUsuario>) request.getAttribute("validacionesUsuario"); %>
			<% if ( validaciones != null) { %>
			<% for(ValidacionUsuario v : validaciones){ %>
			<option value="<%= v.getIdValidacion() %>"><%= v.getNombre() %></option>
			<% } 
			 } %>
		</select> 
		<input type="submit" value="Filtrar" style="margin-left: 20px">
	</form>

	<!-- Botón limpiar filtros -->
	<form action="SvListadoDeUsuario" method="get">
		<input type="submit" class="button" value="Limpiar Filtros">
	</form>

	<!-- Botón para regresar al menú del analista -->
	<form action="menuAnalista.jsp" method="get">
		<input type="submit" value="Cancelar">
	</form>
	
	<table border="1">
		<tr>
			<th>Estado</th>
			<th>Validación</th>
			<th>Nombre de Usuario</th>
			<th>Documento</th>
			<th>Correo Electrónico</th>
			<th>ITR</th>
			<th>Tipo</th>
			<th>Generación</th>
			<th>Acciones</th>
		</tr>
		<% List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios"); %>
		<% if (listaUsuarios != null && !listaUsuarios.isEmpty()) { %>
		<% for (Usuario usuario : listaUsuarios) { %>
		<tr>
		 	<td><%= usuario instanceof Estudiante ? ((Estudiante)usuario).getEstado().getNombre() : usuario instanceof Analista ? ((Analista)usuario).getEstado().getNombre() : ((Tutor)usuario).getEstado().getNombre() %></td>
			<td><%= usuario.getValidacionUsuario().getNombre() %></td>
			<td><%= usuario.getNombreUsuario() %></td>
			<td><%= usuario.getDocumento() %></td>
			<td><%= usuario instanceof Estudiante ? ((Estudiante)usuario).getMailInstitucional() : usuario instanceof Analista ? ((Analista)usuario).getMailInstitucional() : ((Tutor)usuario).getMailInstitucional() %></td>
			<td><%= usuario.getItr().getNombre() %></td>
			<td><%= usuario instanceof Estudiante ? "Estudiante" : usuario instanceof Analista ? "Analista" : "Tutor" %></td>
			<td>
				<% 
            if (usuario instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) usuario;
                if (estudiante.getGeneracion() != null) {
                    out.print(estudiante.getGeneracion().getNombre());
                } else {
                    out.print("No definida"); 
                }
            } else {
                out.print("N/A"); // Para usuarios que no son estudiantes
            }
            %>
			</td>
			<td><a href="datosPersonalesUsuario?id=<%= usuario.getIdUsuario() %>">Editar</a></td>
		</tr>
		<% } %>
		<% } else { %>
		<tr>
			<td colspan="5">No hay usuarios registrados.</td>
		</tr>
		<% } %>
	</table>
</body>
</html>
