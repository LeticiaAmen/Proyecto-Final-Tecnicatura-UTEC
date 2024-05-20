<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.entidades.Usuario"%>
<%@page import="com.entidades.RegistroAccione"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="listaAuxEstados.css">
     <link rel="stylesheet" href="formularios.css">
    <title>Lista Auxiliar</title>
</head>
<body>
    <header>
        <div>
            <a> <img alt="Logo de UTEC" src="images/utec-removebg-preview.png" />
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
 
	 <div style="margin-top: 5em" class="container">
	    <div class="column">
	        <h2>Estados ingresados en el sistema</h2>
	        <form method="POST" action="SvCambiaEstadoRegistro" onsubmit="return cambiarEstado();">
	            <label style="margin-top: 2em" for="registroSeleccionado">Seleccione un registro para cambiar el estado</label>
	            <select name="registroSeleccionado" required oninvalid="this.setCustomValidity('Por favor, seleccione un registro.')" oninput="this.setCustomValidity('')">
	            	<option value="" disabled selected hidden>Seleccione un registro...</option>
	                <c:forEach items="${registros}" var="registro">
	                    <option value="${registro.idRegistroAccion}">${registro.nombre} &#8594; ${registro.estado.nombre}</option>
	                </c:forEach>
	            </select>
	            <select name="nuevoEstado" required oninvalid="this.setCustomValidity('Por favor, seleccione un estado.')" oninput="this.setCustomValidity('')">
	            	<option value="" disabled selected hidden>Seleccione un estado...</option>
	                <option value="1">Activo</option>
	                <option value="2">Inactivo</option>
	            </select>
	            <button type="submit">Cambiar Estado</button>
	        </form>
	    </div>
	    <div class="column">
	        <h2 style="margin-bottom: 50px" >Registro de nuevo estado</h2>
	        
	        <!-- Muestra mensajes de error o éxito desde la sesión y luego limpia la sesión -->
		    <% if (request.getAttribute("errorMessage") != null) { %>
		        <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
		    <% } %>
		    <% if (request.getAttribute("successMessage") != null) { %>
		        <p style="color: green;"><%= request.getAttribute("successMessage") %></p>
		    <% } %>
	       
	        <form id="form-aux" action="SvListaAuxiliarEstado" method="POST" onsubmit="return confirmarEnvio();">
	        	<input type="text" name="nombre" placeholder="Ingrese aquí el nombre del nuevo estado" required oninvalid="this.setCustomValidity('Por favor, ingrese el nombre del estado.')" oninput="this.setCustomValidity('')">
	
	            <select name="idEstado" required oninvalid="this.setCustomValidity('Por favor, seleccione un estado.')" oninput="this.setCustomValidity('')">
	            	<option value="" disabled selected hidden>Seleccione un estado...</option>
	            	<c:forEach var="estado" items="${estados}">
	            		<option value="${estado.idEstado}">${estado.nombre}</option>
	            	</c:forEach>
	            </select>
	            
	            <input type="hidden" name="formSubmitted" value="true">
	            <input style="margin-top: 10px" type="submit" value="Enviar">
	        </form>       
	    </div>
	</div>
	
	<form action="menuAnalista.jsp" method="get">
    	<input type="submit" value="Volver">
    </form>
   
	<script>
		function confirmarEnvio() {
		    return confirm('¿Registrar nombre y estado?');
		}
	</script>

	<script>
	    function cambiarEstado() { 
	    	if(confirm("¿Cambiar estado?")) {
	    		
	        	alert("¡Estado modificado con éxito!");
	        	return true;
	    	} else {
		    	return false;
		    }
	   	} 		
	</script>
</body>
</html>
