package ru.aston.hometask.exception;

import java.io.IOException;

public class FileLoadException extends IOException {
    public FileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
