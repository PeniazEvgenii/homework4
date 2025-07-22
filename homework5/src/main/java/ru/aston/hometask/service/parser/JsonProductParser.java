package ru.aston.hometask.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.exception.FileParseException;
import ru.aston.hometask.service.parser.api.IProductParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonProductParser implements IProductParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ProductDto> parse(InputStream inputStream) throws IOException, FileParseException {
        try {
            List<ProductDto> result = objectMapper.readValue(inputStream, new TypeReference<List<ProductDto>>() {});

            if (result.isEmpty()) {
                throw new FileParseException("JSON файл пустой");
            }

            return result;
        } catch (JsonProcessingException e) {
            throw new FileParseException("Ошибка синтаксиса JSON или неверная структура", e);
        }
    }
}
