<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page import="com.entidades.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Justificar Inasistencia</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
<header>
		
        <div>
            <a href="#"><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
            <%
            Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
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
                        <input type="submit" class="button" value="Cerrar Sesi�n">
                    </form>
                </div>
            </div>
        </div>
    </header>
	 <h2>Justificaci�n de Inasistencia</h2>
    <form action="SvJustificarInasistencia" method="post" onsubmit="return confirmarEliminacion();">
        
       <p>
			<label><strong>*Evento</strong></label>
		</p>
		<select name="idEvento" required oninvalid="this.setCustomValidity('Por favor, seleccione un evento.')" oninput="this.setCustomValidity('')">
			<option value="">Seleccione un evento...</option>
			<c:forEach var="evento" items="${eventos}">
				<option value="${evento.idEvento}">${evento.tituloEvento}</option>
			</c:forEach>
		</select>
        
        <p>
			<label><strong>*Informaci�n Adjunta</strong></label>
		</p>
		<textarea name="detalle" rows="6" cols="80" required></textarea>
		
		<p
			style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos
				obligatorios</span>
		</p>
		
		<%-- Recuperar el usuario logueado desde la sesion --%>
		<% 
            if (usuarioLogeado != null) {
        %>
		<input type="hidden" name="idEstudiante"
			value="<%= usuarioLogeado.getIdUsuario() %>" />
		<% 
            } 
        %>
		
		<!-- Campo oculto para indicar el env�o del formulario -->
		<input type="hidden" name="formSubmitted" value="true">
		
		
        <button type="submit">Enviar</button>
    </form>
    <form action="${backUrl}" method="get" onsubmit="return cancelar();">
		<button type="submit">Cancelar</button>
	</form>
	
	<script>
	    function confirmarEliminacion() {
	        if(confirm("�Enviar justificaci�n?")) {
		        alert("�Justificaci�n enviada con �xito!")
		        return true;
		    }else {
			    return false;
			}       
	    }
	</script>
	<script>
	    function cancelar() {
	        return confirm("�Cancelar y volver al men�?");
	    }
	</script>
	
</body>
</html>