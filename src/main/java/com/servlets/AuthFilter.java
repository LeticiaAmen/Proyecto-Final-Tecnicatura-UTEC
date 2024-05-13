package com.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.entidades.Usuario;
import com.servicios.UsuarioService;

@WebFilter(urlPatterns = {"/menuAnalista.jsp", "/menuEstudiante.jsp", "/menuTutor.jsp", "/SvListadoDeUsuario", "/ListadoItr", "/registroItr.jsp"})
public class AuthFilter implements Filter {

	@EJB
	private UsuarioService usuarioService; 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;

	    String token = extractToken(req);
	    if (token != null) {
	        try {
	            Algorithm algorithm = Algorithm.HMAC256("tuClaveSecreta");
	            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
	            DecodedJWT jwt = verifier.verify(token);
	            String userId = jwt.getClaim("usuarioId").asString();

	            // Reconstruir el usuario a partir del ID obtenido del token
	            if (userId != null) {
	                Usuario usuario = usuarioService.obtenerUsuario(Long.parseLong(userId));
	                if (usuario != null) {
	                    HttpSession session = req.getSession(true);
	                    session.setAttribute("usuario", usuario); // Establecer usuario en la sesión

	                    String role = jwt.getClaim("rol").asString();
	                    if (!isRoleAllowed(role, req.getRequestURI())) {
	                        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
	                        return;
	                    }
	                    chain.doFilter(request, response);
	                } else {
	                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
	                }
	            } else {
	                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
	            }
	        } catch (JWTVerificationException exception) {
	            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token...Por favor, vuelve a iniciar sesión.");
	        }
	    } else {
	        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Token Required");
	    }
	}


	private String extractToken(HttpServletRequest request) {
	    // Primero intentamos obtener el token del header
	    String token = request.getHeader("Authorization");
	    if (token != null && token.startsWith("Bearer ")) {
	        return token.substring(7);
	    }
	    // Si no está en el header, intentamos obtenerlo de la cookie
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("Authorization".equals(cookie.getName())) {
	                String value = cookie.getValue();
	                if (value.startsWith("Bearer ")) {
	                    return value.substring(7);
	                }
	                return value;
	            }
	        }
	    }
	    return null;
	}



    private boolean isRoleAllowed(String role, String path) {
        if (role != null) {
            switch (role.toUpperCase()) {
                case "ANALISTA":
                    return path.contains("/menuAnalista.jsp") || path.contains("/ListadoItr") || path.contains("/registroItr.jsp") || path.contains("/SvListadoDeUsuario");
                case "ESTUDIANTE":
                    return path.contains("/menuEstudiante.jsp");
                case "TUTOR":
                    return path.contains("/menuTutor.jsp");
            }
        }
        return false;  //Si las condiciones no se cumplen, denegamos el acceso
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
       
    }
}
