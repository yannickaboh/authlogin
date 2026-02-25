package com.auth.dao;

import com.auth.models.User;
import com.auth.utils.DBConnection;

import java.sql.*;
import java.time.Instant;
import java.util.Optional;

/**
 * DAO (Data Access Object) pour les opérations CRUD liées aux utilisateurs.
 * Utilise des PreparedStatement et try-with-resources pour éviter les fuites.
 */
public class UserDAO {

    /**
     * Crée un nouvel utilisateur en base. Retourne true si succès.
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users(email, password, created_at) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setTimestamp(3, Timestamp.from(user.getCreatedAt()));
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            // Log erreur côté serveur (ici affichage console pour simplicité)
            System.err.println("createUser error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recherche un utilisateur par email.
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, password, created_at, reset_token, reset_expires FROM users WHERE email = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = mapRow(rs);
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("findByEmail error: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Trouve un utilisateur par token de réinitialisation.
     */
    public Optional<User> findByResetToken(String token) {
        String sql = "SELECT id, email, password, created_at, reset_token, reset_expires FROM users WHERE reset_token = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = mapRow(rs);
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("findByResetToken error: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Définit un token de réinitialisation pour un utilisateur donné.
     */
    public boolean setResetToken(int userId, String token, Instant expires) {
        String sql = "UPDATE users SET reset_token = ?, reset_expires = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setTimestamp(2, Timestamp.from(expires));
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("setResetToken error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Met à jour le mot de passe (hash) et efface les données de reset.
     */
    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET password = ?, reset_token = NULL, reset_expires = NULL WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newPasswordHash);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updatePassword error: " + e.getMessage());
            return false;
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password"));
        Timestamp t = rs.getTimestamp("created_at");
        if (t != null) u.setCreatedAt(t.toInstant());
        u.setResetToken(rs.getString("reset_token"));
        Timestamp rt = rs.getTimestamp("reset_expires");
        if (rt != null) u.setResetExpires(rt.toInstant());
        return u;
    }
}
