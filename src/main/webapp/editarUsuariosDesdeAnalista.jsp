<%@page import="com.entidades.Usuario"%>
<%@page import="com.entidades.Estudiante, com.entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Editar Datos Personales</title>
    <link rel="stylesheet" href="formularios.css">
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
					<form action="LoginServlet" method="get">
						<input type="submit" class="button" value="Datos Personales">
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
	
	<form >
		<p><strong>*Nombre de Usuario:</strong></p><input type="text" name="nomUsuario" value="<%= usuarioAEditar.getNombreUsuario() %>" disabled="disabled" style="background-color: #e9e9e9;color: #a1a1a11;">
		<p><strong>*Contraseña:</strong> </p><input type="password" name="passUsuario" value="<%= usuarioAEditar.getHashContraseña() %>" disabled="disabled" style="background-color: #e9e9e9;color: #a1a1a11;">
	</form>
	
    <form action="datosPersonalesUsuario" method="post" onsubmit="return confirmarModificacion();">
        <input type="hidden" name="userId" value="<%= usuarioAEditar.getIdUsuario() %>">
    	
        <p><strong>*Documento: </strong> </p><input type="text" name="documento" value="<%= usuarioAEditar.getDocumento() %>" required>
        <p><strong>*Nombre: </strong></p> <input type="text" name="nombre" value="<%= usuarioAEditar.getNombres() %>" required>
        <p><strong>*Apellido:  </strong></p> <input type="text" name="apellido" value="<%= usuarioAEditar.getApellidos() %>" required>
        <p><strong>*Mail Institucional: </strong></p><input type="text" name="mailInst" value="<%= request.getAttribute("mailInstitucional") %>" required>
        <p><strong>*Mail: </strong></p> <input type="text" name="mail" value="<%= usuarioAEditar.getMail() %>" required>
        <p><strong>*Telefono: </strong> </p><input type="text" name="telefono" value="<%= usuarioAEditar.getTelefono() %>" required>
        
        <p>
			<label><strong>*Departamento:</strong></label>
	 	</p>
	 	<select name="idDepartamento">
    		<c:forEach var="departamento" items="${departamentos}">
        		<option value="${departamento.idDepartamento}"
            		${departamento.idDepartamento == idDepartamentoUsuario ? 'selected' : ''}>
            		${departamento.nombre}
        		</option>
    		</c:forEach>
		</select>
		
		<p>
			<label><strong>*Localidad:</strong></label>
		</p> 
		<select name="idLocalidad">
			<c:forEach var="localidad" items="${localidades}">
				<option value="${localidad.idLocalidad}"
					${localidad.idLocalidad  == idLocalidadUsuario ? 'selected' : ''}>
					${localidad.nombre}
				</option>
			</c:forEach>
		</select>
		
		
		<p>
    		<label><strong>*Género:</strong></label> 
    	</p>
    	<select name="genero">
        	<option value="M" <%= "M".equals(request.getAttribute("generoActual")) ? "selected" : "" %>>Masculino</option>
        	<option value="F" <%= "F".equals(request.getAttribute("generoActual")) ? "selected" : "" %>>Femenino</option>
        	<option value="O" <%= "O".equals(request.getAttribute("generoActual")) ? "selected" : "" %>>Otros</option>
    	</select>
		
		<p>
			<label><strong>*ITR:</strong></label>
		</p>
		<select name="idItr">
			<c:forEach var="itr" items="${itrs}">
				<option value="${itr.idItr}"
					${itr.idItr == idItrUsuario ? 'selected' : '' }>
					${itr.nombre}
				</option>
			</c:forEach>
		</select>
		
		<p>
			<label><strong>*Fecha de Nacimiento:</strong></label>
		</p> 
		<input type="date" name="fechaNacimiento" value="<%= request.getAttribute("fechaNacimientoStr") %>" required>
		
		<%-- Mostrar campos específicos de estudiantes si el usuario es un estudiante --%>
		<c:if test="${isStudent}">
		    <p><label><strong>*Semestre:</strong></label></p>
		    <select name="semestre">
		        <c:forEach begin="1" end="8" var="num">
		            <option value="${num}" ${num == semestreActual ? 'selected' : ''}>${num}</option>
		        </c:forEach>
		    </select>
		    
		    <p><label><strong>*Generación:</strong></label></p>
		    <select name="generacion">
		        <c:forEach items="${generaciones}" var="generacion">
		            <option value="${generacion.idGeneracion}" ${generacion.idGeneracion == generacionActual ? 'selected' : ''}>
		                ${generacion.nombre}
		            </option>
		        </c:forEach>
		    </select>
		</c:if>
		
		<%-- Mostrar campos específicos de tutor si el usuario es un tutor --%>
		<c:if test="${isTutor}">
    		<p><label><strong>*Rol:</strong></label></p>
    		<select name="rol">
        		<c:forEach items="${roles}" var="rol">
            		<option value="${rol.idRol}" ${rol.idRol == rolActual ? 'selected' : ''}>${rol.nombre}</option>
        		</c:forEach>
    		</select>

    		<p><label><strong>*Área:</strong></label></p>
    		<select name="area">
        		<c:forEach items="${areas}" var="area">
            		<option value="${area.idArea}" ${area.idArea == areaActual ? 'selected' : ''}>${area.nombre}</option>
       			</c:forEach>
    		</select>
		</c:if>
		
		<%-- Validación del usuario --%>
       	 <p>
            <label for="estadoUsuario"><strong>*Validación: </strong></label>
       	</p>
    	
		<select name="estadoUsuario" id="estadoUsuario">
		    <option value="1" <%= usuarioAEditar.getValidacionUsuario().getIdValidacion() == 1 ? "selected" : "" %>>VALIDADO</option>
		    <option value="2" <%= usuarioAEditar.getValidacionUsuario().getIdValidacion() == 2 ? "selected" : "" %>>SIN VALIDAR</option>
		</select>
    
    	<%-- Estado del usuario --%>
    	<p>
        	<label for="usuarioEstado"><strong>*Estado:</strong></label>
    	</p>
    	<select name="estadoUsuarioId" id="estadoUsuario">
    		<c:forEach items="${estados}" var="estado">
        		<option value="${estado.idEstado}" ${usuarioAEditar.getEstado().getIdEstado() == estado.idEstado ? "selected" : ""}>
            		${estado.nombre}
        		</option>
    		</c:forEach>
		</select>
    		
	         
      	 <p style="text-align: center; margin-top: 20px; margin-bottom: 20px; display: flex; align-items: center; justify-content: center;">
			<span style="background-color: white; padding: 0 10px;">*Campos obligatorios</span>
		</p> 
       
       <%-- Botón Modificar --%>
        <input type="submit" name="accion" value="Modificar">
    </form>
    
    <script type="text/javascript">
    	function confirmarModificacion() {
        	return confirm('¿Deseas modificar los datos?');
    	}
	</script>
    
    <form action="SvListadoDeUsuario" method="get" > 
        <input type="submit" value="Cancelar">
    </form>
</body>
</html>
