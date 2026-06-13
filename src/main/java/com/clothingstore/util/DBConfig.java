package com.clothingstore.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class DBConfig {

    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream in = openConfigStream()) {
            if (in == null) {
                throw new IllegalStateException("Không tìm thấy file cấu hình DB db.yml");
            }

            Properties props = loadYamlLikeProperties(in);
            url = require(props, "url");
            username = require(props, "username");
            password = require(props, "password");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static InputStream openConfigStream() {
        InputStream in = DBConfig.class.getResourceAsStream("/db.yml");
        if (in != null) {
            return in;
        }
        return DBConfig.class.getResourceAsStream("/com/clothingstore/resources/db.yml");
    }

    private static Properties loadYamlLikeProperties(InputStream in) throws Exception {
        Properties props = new Properties();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.equals("db:")) {
                    continue;
                }

                int separatorIndex = trimmed.indexOf(':');
                if (separatorIndex <= 0) {
                    continue;
                }

                String key = trimmed.substring(0, separatorIndex).trim();
                String value = trimmed.substring(separatorIndex + 1).trim();
                props.setProperty(key, value);
            }
        }
        return props;
    }

    private static String require(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Thiếu cấu hình DB: " + key);
        }
        return value;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

}