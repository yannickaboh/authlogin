package com.auth.models;

import java.time.Instant;

/**
 * Modèle représentant un utilisateur dans l'application.
 * Contient les champs essentiels stockés en base de données.
 */
public class User {
    private int id;
    private String email;
    private String passwordHash; // stocke salt:hash en base64
    private Instant createdAt;
    private String resetToken;
    private Instant resetExpires;

    public User() {}

    public User(int id, String email, String passwordHash, Instant createdAt, String resetToken, Instant resetExpires) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.resetToken = resetToken;
        this.resetExpires = resetExpires;
    }

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public Instant getResetExpires() { return resetExpires; }
    public void setResetExpires(Instant resetExpires) { this.resetExpires = resetExpires; }
}
