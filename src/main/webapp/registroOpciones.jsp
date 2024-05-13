<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
	<header>
		<div>
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
			</a>
			<h1>Registro de Usuario</h1>
		</div>

	</header>

	<h1 style="margin-top: 2em">Rol de usuario</h1>

	<form action="SvRegistroOpciones" method="Post" name="tipoRegistro">
		<p
			style="text-align: center; margin-top: 20px; display: flex; align-items: center; justify-content: center;">
			<select name="tipoRegistro">
				<option value="ANALISTA">Analista</option>
				<option value="TUTOR">Tutor</option>
				<option value="ESTUDIANTE">Estudiante</option>
			</select>
		</p>

		<button type="submit">Enviar</button>
	</form>

	<form action="index.jsp" method="get">
		<button type="submit">Cancelar</button>
	</form>

</body>
</html>