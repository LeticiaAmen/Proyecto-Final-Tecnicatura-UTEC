<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro Estudiante</title>
    <link rel="stylesheet" href="formularios.css">
    <script type="text/javascript">
    function confirmarEnvio() {
        return confirm("¿Está seguro de que desea enviar el formulario?");
    }
</script>
<script type="text/javascript">
	function confirmarEnvio() {
		return confirm("¿Está seguro de que desea enviar el formulario?");
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
                var option = document.createElement('option'); // Crear una nueva opción en blanco
                option.value = '';
                option.text = 'Seleccione una opción';
                localidadesSelect.appendChild(option); // Agregar la opción en blanco al principio
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
        <a><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
    </div>
</header>

<h1>Registro de Estudiante</h1>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<form action="SvRegistroEstudiante" method="POST" onsubmit="return confirmarEnvio();">

    <p>
        <label><strong>*Nombre:</strong></label>
    </p>
    <input type="text" name="nombre" value="${nombre}" required>
    
    <p>
        <label><strong>*Apellido:</strong></label>
    </p>
    <input type="text" name="apellido" value="${apellido}" required>
    
    <p>
        <label><strong>*Documento:</strong></label>
    </p>
    <input type="text" name="documento" value="${documento}" required>
    
    <p>
        <label><strong>*Nombre de Usuario:</strong></label>
    </p>
    <input type="text" name="nomUsuario" value="${nomUsuario}" required>
    
    <p>
        <label><strong>*Contraseña:</strong></label>
    </p>
    <input type="password" name="contrasenia" value="${contrasenia}" required>

    <p>
        <label><strong>*Mail Institucional:</strong></label>
    </p>
    <input type="text" name="mailInst" value="${mailInst}" required>
    
    <p>
        <label><strong>*Mail:</strong></label>
    </p>
    <input type="text" name="mail" value="${mail}" required>
    
    <p>
        <label><strong>*Teléfono:</strong></label>
    </p>
    <input type="text" name="telefono" value="${telefono}" required>
    
    <p>
        <label><strong>*Género:</strong></label>
    </p>
    <select name="genero" required>
        <option value="" <c:if test="${genero eq ''}">selected</c:if>></option>
        <c:forEach var="genero" items="${generos}">
            <option value="${genero}" <c:if test="${genero eq generoSeleccionado}">selected</c:if>>${genero}</option>
        </c:forEach>
    </select>
    
 		<p>
			<strong>Departamento:</strong>
		</p>
		<select name="idDepartamento"
			onchange="obtenerLocalidades(this.value)">
			<option value="">Seleccione una opción</option>
			<c:forEach var="departamento" items="${departamentos}">
				<option value="${departamento.idDepartamento}"
					${departamento.idDepartamento == idDepartamentoUsuario ? 'selected' : ''}>
					${departamento.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<strong>Localidad:</strong>
		</p>
		<select name="idLocalidad" id="localidades">
			<option value="">Seleccione una opción</option>
			<!-- Agregar esta línea -->
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
        <option value="" <c:if test="${idItr eq ''}">selected</c:if>></option>
        <c:forEach var="itr" items="${itrs}">
            <option value="${itr.idItr}" <c:if test="${itr.idItr eq idItr}">selected</c:if>>${itr.nombre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Semestre:</strong></label>
    </p>
    <select name="semestre" required>
        <option value="" <c:if test="${semestre eq ''}">selected</c:if>></option>
        <c:forEach var="semestre" items="${semestres}">
            <option value="${semestre}" <c:if test="${semestre eq semestreSeleccionado}">selected</c:if>>${semestre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Generación:</strong></label>
    </p>
    <select name="idGeneracion" required>
        <option value="" <c:if test="${idGeneracion eq ''}">selected</c:if>></option>
        <c:forEach var="generacion" items="${generaciones}">
            <option value="${generacion.idGeneracion}" <c:if test="${generacion.idGeneracion eq idGeneracion}">selected</c:if>>${generacion.nombre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Fecha de Nacimiento:</strong></label>
    </p>
    <input type="date" name="fechaNacimiento" value="${fechaNacimiento}" required>
    
    <p style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
        <span style="background-color: white; padding: 0 10px;">*Campos obligatorios</span>
    </p>

    <input type="hidden" name="formSubmitted" value="true">
    <button type="submit">Enviar</button>
</form>

<form action="index.jsp" method="get">
    <button type="submit">Volver</button>
</form>

</body>
</html>
