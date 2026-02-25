package com.auth.utils;

// Imports pour la gestion des propriétés et de l'envoi d'emails
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * Classe utilitaire pour envoyer des emails via le serveur SMTP de Gmail.
 * Cette classe configure les paramètres SMTP et établit une connexion authentifiée.
 */
public class EmailUtil {
    /**
     * Envoie un email via le serveur SMTP de Gmail.
     * 
     * @param toEmail l'adresse email du destinataire
     * @param subject le sujet de l'email
     * @param body le contenu/corps de l'email
     */
    public static void sendEmail(String toEmail, String subject, String body) {
        // Créer un objet Properties pour configurer les paramètres du serveur SMTP
        Properties props = new Properties();
        
        // Charger les paramètres SMTP depuis la configuration
        props.put("mail.smtp.host", com.auth.utils.Config.get("email.host", "smtp.gmail.com"));
        props.put("mail.smtp.port", com.auth.utils.Config.get("email.port", "587"));
        props.put("mail.smtp.auth", com.auth.utils.Config.get("email.auth", "true"));
        props.put("mail.smtp.starttls.enable", com.auth.utils.Config.get("email.starttls", "true"));

        // Lire les identifiants depuis les variables d'environnement pour éviter les secrets en dur
        final String user = System.getenv("EMAIL_USER");
        final String pass = System.getenv("EMAIL_PASS");

        if (user == null || pass == null) {
            // Ne pas tenter d'envoyer d'email si les identifiants ne sont pas configurés
            System.err.println("Email credentials not set (EMAIL_USER, EMAIL_PASS)");
            return;
        }

        // Essayer d'envoyer l'email
        try {

            // Créer une session de courrier avec les paramètres configurés et l'authentification
            Session session = Session.getInstance(props, new Authenticator() {
                // Fournir les identifiants pour l'authentification SMTP
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            // Créer un nouveau message email
            Message message = new MimeMessage(session);
            
            // Définir l'expéditeur de l'email
            message.setFrom(new InternetAddress(user));
            
            // Définir le destinataire de l'email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            
            // Définir le sujet de l'email
            message.setSubject(subject);
            
            // Définir le contenu/corps de l'email
            message.setText(body);

            // Envoyer le message via le serveur SMTP
            Transport.send(message);

        } catch (Throwable e) {
            // Capturer et gérer toutes les exceptions qui pourraient survenir lors de l'envoi
            // Afficher le message d'erreur sur la sortie d'erreur standard
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            
            // Afficher la pile d'exécution complète pour le débogage
            e.printStackTrace();

        }














    }











}
