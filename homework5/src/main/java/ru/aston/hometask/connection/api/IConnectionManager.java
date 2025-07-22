package ru.aston.hometask.connection.api;

import java.sql.Connection;

public interface IConnectionManager {
    Connection open();
}
