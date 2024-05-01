package com.servlets;

import java.io.IOException;
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
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@WebFilter(urlPatterns = {"/menuAnalista.jsp", "/menuEstudiante.jsp", "/menuTutor.jsp", "/SvListadoDeUsuario", "/ListadoItr", "/registroItr.jsp"})
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;

	    String token = null;
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("Authorization".equals(cookie.getName())) {
	                token = cookie.getValue();
	                if (token.startsWith("Bearer ")) {
	                    token = token.substring(7); // Elimina "Bearer "
	                }
	                break;
	            }
	        }
	    }

	    if (token != null) {
	        try {
	            Algorithm algorithm = Algorithm.HMAC256("tuClaveSecreta");
	            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
	            DecodedJWT jwt = verifier.verify(token);

	            String role = jwt.getClaim("rol").asString();

	            if (!isRoleAllowed(role, req.getRequestURI())) {
	                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
	                return;
	            }
	            chain.doFilter(request, response);
	        } catch (JWTVerificationException exception) {
	            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
	        }
	    } else {
	        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Token Required");
	    }
	}


	private boolean isRoleAllowed(String role, String path) {
        if ((role.equalsIgnoreCase("ANALISTA") && path.contains("/menuAnalista.jsp")) ||
            (role.equalsIgnoreCase("ESTUDIANTE") && path.contains("/menuEstudiante.jsp")) ||
            (role.equalsIgnoreCase("TUTOR") && path.contains("/menuTutor.jsp")) ||
            (role.equalsIgnoreCase("ANALISTA") && path.contains("/ListadoItr")) ||
            (role.equalsIgnoreCase("ANALISTA") && path.contains("/registroItr.jsp")) ||
            (role.equalsIgnoreCase("ANALISTA") && path.contains("/SvListadoDeUsuario"))) {
            return true;
        }
        return false;  // Si no cumple ninguna de las condiciones anteriores, acceso denegado
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
