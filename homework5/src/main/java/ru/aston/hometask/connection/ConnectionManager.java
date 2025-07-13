package ru.aston.hometask.connection;

import ru.aston.hometask.connection.configuration.ConnectionProperty;
import ru.aston.hometask.connection.api.IConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements IConnectionManager {
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
            throw new RuntimeException("Ошибка при получении соединения", e);
        }
    }

    private void loadDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Ошибка при загрузке драйвера бд", e);
        }
    }
}
