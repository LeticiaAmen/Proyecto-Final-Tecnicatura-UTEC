<%@page import="com.entidades.RegistroAccione"%>
<%@page import="com.entidades.Usuario"%>
<%@page import="com.entidades.Reclamo"%>
<%@page import="com.entidades.Justificacion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalles de la Acción</title>
    <link rel="stylesheet" href="verReclamo.css">
</head>
<body>
    <header>
        <div>
            <a><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
            <% Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
            Boolean esAnalista = (Boolean) request.getSession().getAttribute("esAnalista");
            if (esAnalista == null) {
                esAnalista = Boolean.FALSE;
            } %>
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
    <h1 style="margin-top: 1em; margin-right: 24em">Detalles de la Acción</h1>
    <div style="margin-left: 13em">
        <ul>
            <c:choose>
                <c:when test="${tipo == 'reclamo'}">
                    <li><strong>Título del Reclamo:</strong> ${reclamo.tituloReclamo}</li>
                    <li><strong>Título del Evento:</strong> ${reclamo.evento.tituloEvento}</li>
                    <li><strong>Detalles del Evento:</strong> ${reclamo.evento.informacion}</li>
                    <li><strong>Créditos del Evento:</strong> ${reclamo.evento.creditos}</li>
                    <li><strong>Modalidad del Evento:</strong> ${reclamo.evento.modalidad}</li>
                    <li><strong>Semestre del Evento:</strong> ${reclamo.evento.semestre}</li>
                    <li><strong>Tipo de Evento:</strong> ${reclamo.evento.tipoEvento.nombre}</li>
                    <li><strong>Detalle ingresado por el estudiante:</strong> 
                        <div style="margin-right: 14em" id="reclamoEstudiante">
                            <ul>
                                <li>${reclamo.detalle}</li>
                            </ul>
                        </div>
                    </li>
                </c:when>
                <c:when test="${tipo == 'justificacion'}">
                    <li><strong>Fecha de la Justificación:</strong> ${justificacion.fechaHora}</li>
                    <li><strong>Título del Evento:</strong> ${justificacion.evento.tituloEvento}</li>
                    <li><strong>Detalles del Evento:</strong> ${justificacion.evento.informacion}</li>
                    <li><strong>Créditos del Evento:</strong> ${justificacion.evento.creditos}</li>
                    <li><strong>Modalidad del Evento:</strong> ${justificacion.evento.modalidad}</li>
                    <li><strong>Semestre del Evento:</strong> ${justificacion.evento.semestre}</li>
                    <li><strong>Tipo de Evento:</strong> ${justificacion.evento.tipoEvento.nombre}</li>
                    <li><strong>Detalle ingresado por el estudiante:</strong> 
                        <div style="margin-right: 14em" id="reclamoEstudiante">
                            <ul>
                                <li>${justificacion.detalle}</li>
                            </ul>
                        </div>
                    </li>
                </c:when>
            </c:choose>
        </ul>
    </div>
    <h2 style="margin-left: 10em; margin-top: 2em">Acciones Registradas</h2>
    <c:choose>
        <c:when test="${tipo == 'reclamo'}">
            <c:if test="${not empty reclamo.acciones}">
                <table>
                    <tr>
                        <th>Fecha</th>
                        <th>Analista</th>
                        <th>Detalle</th>
                        <th>Estado</th>
                    </tr>
                    <c:forEach items="${reclamo.acciones}" var="accion">
                        <tr>
                            <td>${accion.fechaHora}</td>
                            <td>${accion.analista.nombres} ${accion.analista.apellidos}</td>
                            <td>${accion.detalle}</td>
                            <td>${accion.registroAccion.nombre}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty reclamo.acciones}">
                <p style="margin-left: 15em">No hay acciones registradas.</p>
            </c:if>
        </c:when>
        <c:when test="${tipo == 'justificacion'}">
            <c:if test="${not empty justificacion.acciones}">
                <table>
                    <tr>
                        <th>Fecha</th>
                        <th>Analista</th>
                        <th>Detalle</th>
                        <th>Estado</th>
                    </tr>
                    <c:forEach items="${justificacion.acciones}" var="accion">
                        <tr>
                            <td>${accion.fechaHora}</td>
                            <td>${accion.analista.nombres} ${accion.analista.apellidos}</td>
                            <td>${accion.detalle}</td>
                            <td>${accion.registroAccion.nombre}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty justificacion.acciones}">
                <p style="margin-left: 15em">No hay acciones registradas.</p>
            </c:if>
        </c:when>
    </c:choose>
    
    <div style="margin-left: 14.5em">
        <c:if test="${esAnalista}">
            <h2 style="margin-left: 10px; margin-top: 1.5em">Registrar acción</h2>
            <form action="guardarAccion" method="POST" onsubmit="return confirmarEnvio();">
                <label for="nuevoEstado">Estado:</label>
                <select name="nuevoEstado">
                    <c:forEach items="${estados}" var="estado">
                        <option value="${estado.idRegistroAccion}" 
                                ${estado.idRegistroAccion == reclamo.registroAccione.idRegistroAccion ? 'selected' : ''}>
                            ${estado.nombre}
                        </option>
                    </c:forEach>
                </select>
                <br><br>
                <input type="hidden" name="id" value="${tipo == 'reclamo' ? reclamo.idReclamo : justificacion.idJustificacion}">
                <input type="hidden" name="tipo" value="${tipo}">
                <label for="detalle">Detalle de la Acción:</label><br>
                <textarea id="detalle" name="detalle" rows="4" cols="50" required oninvalid="this.setCustomValidity('Por favor, ingrese un detalle.')"></textarea><br>
                <input type="submit" name="accion" value="Enviar" class="submit-btn">
            </form>
        </c:if>
    </div>
    
    <c:if test="${not esAnalista and reclamo.registroAccione.nombre == 'Ingresado'}">
        <form action="SvEditarReclamo" method="get">
            <input type="hidden" name="id" value="${reclamo.idReclamo}">
            <input style="margin-left: 14em" type="submit" value="Modificar Reclamo">
        </form>
    </c:if>
    
    <form action="${tipo == 'reclamo' ? 'SvListarReclamos' : 'SvListarJustificaciones'}" method="get" style="margin-left: 75em">
        <input type="submit" value="Volver">
    </form>
    
    <script>
        function confirmarEnvio() {
            if (confirm("¿Actualizar estado y detalle de la acción? Se enviará un email al estudiante con los detalles modificados.")) {
                alert('¡Acción registrada con éxito! Se envió un e-mail al estudiante.');
                return true;
            } else {
                return false;
            }
        }
    </script>
</body>
</html>
