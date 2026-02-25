package com.auth.servlets;

import com.auth.dao.UserDAO;
import com.auth.models.User;
import com.auth.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet gérant la connexion des utilisateurs.
 * Endpoint: POST /login
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing credentials");
            return;
        }

        // Basic server-side email format validation
        if (!com.auth.utils.ValidationUtil.isValidEmail(email)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid email format");
            return;
        }

        Optional<User> maybe = userDAO.findByEmail(email);
        if (maybe.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Invalid email or password");
            return;
        }

        User user = maybe.get();

        // Vérifier le mot de passe de manière résistante aux attaques par timing
        boolean ok = PasswordUtil.verifyPassword(password, user.getPasswordHash());
        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Invalid email or password");
            return;
        }

        // Authentification réussie : créer une session
        HttpSession session = req.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Logged in");
    }
}
