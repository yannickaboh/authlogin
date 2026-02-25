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
        
        // Configurer le serveur SMTP Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");  // Serveur SMTP de Gmail
        props.put("mail.smtp.port", "587");              // Port SMTP (TLS)
        props.put("mail.smtp.auth", "true");             // Activer l'authentification
        props.put("mail.smtp.starttls.enable", "true");  // Activer le chiffrement TLS

        // Identifiants Gmail (utiliser un mot de passe d'application, pas le mot de passe principal)
        final String user = "votre-mail@gmail.com";
        final String pass = "votre-mot-de-passe-applicatif-gmail";

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
