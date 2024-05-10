package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidar la sesión
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Eliminar la cookie de autorización
        Cookie cookie = new Cookie("Authorization", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);  // Configura la cookie para que expire inmediatamente
        response.addCookie(cookie);

        // Redireccionar a la página de inicio
        response.sendRedirect("index.jsp");
    }
}
