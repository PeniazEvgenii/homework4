package ru.aston.hometask.connection.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aston.hometask.connection.configuration.ConnectionProperty;
import ru.aston.hometask.connection.ConnectionManager;
import ru.aston.hometask.connection.ConnectionManagerProxy;
import ru.aston.hometask.connection.api.IConnectionManager;
import ru.aston.hometask.util.PropertiesUtil;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionManagerFactory {
    private static final IConnectionManager CONNECTION_MANAGER = new ConnectionManager(ConnectionConfigurationProvider.getProperty());
    private static final IConnectionManager CONNECTION_MANAGER_PROXY = new ConnectionManagerProxy(CONNECTION_MANAGER);

    public static IConnectionManager getConnectionManager() {
        return CONNECTION_MANAGER;
    }

    public static IConnectionManager getConnectionManagerProxy() {
        return CONNECTION_MANAGER_PROXY;
    }


    public static class ConnectionConfigurationProvider {
        private static final String URL_KEY = "db.url";
        private static final String USERNAME_KEY = "db.username";
        private static final String PASSWORD_KEY = "db.password";
        private static final String DB_DRIVER_KEY = "db.driver";

        private static final String ENV_CONFIG_URL = System.getenv("DATABASE_URL");
        private static final String ENV_CONFIG_USERNAME = System.getenv("DATABASE_USERNAME");
        private static final String ENV_CONFIG_PASSWORD = System.getenv("DATABASE_PASSWORD");
        private static final String ENV_CONFIG_DRIVER = System.getenv("DATABASE_DRIVER");

        private static ConnectionProperty getProperty() {
            String url, username, password, driver;

            if (isValidEnvVariables()) {
                url = ENV_CONFIG_URL;
                username = ENV_CONFIG_USERNAME;
                password = ENV_CONFIG_PASSWORD;
                driver = ENV_CONFIG_DRIVER;
            } else {
                url = PropertiesUtil.get(URL_KEY);
                username = PropertiesUtil.get(USERNAME_KEY);
                password = PropertiesUtil.get(PASSWORD_KEY);
                driver = PropertiesUtil.get(DB_DRIVER_KEY);
            }
            return ConnectionProperty.builder()
                    .setUrl(url)
                    .setUsername(username)
                    .setPassword(password)
                    .setDriver(driver)
                    .build();
        }

        private static boolean isValidEnvVariables() {
            return ENV_CONFIG_URL != null && ENV_CONFIG_USERNAME != null &&
                    ENV_CONFIG_PASSWORD != null && ENV_CONFIG_DRIVER != null;
        }
    }
}
