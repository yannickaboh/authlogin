package com.auth.servlets;

import com.auth.dao.UserDAO;
import com.auth.models.User;
import com.auth.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet gérant l'inscription des utilisateurs.
 * Endpoint: POST /register
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Validation basique
        if (email == null || email.isBlank() || password == null || password.length() < 6) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid email or password (min 6 chars)");
            return;
        }

        // Vérifier si l'utilisateur existe déjà
        if (userDAO.findByEmail(email).isPresent()) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write("User already exists");
            return;
        }

        // Hacher le mot de passe avec salt
        String hash = PasswordUtil.hashPassword(password);

        User u = new User(email, hash);
        boolean ok = userDAO.createUser(u);
        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Unable to create user");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("User created");
    }
}
