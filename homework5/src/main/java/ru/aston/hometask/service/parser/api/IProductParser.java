package ru.aston.hometask.service.parser.api;

import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.exception.FileParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IProductParser {
    List<ProductDto> parse(InputStream inputStream) throws IOException, FileParseException;
}
