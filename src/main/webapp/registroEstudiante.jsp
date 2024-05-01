<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro Estudiante</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
<header>
		<div>
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
			</a>
		</div>
	</header>
	
	<h1>Registro de Estudiante</h1>
			
	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>

	<form action="SvRegistroEstudiante" method="POST">

		<p>
			<label>*Nombre:</label>
		</p>
		<input type="text" name="nombre" required>
		
		<p>
			<label>*Apellido:</label>
		</p>
		<input type="text" name="apellido" required>
		
		<p>
			<label>*Documento:</label>
		</p><input type="text" name="documento" required>
		
		<p>
			<label>*Nombre de Usuario:</label>
		</p><input type="text" name="nomUsuario" required>
		
		<p>
			<label>*Contraseña:</label></p>
		<input type="password" name="contrasenia"nrequired>

		<p>
			<label>*Mail Institucional:</label>
		</p><input type="text" name="mailInst" required>
		
		<p>
			<label>*Mail:</label>
		</p>
			<input type="text" name="mail" required>
		<p>
			<label>*Telefono:</label>
		</p>
		<input type="text" name="telefono" required>
		
		<p>
			<label>*Departamento:</label>
		</p> 
		<select name="idDepartamento">
			<c:forEach var="departamento" items="${departamentos}">
				<option value="${departamento.idDepartamento}">${departamento.nombre}</option>
			</c:forEach>
		</select>
		
		<p>
			<label>*Localidad:</label>
		</p> 
		<select name="idLocalidad">
			<c:forEach var="localidad" items="${localidades}">
				<option value="${localidad.idLocalidad}">${localidad.nombre}</option>
			</c:forEach>
		</select>
		
		<p>
			<label>*Género:</label>
		</p>
		<select name="genero">
			<c:forEach var="genero" items="${generos}">
				<option value="${genero}">${genero}</option>
			</c:forEach>
		</select>
		
		<p>
			<label>*ITR:</label>
		</p> <select name="idItr">
			<c:forEach var="itr" items="${itrs}">
				<option value="${itr.idItr}">${itr.nombre}</option>
			</c:forEach>
		</select>
		
		<p>
			<label>*Semestre:</label>
		</p> <select name="semestre">
			<c:forEach var="semestre" items="${semestres}">
				<option value="${semestre}">${semestre}</option>
			</c:forEach>
		</select>
		
		<p>
			<label>*Generación:</label>
		</p> <select name="generacion">
			<c:forEach var="generacion" items="${generaciones}">
				<option value="${generacion}">${generacion}</option>
			</c:forEach>
		</select>
		
		<p>
			<label>*Fecha de Nacimiento:</label>
		</p>
		<input type="date" name="fechaNacimiento" required>
		
		<p style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos obligatorios</span>
		</p>

		<!-- Campo oculto para indicar el envío del formulario -->
		<input type="hidden" name="formSubmitted" value="true">
		<button type="submit">Enviar</button>
	</form>

	<form action="index.jsp" method="get">
		<button type="submit">Cancelar</button>
	</form>
	
</body>
</html>