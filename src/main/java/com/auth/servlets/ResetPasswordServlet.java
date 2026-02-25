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

        if (token == null || token.isBlank() || newPassword == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid token or password");
            return;
        }

        if (!com.auth.utils.ValidationUtil.isStrongPassword(newPassword)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Password does not meet complexity requirements (min 8 chars, include upper/lower/digit/special)");
            return;
        }

        Optional<User> maybe = userDAO.findByResetToken(token);
        if (maybe.isEmpty()) {
            req.getSession().setAttribute("flash", "Invalid or expired token");
            req.getSession().setAttribute("flashType", "Error");
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        User user = maybe.get();
        Instant expires = user.getResetExpires();
        if (expires == null || Instant.now().isAfter(expires)) {
            req.getSession().setAttribute("flash", "Invalid or expired token");
            req.getSession().setAttribute("flashType", "Error");
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        // Hacher le nouveau mot de passe et mettre à jour
        String hash = PasswordUtil.hashPassword(newPassword);
        boolean ok = userDAO.updatePassword(user.getId(), hash);
        if (!ok) {
            req.getSession().setAttribute("flash", "Unable to reset password");
            req.getSession().setAttribute("flashType", "Error");
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        req.getSession().setAttribute("flash", "Password has been reset");
        req.getSession().setAttribute("flashType", "Success");
        resp.sendRedirect(req.getContextPath() + "/success.jsp");
    }
}
