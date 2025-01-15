package config;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }
    public static String getUser() {
        return properties.getProperty("db.user");
    }
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
