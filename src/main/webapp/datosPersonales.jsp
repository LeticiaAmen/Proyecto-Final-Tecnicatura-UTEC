<%@page import="com.entidades.Usuario"%>
<%@page
	import="com.entidades.Estudiante, com.entidades.Usuario, com.entidades.Tutor, com.entidades.Analista"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Datos Personales</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
	<header>
		<div>
			<a> <img alt="Logo de UTEC"
				src="images/utec-removebg-preview.png" />
			</a>
			
			<%	Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
				String jwtToken = null;
				Cookie[] cookies = request.getCookies();
				
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if ("Authorization".equals(cookie.getName())) {
							jwtToken = cookie.getValue();
							if (jwtToken.startsWith("Bearer ")) {
								jwtToken = jwtToken.substring(7); // Elimina "Bearer"
							}
							break;
						}
					}
				} %>
			
			<div id="usuario-dropdown">
				<h1><%=usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos()%></h1>
				
				<%-- Muestra el token --%>
				<% if (jwtToken != null) { 
						System.out.println(jwtToken); 
				 	} else { 
				 		System.out.println("No hay token disponible"); 
				 	} %>
				
				<div id="dropdown-content">
					<form action="datosPersonales" method="get">
						<input type="hidden" name="id" value="<%=usuarioLogeado.getIdUsuario()%>">
						<input type="submit" class="button" value="Datos Personales">
					</form>

					<form action="LogoutServlet" method="post">
						<input type="submit" class="button" value="Cerrar Sesión">
					</form>
				</div>
			</div>
		</div>
	</header>
	<h1>Datos Personales</h1>
	
	<%	Usuario usuarioAEditar = (Usuario) request.getAttribute("usuarioAEditar");
		if (usuarioAEditar == null) {
			response.sendRedirect("errorPage.jsp"); // Redireccionar a una página de error si el usuario no existe.
			return;
		}
		
		boolean isStudent = usuarioAEditar instanceof Estudiante;
		Estudiante student = isStudent ? (Estudiante) usuarioAEditar : null; %>

	<form action="datosPersonales" method="post" onsubmit="return confirmarModificacion();">
		<input type="hidden" name="userId" value="<%=usuarioAEditar.getIdUsuario()%>">
		
		<p>
			<strong>Nombre de Usuario:</strong>
		</p>
		<input type="text" name="nomUsuario" value="<%=usuarioAEditar.getNombreUsuario()%>" required>
		
		<p>
			<strong>Contraseña (dejar en blanco si no desea cambiarla):</strong>
		</p>
		<input type="password" name="passUsuario" value="" >
		
		<p>
			<strong>Documento*: </strong>
		</p>
		<input type="text" name="documento" value="<%=usuarioAEditar.getDocumento()%>" required>
		
		<p>
			<strong>Nombre*: </strong>
		</p>
		<input type="text" name="nombre" value="<%=usuarioAEditar.getNombres()%>" required>
		
		<p>
			<strong>Apellido*: </strong>
		</p>
		<input type="text" name="apellido" value="<%=usuarioAEditar.getApellidos()%>" required minlength="3"
			oninvalid="this.setCustomValidity('Por favor, introduce un apellido entre 3 y 20 caracteres')" 
		    oninput="this.setCustomValidity('')">
		
		<p>
			<strong>Mail Institucional*: </strong>
		</p>
		<input type="text" name="mailInst" value="<%=request.getAttribute("mailInstitucional")%>" required>
		
		<p>
			<strong>Mail*: </strong>
		</p>
		<input style=" width: 100%; padding: 10px; margin: 8px 0; display: inline-block; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box;
	    	transition: border 0.3s, box-shadow 0.3s;" type="email" name="mail" value="<%=usuarioAEditar.getMail()%>" required 
	    	oninvalid="this.setCustomValidity('Por favor, introduce una dirección de correo electrónico personal válido.')" 
	       	oninput="this.setCustomValidity('')">
		
		<p>
			<strong>Telefono*: </strong>
		</p>
		<input type="text" name="telefono" value="<%=usuarioAEditar.getTelefono()%>" required>

		<p>
			<label><strong>Departamento*:</strong></label>
		</p>
		<select name="idDepartamento">
			<c:forEach var="departamento" items="${departamentos}">
				<option value="${departamento.idDepartamento}"
					${departamento.idDepartamento == idDepartamentoUsuario ? 'selected' : ''}>
					${departamento.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<label><strong>Localidad*:</strong></label>
		</p>
		<select name="idLocalidad">
			<c:forEach var="localidad" items="${localidades}">
				<option value="${localidad.idLocalidad}"
					${localidad.idLocalidad  == idLocalidadUsuario ? 'selected' : ''}>
					${localidad.nombre}</option>
			</c:forEach>
		</select>


		<p>
			<label><strong>Género*:</strong></label>
		</p>
		<select name="genero">
			<option value="M"
				<%="M".equals(request.getAttribute("generoActual")) ? "selected" : ""%>>Masculino</option>
			<option value="F"
				<%="F".equals(request.getAttribute("generoActual")) ? "selected" : ""%>>Femenino</option>
			<option value="O"
				<%="O".equals(request.getAttribute("generoActual")) ? "selected" : ""%>>Otros</option>
		</select>

		<p>
			<label><strong>ITR*:</strong></label>
		</p>
		<select name="idItr">
			<c:forEach var="itr" items="${itrs}">
				<option value="${itr.idItr}"
					${itr.idItr == idItrUsuario ? 'selected' : '' }>
					${itr.nombre}</option>
			</c:forEach>
		</select>

		<p>
			<label><strong>Fecha de Nacimiento*:</strong></label>
		</p>
		<input type="date" name="fechaNacimiento"
			value="<%=request.getAttribute("fechaNacimientoStr")%>" required>

		<%-- Mostrar campos específicos de estudiantes si el usuario es un estudiante --%>
		<c:if test="${isStudent}">
			<p>
				<label><strong>Semestre*:</strong></label>
			</p>
			<select name="semestre">
				<c:forEach begin="1" end="8" var="num">
					<option value="${num}" ${num == semestreActual ? 'selected' : ''}>${num}</option>
				</c:forEach>
			</select>

			<p>
				<label><strong>Generación*:</strong></label>
			</p>
			<select name="generacion">
				<c:forEach items="${generaciones}" var="generacion">
					<option value="${generacion.idGeneracion}"
						${generacion.idGeneracion == generacionActual ? 'selected' : ''}>
						${generacion.nombre}</option>
				</c:forEach>
			</select>
		</c:if>

		<%-- Mostrar campos específicos de estudiantes si el usuario es un tutor --%>
		<c:if test="${isTutor}">
			<p>
				<label><strong>Rol*:</strong></label>
			</p>
			<select name="rol">
				<c:forEach items="${roles}" var="rol">
					<option value="${rol.idRol}"
						${rol.idRol == rolActual ? 'selected' : ''}>${rol.nombre}</option>
				</c:forEach>
			</select>

			<p>
				<label><strong>Área*:</strong></label>
			</p>
			<select name="area">
				<c:forEach items="${areas}" var="area">
					<option value="${area.idArea}"
						${area.idArea == areaActual ? 'selected' : ''}>${area.nombre}</option>
				</c:forEach>
			</select>
		</c:if>
		<p
			style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos
				obligatorios</span>
		</p>

		<!-- Botón Modificar -->
    		<input type="submit" name="accion" value="Modificar" >   		

		<%-- Verificar si hay un mensaje de éxito y lo mostramos --%>
		<c:if test="${not empty requestScope.successMessage}">
			<div class="alert alert-success" role="alert">
				${requestScope.successMessage}</div>
		</c:if>
		
	</form>
	
	<%
    // Asignar el tipo de usuario a una variable basada en el objeto usuarioLogeado
    String tipoUsuario = "index.jsp"; // predeterminado a la página de inicio
    if (usuarioLogeado instanceof Estudiante) {
        tipoUsuario = "menuEstudiante.jsp";
    } else if (usuarioLogeado instanceof Tutor) {
        tipoUsuario = "menuTutor.jsp";
    } else if (usuarioLogeado instanceof Analista) {
        tipoUsuario = "menuAnalista.jsp";
    }
	%>
	
	<!-- Botón Cancelar -->
	<form action="<%= tipoUsuario %>" method="get">
    	<input type="submit" value="Cancelar">
	</form>

	
	<script type="text/javascript">
	    function confirmarModificacion() {
	        // Obtener el botón que se presionó
	        var botonPresionado = document.activeElement.value;
	
	        if (botonPresionado === 'Modificar') {
	            if (confirm('¿Modificar datos personales?')) {
	                alert('¡Datos personales modificados con éxito!');
	                return true; // Continuar con la acción de modificar
	            }else {
	                return false; // Cancela la acción de modificar
	            	}
	        	}
	        }
	</script>
</body>
</html>
