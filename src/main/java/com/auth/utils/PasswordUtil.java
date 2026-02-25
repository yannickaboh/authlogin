package com.auth.utils;

// Imports pour la manipulation de chaînes, hachage cryptographique et encodage en base64
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Classe utilitaire pour gérer le hachage et la vérification sécurisée des mots de passe.
 * Utilise l'algorithme SHA-256 avec un salt aléatoire pour renforcer la sécurité.
 */
public class PasswordUtil {

    // Générateur de nombres aléatoires sécurisés utilisé pour créer le salt
    private static final SecureRandom RNG = new SecureRandom();

    /**
     * Hache un mot de passe avec un salt aléatoire en utilisant SHA-256.
     * Le résultat retourné contient le salt et le hash séparés par ':' et encodés en base64.
     * 
     * @param password le mot de passe à hacher
     * @return une chaîne au format: salt_en_base64:hash_en_base64 ou null si le mot de passe est null
     */
    public static String hashPassword(String password) {
        // Retourner null si le mot de passe est vide (null)
        if (password == null) return null;
        
        // Créer un salt (valeur aléatoire) de 16 octets pour renforcer la sécurité
        byte[] salt = new byte[16];

        // Remplir le salt avec des valeurs aléatoires sécurisées
        RNG.nextBytes(salt);
        
        // Hacher le mot de passe avec le salt en utilisant SHA-256
        byte[] hash = sha256(salt, password);
        
        // Retourner le salt et le hash encodés en base64, séparés par ':'
        // Format: salt_base64:hash_base64
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);

    }

    /**
     * Vérifie si un mot de passe fourni correspond à un mot de passe stocké en base de données.
     * La vérification est effectuée en comparant les hashes pour éviter les attaques par timing.
     * 
     * @param password le mot de passe à vérifier
     * @param stored le mot de passe stocké au format salt_base64:hash_base64
     * @return true si les mots de passe correspondent, false sinon
     */
    public static boolean verifyPassword(String password, String stored) {
        // Vérifier que les deux paramètres ne sont pas null
        if (password == null || stored == null) return false;
        
        // Trouver la position du séparateur ':' pour extraire le salt et le hash
        int idx = stored.indexOf(':');
        
        // Si le séparateur n'est pas trouvé, comparer directement les chaînes (fallback legacy)
        if(idx < 0) {
            return stored.equals(password);
        }

        // Extraire le salt (avant le ':') et le hash (après le ':') de la chaîne stockée
        String saltB64 = stored.substring(0, idx);
        String hashB64 = stored.substring(idx + 1);
        
        byte[] salt;
        byte[] expected;
        
        try {
            // Décoder le salt et le hash depuis le format base64
            salt = Base64.getDecoder().decode(saltB64);
            expected = Base64.getDecoder().decode(hashB64);
        } catch (IllegalArgumentException e) {
            // Si le décodage échoue, le format est invalide
            return false;
        } 
        
        // Hacher le mot de passe fourni avec le salt extrait
        byte[] actual = sha256(salt, password);
        
        // Comparer les deux hashes de façon sécurisée (résistant aux attaques par timing)
        return MessageDigest.isEqual(expected, actual);
    }

    /**
     * Hache un mot de passe avec un salt en utilisant l'algorithme SHA-256.
     * 
     * @param salt la valeur aléatoire utilisée pour renforcer la sécurité
     * @param password le mot de passe à hacher
     * @return le hash SHA-256 du (salt + mot de passe)
     */
    public static byte[] sha256(byte[] salt, String password) {
        try {
            // Créer une instance du digesteur SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Ajouter le salt au digest
            md.update(salt);
            
            // Ajouter le mot de passe encodé en UTF-8 au digest
            md.update(password.getBytes(StandardCharsets.UTF_8));
            
            // Calculer et retourner le hash final
            return md.digest();
        } catch (Exception e) {
            // Si SHA-256 n'est pas disponible, lever une exception
            throw new IllegalStateException("SHA-256 est indisponible");
        }

    }





















}