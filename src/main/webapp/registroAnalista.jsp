<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registro Analista</title>
<link rel="stylesheet" href="formularios.css">
<script type="text/javascript">
	function confirmarEnvio() {
		return confirm("�Est� seguro de que desea enviar el formulario?");
	}
</script>
<script type="text/javascript">
    function obtenerLocalidades(departamentoId) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'obtenerLocalidades?departamentoId=' + departamentoId, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var localidadesSelect = document.getElementById('localidades');
                localidadesSelect.innerHTML = ''; // Limpiar la lista actual de localidades
                var option = document.createElement('option'); // Crear una nueva opci�n en blanco
                option.value = '';
                option.text = 'Seleccione una opci�n';
                localidadesSelect.appendChild(option); // Agregar la opci�n en blanco al principio
                localidadesSelect.innerHTML += xhr.responseText; // Agregar las localidades filtradas
            }
        };
        xhr.send();
    }
</script>
</head>
<body>
	<header>
		<div>
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
			</a>
		</div>
	</header>

	<h1>Registro de Analista</h1>
	<c:if test="${not empty error}">
		<div class="error">${error}</div>
	</c:if>

	<form action="SvRegistroAnalista" method="POST"
		onsubmit="return confirmarEnvio();">
		<p>
			<label><strong>*Nombre:</strong></label>
		</p>
		<input type="text" name="nombre" value="${nombre}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Apellido:</strong></label>
		</p>
		<input type="text" name="apellido" value="${apellido}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Documento:</strong></label>
		</p>
		<input type="text" name="documento" value="${documento}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Nombre de Usuario:</strong></label>
		</p>
		<input type="text" name="nomUsuario" value="${nomUsuario}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Contrase�a:</strong></label>
		</p>
		<input type="password" name="contrasenia" value="${contrasenia}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Mail Institucional:</strong></label>
		</p>
		<input type="text" name="mailInst" value="${mailInst}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Mail:</strong></label>
		</p>
		<input type="text" name="mail" value="${mail}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*Tel�fono:</strong></label>
		</p>
		<input type="text" name="telefono" value="${telefono}" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">

		<p>
			<label><strong>*G�nero:</strong></label>
		</p>
		<select name="genero" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">
			<option value="" selected></option>
			<c:forEach var="genero" items="${generos}">
				<option value="${genero}"
					<c:if test="${genero eq generoSeleccionado}">selected</c:if>>${genero}</option>
			</c:forEach>
		</select>

		<p>
			<strong>Departamento:</strong>
		</p>
		<select name="idDepartamento"
			onchange="obtenerLocalidades(this.value)" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">
			<option value="">Seleccione una opci�n</option>
			<c:forEach var="departamento" items="${departamentos}">
				<option value="${departamento.idDepartamento}"
					${departamento.idDepartamento == idDepartamentoUsuario ? 'selected' : ''}>
					${departamento.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<strong>Localidad:</strong>
		</p>
		<select name="idLocalidad" id="localidades" required oninvalid="this.setCustomValidity('Completa este campo')" oninput="this.setCustomValidity('')">
			<option value="">Seleccione una opci�n</option>
			<!-- Agregar esta l�nea -->
			<c:forEach var="localidad" items="${localidades}">
				<option value="${localidad.idLocalidad}"
					${localidad.idLocalidad == idLocalidadUsuario ? 'selected' : ''}>
					${localidad.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<label><strong>*ITR:</strong></label>
		</p>
		<select name="idItr" required>
			<option value="" selected></option>
			<c:forEach var="itr" items="${itrs}">
				<option value="${itr.idItr}"
					<c:if test="${itr.idItr eq idItr}">selected</c:if>>${itr.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<label><strong>*Fecha de Nacimiento:</strong></label>
		</p>
		<input type="date" name="fechaNacimiento" value="${fechaNacimiento}"
			required>

		<p
			style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos
				obligatorios</span>
		</p>

		<!-- Campo oculto para indicar el env�o del formulario -->
		<input type="hidden" name="formSubmitted" value="true">
		<button type="submit">Enviar</button>
	</form>

	<form action="index.jsp" method="get">
		<button type="submit">Volver</button>
	</form>

</body>
</html>
