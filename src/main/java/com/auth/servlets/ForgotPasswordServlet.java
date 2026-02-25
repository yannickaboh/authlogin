package com.auth.servlets;

import com.auth.dao.UserDAO;
import com.auth.models.User;
import com.auth.utils.EmailUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;

/**
 * Servlet qui initie le processus "mot de passe oublié".
 * Endpoint: POST /forgot
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot"})
public class ForgotPasswordServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private static final SecureRandom RNG = new SecureRandom();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (email == null || email.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing email");
            return;
        }

        Optional<User> maybe = userDAO.findByEmail(email);
        if (maybe.isEmpty()) {
            // Pour ne pas divulguer l'existence d'un compte, renvoyer OK même si l'email n'existe pas
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("If the account exists, you will receive an email with reset instructions");
            return;
        }

        User user = maybe.get();

        // Générer un token URL-safe
        byte[] b = new byte[24];
        RNG.nextBytes(b);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(b);

        Instant expires = Instant.now().plus(1, ChronoUnit.HOURS);

        boolean ok = userDAO.setResetToken(user.getId(), token, expires);
        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Unable to initiate password reset");
            return;
        }

        // Construire un lien de réinitialisation
        String base = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String resetLink = base + "/reset?token=" + token;

        // Envoyer le mail (EmailUtil doit être configuré correctement)
        String subject = "Password reset instructions";
        String body = "Click the link to reset your password: " + resetLink + "\nThis link expires in 1 hour.";
        EmailUtil.sendEmail(user.getEmail(), subject, body);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("If the account exists, you will receive an email with reset instructions");
    }
}
