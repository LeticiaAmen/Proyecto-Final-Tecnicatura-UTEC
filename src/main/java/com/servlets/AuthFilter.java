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

        String token = extractToken(req);

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

    private String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (value.startsWith("Bearer ")) {
                        return value.substring(7); 
                    }
                    return value; // Si el token no empieza con "Bearer ", retornamos el valor
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
