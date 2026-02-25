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
    // Lire la configuration depuis les variables d'environnement si disponibles
    // Variables attendues: DB_URL, DB_USER, DB_PASS
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:postgresql://localhost:5432/auth_system";
    private static final String USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "auth_user";
    private static final String PASSWORD = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "QWzx1234@";

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

            // Afficher un message de confirmation si la connexion est réussie (sans révéler les identifiants)
            System.out.println("Connected to DB: " + URL);
        } catch (ClassNotFoundException | SQLException e) {
            // Capturer les exceptions si le driver n'est pas trouvé ou si la connexion échoue
            System.err.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
        
        // Retourner la connexion (null si une erreur est survenue)
        return connection;
    }






}