package com.example.minifb.util;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static Properties props = new Properties();
    static {
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    public static String get(String key) { return props.getProperty(key); }
}
