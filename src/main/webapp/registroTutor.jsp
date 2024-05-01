<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro Tutor</title>
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

	<h1>Registro de Tutor</h1>
	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>

	<form action="SvRegistroTutor" method="POST">

		<p>
			<label>*Nombre:</label><input type="text" name="nombre" required>
		</p>

		<p>
			<label>*Apellido:</label><input type="text" name="apellido" required>
		</p>

		<p>
			<label>*Documento:</label><input type="text" name="documento" required>
		</p>

		<p>
			<label>*Nombre de Usuario:</label><input type="text" name="nomUsuario" required>
		</p>

		<p>
			<label>*Contraseña:</label><input type="text" name="contrasenia" required>
		</p>

		<p>
			<label>*Mail Institucional:</label><input type="text" name="mailInst" required>
		</p>

		<p>
			<label>*Mail:</label><input type="text" name="mail" required>
		</p>

		<p>
			<label>*Telefono:</label><input type="text" name="telefono" required>
		</p>
		<p>
			<label>*Departamento:</label> <select name="idDepartamento">
				<c:forEach var="departamento" items="${departamentos}">
					<option value="${departamento.idDepartamento}">${departamento.nombre}</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label>*Localidad:</label> <select name="idLocalidad">
				<c:forEach var="localidad" items="${localidades}">
					<option value="${localidad.idLocalidad}">${localidad.nombre}</option>
				</c:forEach>
			</select>
		</p>

		<p>
			<label>*Género:</label> <select name="genero">
				<c:forEach var="genero" items="${generos}">
					<option value="${genero}">${genero}</option>
				</c:forEach>
			</select>
		</p>

		<p>
			<label>*ITR:</label> <select name="idItr">
				<c:forEach var="itr" items="${itrs}">
					<option value="${itr.idItr}">${itr.nombre}</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label>*Área:</label> <select name="area">
				<c:forEach var="area" items="${areas}">
					<option value="${area}">${area}</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label>*Fecha de Nacimiento:</label> <input type="date"
				name="fechaNacimiento" required>
		</p>

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