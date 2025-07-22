package ru.aston.hometask.service.parser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.exception.FileParseException;
import ru.aston.hometask.service.parser.api.IProductParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvProductParser implements IProductParser {
    private static final int ROW_NAME = 0;
    private static final int ROW_PRICE = 1;
    private static final int ROW_QUANTITY = 2;
    private static final char SEPARATOR = ';';
    private static final int COUNT_SKIP_RAW_HEADER = 1;

    @Override
    public List<ProductDto> parse(InputStream inputStream) throws IOException, FileParseException {
        List<ProductDto> result = new ArrayList<>();

        CSVParser csvParserBuilder = new CSVParserBuilder()
                .withSeparator(SEPARATOR)
                .build();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(csvParserBuilder)
                .withSkipLines(COUNT_SKIP_RAW_HEADER)
                .build()) {

            List<String[]> productsRow = reader.readAll();

            for (String[] productRow : productsRow) {
                ProductDto productDto = new ProductDto(
                        productRow[ROW_NAME],
                        new BigDecimal(productRow[ROW_PRICE]),
                        Integer.parseInt(productRow[ROW_QUANTITY]));
                result.add(productDto);
            }

            if (result.isEmpty()) {
                throw new FileParseException("CSV файл пустой");
            }

            return result;
        } catch (CsvException e) {
            throw new FileParseException("Ошибка при чтении данных из файла", e);
        }
    }
}
