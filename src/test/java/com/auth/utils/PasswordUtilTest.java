package com.auth.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    public void testHashAndVerify() {
        String password = "Str0ng!Pass";
        String stored = PasswordUtil.hashPassword(password);
        assertNotNull(stored);
        assertTrue(PasswordUtil.verifyPassword(password, stored));
        assertFalse(PasswordUtil.verifyPassword("wrongpass", stored));
    }
}
