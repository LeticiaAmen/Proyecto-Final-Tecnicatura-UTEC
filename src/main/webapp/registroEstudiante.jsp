<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <a><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
    </div>
</header>

<h1>Registro de Estudiante</h1>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<form action="SvRegistroEstudiante" method="POST">

    <p>
        <label><strong>*Nombre:</strong></label>
    </p>
    <input type="text" name="nombre" value="${param.nombre}" required>
    
    <p>
        <label><strong>*Apellido:</strong></label>
    </p>
    <input type="text" name="apellido" value="${param.apellido}" required>
    
    <p>
        <label><strong>*Documento:</strong></label>
    </p>
    <input type="text" name="documento" value="${param.documento}" required>
    
    <p>
        <label><strong>*Nombre de Usuario:</strong></label>
    </p>
    <input type="text" name="nomUsuario" value="${param.nomUsuario}" required>
    
    <p>
        <label><strong>*Contraseña:</strong></label>
    </p>
    <input type="password" name="contrasenia" required>

    <p>
        <label><strong>*Mail Institucional:</strong></label>
    </p>
    <input type="text" name="mailInst" value="${param.mailInst}" required>
    
    <p>
        <label><strong>*Mail:</strong></label>
    </p>
    <input type="text" name="mail" value="${param.mail}" required>
    
    <p>
        <label><strong>*Telefono:</strong></label>
    </p>
    <input type="text" name="telefono" value="${param.telefono}" required>
    
    <p>
        <label><strong>*Género:</strong></label>
    </p>
    <select name="genero" required>
        <option value="" ${param.genero == '' ? 'selected' : ''}></option>
        <c:forEach var="genero" items="${generos}">
            <option value="${genero}" ${param.genero == genero ? 'selected' : ''}>${genero}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Departamento:</strong></label>
    </p>
    <select name="idDepartamento" required>
        <option value="" ${param.idDepartamento == '' ? 'selected' : ''}></option>
        <c:forEach var="departamento" items="${departamentos}">
            <option value="${departamento.idDepartamento}" ${param.idDepartamento == departamento.idDepartamento ? 'selected' : ''}>${departamento.nombre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Localidad:</strong></label>
    </p>
    <select name="idLocalidad" required>
        <option value="" ${param.idLocalidad == '' ? 'selected' : ''}></option>
        <c:forEach var="localidad" items="${localidades}">
            <option value="${localidad.idLocalidad}" ${param.idLocalidad == localidad.idLocalidad ? 'selected' : ''}>${localidad.nombre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*ITR:</strong></label>
    </p>
    <select name="idItr" required>
        <option value="" ${param.idItr == '' ? 'selected' : ''}></option>
        <c:forEach var="itr" items="${itrs}">
            <option value="${itr.idItr}" ${param.idItr == itr.idItr ? 'selected' : ''}>${itr.nombre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Semestre:</strong></label>
    </p>
    <select name="semestre" required>
        <option value="" ${param.semestre == '' ? 'selected' : ''}></option>
        <c:forEach var="semestre" items="${semestres}">
            <option value="${semestre}" ${param.semestre == semestre ? 'selected' : ''}>${semestre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Generación:</strong></label>
    </p>
    <select name="idGeneracion" required>
        <option value="" ${param.idGeneracion == '' ? 'selected' : ''}></option>
        <c:forEach var="generacion" items="${generaciones}">
            <option value="${generacion.idGeneracion}" ${param.idGeneracion == generacion.idGeneracion ? 'selected' : ''}>${generacion.nombre}</option>
        </c:forEach>
    </select>
    
    <p>
        <label><strong>*Fecha de Nacimiento:</strong></label>
    </p>
    <input type="date" name="fechaNacimiento" value="${param.fechaNacimiento}" required>
    
    <p style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
        <span style="background-color: white; padding: 0 10px;">*Campos obligatorios</span>
    </p>

    <input type="hidden" name="formSubmitted" value="true">
    <button type="submit">Enviar</button>
</form>

<form action="index.jsp" method="get">
    <button type="submit">Cancelar</button>
</form>

</body>
</html>
