package integration.properties;

import integration.loggers.CustomLogger;
import properties.PropertiesResourceManager;

public class ConfigProperties {
    private ConfigProperties() {}

    private static PropertiesResourceManager props = new PropertiesResourceManager("properties/config.properties");

    public static String getHost() {
        return props.getProperty("host");
    }

    public static String getVersion() {
        return props.getProperty("version");
    }

    public static String getUsername() {
        String username = System.getenv("TEST_USERNAME");
        if (username != null && !username.isEmpty()) {
            CustomLogger.info(String.format("Using global env 'TEST_USERNAME=%s'", username));
            return username;
        }
        CustomLogger.info("No global env 'TEST_USERNAME'. Using value from config");
        return props.getProperty("username");
    }

    public static String getPassword() {
        String password = System.getenv("TEST_PASSWORD");
        if (password != null && !password.isEmpty()) {
            CustomLogger.info(String.format("Using global env 'TEST_PASSWORD=%s'", password));
            return password;
        }
        CustomLogger.info("No global env 'TEST_PASSWORD'. Using value from config");
        return props.getProperty("password");
    }

    public static boolean getLogRequest() {
        return Boolean.parseBoolean(props.getProperty("logRequest"));
    }

    public static boolean getLogResponse() {
        return Boolean.parseBoolean(props.getProperty("logResponse"));
    }
}
