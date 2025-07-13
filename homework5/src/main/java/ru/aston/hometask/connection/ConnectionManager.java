package ru.aston.hometask.connection;

import ru.aston.hometask.connection.configuration.ConnectionProperty;
import ru.aston.hometask.connection.api.IConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements IConnectionManager {
    private static final String ERROR_CONNECTION = "Ошибка при получении соединения";
    private static final String ERROR_LOAD_DRIVER = "Ошибка при загрузке драйвера бд";
    private final ConnectionProperty connectionProperty;

    public ConnectionManager(ConnectionProperty connectionProperty) {
        loadDriver(connectionProperty.getDriver());
        this.connectionProperty = connectionProperty;
    }

    @Override
    public Connection open() {
        try {
            return DriverManager.getConnection(
                    connectionProperty.getUrl(),
                    connectionProperty.getUsername(),
                    connectionProperty.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CONNECTION, e);
        }
    }

    private void loadDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ERROR_LOAD_DRIVER, e);
        }
    }
}
