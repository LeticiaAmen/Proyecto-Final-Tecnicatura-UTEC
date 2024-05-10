<%@page import="com.entidades.Usuario"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu de Estudiante</title>
<link rel="stylesheet" href="menuUsuario.css">
</head>
<body>
    <header>
        <div>
            <a href="#"><img alt="Logo de UTEC" src="images/utec-removebg-preview.png" /></a>
            <%
            Usuario usuarioLogeado = (Usuario) request.getSession().getAttribute("usuario");
            %>
            <div id="usuario-dropdown">
                <h1><%= usuarioLogeado.getNombres() + " " + usuarioLogeado.getApellidos() %></h1>
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
    <!-- Botón para ir al listado de reclamos -->
    <form action="SvListarReclamos" method="get">
    	<input type="submit" class="button" value="Reclamos">
    </form>
</body>
</html>
