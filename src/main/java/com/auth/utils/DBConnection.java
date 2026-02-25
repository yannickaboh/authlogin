package com.auth.utils;

// Imports pour la gestion des connexions à la base de données
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour gérer la connexion à la base de données PostgreSQL.
 * Cette classe centralise les paramètres de connexion et fournit une méthode
 * pour obtenir une connexion à la base de données.
 */
public class DBConnection {
    // URL de la base de données: jdbc:postgresql://[hôte]:[port]/[nom_base]
    private static final String URL = "jdbc:postgresql://localhost:5432/auth_system";
    
    // Nom d'utilisateur pour l'authentification à la base de données
    private static final String USER = "auth_user";
    
    // Mot de passe pour l'authentification à la base de données
    private static final String PASSWORD = "QWzx1234@";

    /**
     * Obtient une connexion à la base de données PostgreSQL.
     * 
     * @return une connexion à la base de données, ou null si une erreur survient
     */
    public static Connection getConnection() {
        // Initialiser la variable de connexion à null
        Connection connection = null;

        try {
            // Charger le driver PostgreSQL en mémoire
            Class.forName("org.postgresql.Driver");
            
            // Établir une connexion à la base de données avec les identifiants
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Afficher un message de confirmation si la connexion est réussie
            System.out.println("Connexion a la base de donnees reussie !");
        } catch (ClassNotFoundException | SQLException e) {
            // Capturer les exceptions si le driver n'est pas trouvé ou si la connexion échoue
            // Afficher le message d'erreur sur la sortie d'erreur standard
            System.err.println("Erreur de connexion : " + e.getMessage());
            
            // Afficher la pile d'exécution complète pour le débogage
            e.printStackTrace();
        }
        
        // Retourner la connexion (null si une erreur est survenue)
        return connection;
    }






}