package com.auth.utils;

import java.util.regex.Pattern;

/**
 * Utilitaires de validation côté serveur.
 */
public class ValidationUtil {
    // Simple regex pour valider les emails (suffisant pour la plupart des cas)
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    // Politique de mot de passe: min 8, au moins 1 majuscule, 1 minuscule, 1 chiffre, 1 caractère spécial
    private static final Pattern STRONG_PWD = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isStrongPassword(String password) {
        return password != null && STRONG_PWD.matcher(password).matches();
    }
}
