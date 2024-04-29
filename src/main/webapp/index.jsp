<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%

HttpSession sesion = request.getSession(false);
 String registroExitoso = null;
 if (sesion != null) {
     registroExitoso = (String) sesion.getAttribute("registroExitoso");
     sesion.removeAttribute("registroExitoso"); // Remover atributo para que no se muestre nuevamente
 }
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="login.css">
<title>Gestión de Usuarios</title>

<% if (registroExitoso != null) { %>
    <script>
        alert("<%= registroExitoso %>");
    </script>
<% } %>
</head>
<body>
	<header>
		<div>
			<a>
				<img alt="Logo de UTEC" src="images/utec-removebg-preview.png"/>
			</a>
			<h1>Ingreso a plataforma virtual</h1>
		</div>

	</header>
	<div class="container">
		<c:if test="${not empty error}">
    		<div style="color:red; text-align:center; margin-bottom: 0px;">
        		${error}
    		</div>
		</c:if>

		<form id="form" action="LoginServlet" method="POST">
				<label id="label=usuario" for="nomUsuario"><b>Nombre de usuario: </b></label> 
				<input id="input-1" type="text" placeholder="Ingrese nombre de usuario" name="nomUsuario" required /> 
					
				<label id="label-password" for="psw"><b>Contraseña:</b></label>
				<input id="input-2" type="password" placeholder="Ingrese contraseña" name="psw" required />

				<button class="button" type="submit">Iniciar sesión</button>
		</form>
	</div>

	<section>
		<div class="container2">
			<h3>¿No tienes una cuenta? <a href="registroOpciones.jsp">Regístrate aquí</a></h3>
		</div>
	</section>
	
	 <footer>
    	<p>UNIVERSIDAD TECNOLÓGICA @ 2023 - All rights reserved. Teléfono (+598) 2603 8832 | consultas@utec.edu.uy</p>
    </footer>
</body>
</html>
