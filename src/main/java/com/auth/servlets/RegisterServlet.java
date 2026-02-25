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

        // Validation côté serveur (plus stricte)
        if (!com.auth.utils.ValidationUtil.isValidEmail(email) || !com.auth.utils.ValidationUtil.isStrongPassword(password)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid email or weak password (min 8 chars, include upper/lower/digit/special)");
            return;
        }

        // Vérifier si l'utilisateur existe déjà
        if (userDAO.findByEmail(email).isPresent()) {
            // Utiliser message flash et rediriger vers la page d'inscription
            req.getSession().setAttribute("flash", "User already exists");
            req.getSession().setAttribute("flashType", "Error");
            resp.sendRedirect(req.getContextPath() + "/register.jsp");
            return;
        }

        // Hacher le mot de passe avec salt
        String hash = PasswordUtil.hashPassword(password);

        User u = new User(email, hash);
        boolean ok = userDAO.createUser(u);
        if (!ok) {
            req.getSession().setAttribute("flash", "Unable to create user");
            req.getSession().setAttribute("flashType", "Error");
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        req.getSession().setAttribute("flash", "User created successfully");
        req.getSession().setAttribute("flashType", "Success");
        resp.sendRedirect(req.getContextPath() + "/success.jsp");
    }
}
