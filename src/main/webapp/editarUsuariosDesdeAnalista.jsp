<%@page import="com.entidades.Usuario"%>
<%@page import="com.entidades.Estudiante, com.entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Datos Personales</title>
<link rel="stylesheet" href="formularios.css">
<script type="text/javascript">
        function validarFormulario() {
            var nombre = document.forms["editarFormulario"]["nombre"].value;
            var nombrePattern = /^[a-zA-Z\s]{3,20}$/;

            if (!nombrePattern.test(nombre)) {
                alert("El nombre debe tener entre 3 y 20 caracteres y no contener números.");
                return false;
            }

            return true;
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
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
			</a>
			<%
				Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
			%>
			<div id="usuario-dropdown">
				<h1><%=usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos()%></h1>
				<div id="dropdown-content">
					<form action="datosPersonales" method="get">
						<input type="hidden" name="id"
							value="<%= usuarioLogeado.getIdUsuario() %>"> <input
							type="submit" class="button" value="Datos Personales">
					</form>
					<form action="LogoutServlet" method="post">
						<input type="submit" class="button" value="Cerrar Sesión">
					</form>
				</div>
			</div>
		</div>
	</header>
	<h1>Editar Usuario</h1>

	<%
        Usuario usuarioAEditar = (Usuario) request.getAttribute("usuarioAEditar");
        if (usuarioAEditar == null) {
            response.sendRedirect("errorPage.jsp"); // Redireccionar a una página de error si el usuario no existe.
            return;
        }
        boolean isStudent = usuarioAEditar instanceof Estudiante;
        Estudiante student = isStudent ? (Estudiante) usuarioAEditar : null;
    %>
      <%-- Mostrar mensajes de error y éxito --%>
    <c:if test="${not empty param.mensajeError}">
        <div class="alert alert-danger" role="alert" style ="color: red;">
            ${param.mensajeError}
        </div>
    </c:if>
    <c:if test="${not empty param.mensajeExito}">
        <div class="alert alert-success" role="alert" style = "color: blue;">
            ${param.mensajeExito}
        </div>
    </c:if>

	<form>
		<p>
			<strong>*Nombre de Usuario:</strong>
		</p>
		<input type="text" name="nomUsuario"
			value="<%= usuarioAEditar.getNombreUsuario() %>" disabled="disabled"
			style="background-color: #e9e9e9; color: #a1a1a11;">
		<p>
			<strong>*Contraseña:</strong>
		</p>
		<input type="password" name="passUsuario"
			value="<%= usuarioAEditar.getHashContraseña() %>" disabled="disabled"
			style="background-color: #e9e9e9; color: #a1a1a11;">
	</form>

	<form action="datosPersonalesUsuario" method="post"
		onsubmit="return confirmarModificacion();">
		<input type="hidden" name="userId"
			value="<%= usuarioAEditar.getIdUsuario() %>">

		<p>
			<strong>*Documento: </strong>
		</p>
		<input type="text" name="documento"
			value="<%= usuarioAEditar.getDocumento() %>" required>
		<p>
			<strong>*Nombre: </strong>
		</p>
		<input type="text" name="nombre"
			value="<%= usuarioAEditar.getNombres() %>" required>
		<p>
			<strong>*Apellido: </strong>
		</p>
		<input type="text" name="apellido"
			value="<%= usuarioAEditar.getApellidos() %>" required>
		<p>
			<strong>*Mail Institucional: </strong>
		</p>
		<input type="text" name="mailInst"
			value="<%= request.getAttribute("mailInstitucional") %>" required>
		<p>
			<strong>*Mail: </strong>
		</p>
		<input type="text" name="mail" value="<%= usuarioAEditar.getMail() %>"
			required>
		<p>
			<strong>*Telefono: </strong>
		</p>
		<input type="text" name="telefono"
			value="<%= usuarioAEditar.getTelefono() %>" required>

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
			<label><strong>*Género:</strong></label>
		</p>
		<select name="genero">
			<option value="M"
				<%= "M".equals(request.getAttribute("generoActual")) ? "selected" : "" %>>Masculino</option>
			<option value="F"
				<%= "F".equals(request.getAttribute("generoActual")) ? "selected" : "" %>>Femenino</option>
			<option value="O"
				<%= "O".equals(request.getAttribute("generoActual")) ? "selected" : "" %>>Otros</option>
		</select>

		<p>
			<label><strong>*ITR:</strong></label>
		</p>
		<select name="idItr">
			<c:forEach var="itr" items="${itrs}">
				<option value="${itr.idItr}"
					${itr.idItr == idItrUsuario ? 'selected' : '' }>
					${itr.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<label><strong>*Fecha de Nacimiento:</strong></label>
		</p>
		<input type="date" name="fechaNacimiento"
			value="<%= request.getAttribute("fechaNacimientoStr") %>" required>

		<%-- Mostrar campos específicos de estudiantes si el usuario es un estudiante --%>
		<c:if test="${isStudent}">
			<p>
				<label><strong>*Semestre:</strong></label>
			</p>
			<select name="semestre">
				<c:forEach begin="1" end="8" var="num">
					<option value="${num}" ${num == semestreActual ? 'selected' : ''}>${num}</option>
				</c:forEach>
			</select>

			<p>
				<label><strong>*Generación:</strong></label>
			</p>
			<select name="generacion">
				<c:forEach items="${generaciones}" var="generacion">
					<option value="${generacion.idGeneracion}"
						${generacion.idGeneracion == generacionActual ? 'selected' : ''}>
						${generacion.nombre}</option>
				</c:forEach>
			</select>
		</c:if>

		<%-- Mostrar campos específicos de tutor si el usuario es un tutor --%>
		<c:if test="${isTutor}">
			<p>
				<label><strong>*Rol:</strong></label>
			</p>
			<select name="rol">
				<c:forEach items="${roles}" var="rol">
					<option value="${rol.idRol}"
						${rol.idRol == rolActual ? 'selected' : ''}>${rol.nombre}</option>
				</c:forEach>
			</select>

			<p>
				<label><strong>*Área:</strong></label>
			</p>
			<select name="area">
				<c:forEach items="${areas}" var="area">
					<option value="${area.idArea}"
						${area.idArea == areaActual ? 'selected' : ''}>${area.nombre}</option>
				</c:forEach>
			</select>
		</c:if>

		<%-- Validación del usuario --%>
		<p>
			<label for="estadoUsuario"><strong>*Validación: </strong></label>
		</p>

		<select name="estadoUsuario" id="estadoUsuario">
			<option value="1"
				<%= usuarioAEditar.getValidacionUsuario().getIdValidacion() == 1 ? "selected" : "" %>>VALIDADO</option>
			<option value="2"
				<%= usuarioAEditar.getValidacionUsuario().getIdValidacion() == 2 ? "selected" : "" %>>SIN
				VALIDAR</option>
		</select>

		<%-- Estado del usuario --%>
		<p>
			<label for="usuarioEstado"><strong>*Estado:</strong></label>
		</p>
		<select name="estadoUsuarioId" id="estadoUsuario">
			<c:forEach items="${estados}" var="estado">
				<option value="${estado.idEstado}"
					${usuarioAEditar.getEstado().getIdEstado() == estado.idEstado ? "selected" : ""}>
					${estado.nombre}</option>
			</c:forEach>
		</select>


		<p
			style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos
				obligatorios</span>
		</p>

		<%-- Botón Modificar --%>
		<input type="submit" name="accion" value="Modificar">
	</form>

	<script type="text/javascript">
    function confirmarModificacion() {
        var departamentoSelect = document.getElementsByName('idDepartamento')[0];
        var localidadesSelect = document.getElementById('localidades');

        // Verificar si el departamento ha sido cambiado y la localidad no está seleccionada
        if (departamentoSelect.value && localidadesSelect.value === '') {
            alert('Por favor, seleccione una localidad antes de guardar los cambios.');
            return false;
        }

        // Confirmación estándar para proceder con la modificación
        if (confirm('¿Modificar datos?')) {
            return true;
        } else {
            return false;
        }
    }
</script>


	<form action="SvListadoDeUsuario" method="get">
		<input type="submit" value="Volver">
	</form>
</body>
</html>
