package com.auth.dao;

import com.auth.models.User;
import com.auth.utils.DBConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test basique pour UserDAO. Ce test ne s'exécutera que si la variable d'environnement DB_URL est définie.
 */
public class UserDAOTest {

    @Test
    @EnabledIfEnvironmentVariable(named = "DB_URL", matches = ".+")
    public void integrationCreateAndDelete() throws SQLException {
        Connection c = DBConnection.getConnection();
        assertNotNull(c);
        c.close();

        // This is a lightweight check; full CRUD tests require a test DB setup and teardown.
        UserDAO dao = new UserDAO();
        User u = new User("test-user@example.com", "dummy-hash");
        boolean ok = dao.createUser(u);
        assertTrue(ok);
        assertTrue(u.getId() > 0);
        // Note: Cleanup not implemented here — prefer a dedicated test DB
    }
}
