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
import java.time.Instant;
import java.util.Optional;

/**
 * Servlet qui gère la réinitialisation du mot de passe à partir d'un token.
 * Endpoint: POST /reset
 * Paramètres attendus: token, password
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/reset"})
public class ResetPasswordServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String newPassword = req.getParameter("password");

        if (token == null || token.isBlank() || newPassword == null || newPassword.length() < 6) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid token or password (min 6 chars)");
            return;
        }

        Optional<User> maybe = userDAO.findByResetToken(token);
        if (maybe.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid or expired token");
            return;
        }

        User user = maybe.get();
        Instant expires = user.getResetExpires();
        if (expires == null || Instant.now().isAfter(expires)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid or expired token");
            return;
        }

        // Hacher le nouveau mot de passe et mettre à jour
        String hash = PasswordUtil.hashPassword(newPassword);
        boolean ok = userDAO.updatePassword(user.getId(), hash);
        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Unable to reset password");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Password has been reset");
    }
}
