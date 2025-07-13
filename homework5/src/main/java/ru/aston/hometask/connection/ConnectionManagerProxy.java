package ru.aston.hometask.connection;

import ru.aston.hometask.connection.api.IConnectionManager;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManagerProxy implements IConnectionManager {
    private static final int DATABASE_POOL_SIZE = 10;

    private final IConnectionManager connectionManager;
    private final List<Connection> sourceConnection;
    private final BlockingQueue<Connection> connectionPool;

    public ConnectionManagerProxy(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        sourceConnection = new ArrayList<>(DATABASE_POOL_SIZE);
        connectionPool = new ArrayBlockingQueue<>(DATABASE_POOL_SIZE);
        initPool();
    }

    public void initPool() {
        for (int i = 0; i < DATABASE_POOL_SIZE; i++) {
            Connection connection = connectionManager.open();

            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManagerProxy.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) ->
                            method.getName().equalsIgnoreCase("close")
                                    ? connectionPool.add((Connection) proxy)
                                    : method.invoke(connection, args)
            );
            sourceConnection.add(connection);
            connectionPool.add(proxyConnection);
        }
    }

    @Override
    public Connection open() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ошибка при получении соединения", e);
        }
    }

    public void close() throws SQLException {
        for (Connection connection : sourceConnection) {
            connection.close();
        }
    }
}
