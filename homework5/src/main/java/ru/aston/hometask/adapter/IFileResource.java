package ru.aston.hometask.adapter;

import java.io.IOException;
import java.io.InputStream;

public interface IFileResource {
    InputStream getInputStream() throws IOException;
    String getName();
    long getSize();
}
