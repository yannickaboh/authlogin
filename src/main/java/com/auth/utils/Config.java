package com.auth.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility to load configuration from application.properties with environment variable overrides.
 */
public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            System.err.println("Unable to load application.properties: " + e.getMessage());
        }
    }

    public static String get(String key, String defaultValue) {
        // Environment variables take precedence
        String env = System.getenv(keyToEnv(key));
        if (env != null && !env.isEmpty()) return env;
        String p = props.getProperty(key);
        return p != null ? p : defaultValue;
    }

    private static String keyToEnv(String key) {
        // Convert dotted keys to upper-case underscore env var names, e.g. db.url -> DB_URL
        return key.toUpperCase().replace('.', '_');
    }
}
