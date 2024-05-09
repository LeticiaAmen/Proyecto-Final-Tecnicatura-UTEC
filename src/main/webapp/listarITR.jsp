<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.entidades.Itr"%>
<%@page import="com.entidades.Usuario"%>
<%@page import="com.entidades.Estado"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de ITRS</title>
<link rel="stylesheet" href="listados.css">
</head>
<body>
<% if (session.getAttribute("successMessage") != null) { %>
    <script>alert('<%= session.getAttribute("successMessage") %>');</script>
    <% session.removeAttribute("successMessage"); %>
<% } %>
<% if (session.getAttribute("errorMessage") != null) { %>
    <script>alert('<%= session.getAttribute("errorMessage") %>');</script>
    <% session.removeAttribute("errorMessage"); %>
<% } %>

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

					<form action="index.jsp">
						<input type="submit" class="button" value="Cerrar Sesión">
					</form>
				</div>
			</div>
		</div>
	</header>

	<div class="content">
		<h1>Lista de ITRs</h1>

		<form action="SvFiltrarItrs" method="get" class="filter-form">
			<label for="estado">Filtrar por Estado:</label> <select name="estado"
				id="estado">
				<option value="todosLosEstados">Todos</option>
				<%
				List<Estado> listaEstados = (List<Estado>) request.getAttribute("ListaEstados");%>
				<%if (listaEstados != null && !listaEstados.isEmpty()) {%>
				<%for (Estado e : listaEstados) {%>
				<option value="<%=e.getIdEstado()%>">
					<%=e.getNombre()%></option>
				<% } %>
			<% } else { %>
			<!-- listaEstado es nula o vacía -->
			<option value="">No hay elementos</option>
			<% } %>
		</select>  
		<input type="submit" value="Filtrar">
		</form>


		<table border="1">
			<tr>
				<th>Nombre</th>
				<th>Estado</th>
				<th>Acción</th>
			</tr>
			<%
			List<Itr> listaItrs = (List<Itr>) request.getAttribute("itrs");
			%>
			<%
			if (listaItrs != null &&!listaItrs.isEmpty()) {
			%>
			<%
			for (Itr itr : listaItrs) {
			%>
			<tr>
				<td><%=itr.getNombre()%></td>
				<td><%=itr.getEstado().getNombre()%></td>
				<td><a href="SvEditarITR?IdItr=<%=itr.getIdItr()%>">Editar</a></td>
			</tr>
			<%
			}
			%>
			<%
			} else {
			%>
			<tr>
				<td colspan="4">No hay Itrs registrados.</td>
			</tr>
			<%
			}
			%>
		</table>

		<form action="SvRegistroItr" class="create-itr-form">
			<input type="submit" value="Ingresar Nuevo ITR">
		</form>
	</div>

	<form action="menuAnalista.jsp" method="get" class="back-form">
		<input type="submit" value="Atrás">
	</form>
</body>
</html>
