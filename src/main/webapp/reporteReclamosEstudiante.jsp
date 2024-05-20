<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.entidades.Usuario"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="ListadoReclamos.css">
<title>Reportes del estudiante seleccionado</title>
</head>
<body>
    <header>
        <div>
            <a><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
            <%
            Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
            Usuario estudianteSeleccionado = (Usuario) request.getAttribute("estudianteSeleccionado");
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
    <h1 style="margin-bottom: 1em">Análisis de reclamos ingresados en el sistema</h1>

    <form style="margin-left: 25.7em" action="reporteReclamosEstudianteUsuario" method="get" class="filter-form">
        <input type="hidden" name="id" value="<%= estudianteSeleccionado.getIdUsuario() %>">
        <label for="anio">Año:</label>
        <select name="anio" required oninvalid="this.setCustomValidity('Por favor, seleccione un año.')" oninput="this.setCustomValidity('')">
            <option value="">Seleccionar un año</option>
            <c:forEach begin="2017" end="2024" var="anio">
                <option value="${anio}" <c:if test="${selectedAnio != null && selectedAnio == anio}">selected</c:if>>${anio}</option>
            </c:forEach>
        </select>
        <label style="margin-left: 1em" for="mes">Mes:</label>
        <select name="mes" required oninvalid="this.setCustomValidity('Por favor, seleccione un mes.')" oninput="this.setCustomValidity('')">
            <option value="">Seleccionar un mes</option>
            <option value="1" <c:if test="${selectedMes != null && selectedMes == 1}">selected</c:if>>Enero</option>
            <option value="2" <c:if test="${selectedMes != null && selectedMes == 2}">selected</c:if>>Febrero</option>
            <option value="3" <c:if test="${selectedMes != null && selectedMes == 3}">selected</c:if>>Marzo</option>
            <option value="4" <c:if test="${selectedMes != null && selectedMes == 4}">selected</c:if>>Abril</option>
            <option value="5" <c:if test="${selectedMes != null && selectedMes == 5}">selected</c:if>>Mayo</option>
            <option value="6" <c:if test="${selectedMes != null && selectedMes == 6}">selected</c:if>>Junio</option>
            <option value="7" <c:if test="${selectedMes != null && selectedMes == 7}">selected</c:if>>Julio</option>
            <option value="8" <c:if test="${selectedMes != null && selectedMes == 8}">selected</c:if>>Agosto</option>
            <option value="9" <c:if test="${selectedMes != null && selectedMes == 9}">selected</c:if>>Setiembre</option>
            <option value="10" <c:if test="${selectedMes != null && selectedMes == 10}">selected</c:if>>Octubre</option>
            <option value="11" <c:if test="${selectedMes != null && selectedMes == 11}">selected</c:if>>Noviembre</option>
            <option value="12" <c:if test="${selectedMes != null && selectedMes == 12}">selected</c:if>>Diciembre</option>
        </select>
        <input style="margin-left: 1em" type="submit" value="Filtrar">
    </form>
    
    <form action="reporteReclamosEstudianteUsuario" method="get">
        <input type="hidden" name="id" value="<%= estudianteSeleccionado.getIdUsuario() %>">
        <input type="submit" class="button" value="Limpiar Filtros">
    </form>
    
    <!-- Botón para regresar al menú de reportes -->
    <form action="SvReportes" method="get">
        <input style="margin-left: 14em" type="submit" value="Cancelar">
    </form>
    
    <div class="container" style="margin-top: 1em">
        <table>
            <thead>
                <tr>
                    <td colspan="5" style="text-align: center;">
    					<strong>Total de reclamos:</strong> 
    					<span style="font-size: 1.2em; font-weight: bold; color: white; background-color: #007bff; padding: 0.2em 0.5em; border-radius: 5px;">
							${fn:length(reclamos)}
    					</span>
					</td>
                </tr>
                <tr>
                    <th>Estudiante</th>
                    <th>Fecha</th>
                    <th>Título</th>
                    <th>Detalle</th>
                    <th>Evento</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reclamos}" var="reclamo">
                    <tr>
                        <td>${reclamo.estudiante.nombres} ${reclamo.estudiante.apellidos}</td>
                        <td>${reclamo.fechaHoraReclamo}</td>
                        <td>${reclamo.tituloReclamo}</td>
                        <td>${reclamo.detalle}</td>
                        <td>${reclamo.evento.tituloEvento}</td>
                    </tr>
                </c:forEach>
                <c:if test="${fn:length(reclamos) == 0}">
                    <tr>
                        <td colspan="5" style="text-align: center;">No hay reclamos registrados para el estudiante seleccionado.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
