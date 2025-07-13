package ru.aston.hometask.service.parser;

import org.junit.jupiter.api.Test;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.exception.FileParseException;
import ru.aston.hometask.service.parser.api.IProductParser;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExcelProductParserTest {
    private final IProductParser parser = new ExcelProductParser();

    @Test
    void when_parseValidXlsx_then_returnAllProducts() throws IOException, FileParseException {
        ProductDto product1 = new ProductDto("Product1", BigDecimal.valueOf(100.12), 10);
        ProductDto product5 = new ProductDto("Product5", BigDecimal.valueOf(500.55), 50);

        try (InputStream inputStream = ExcelProductParserTest.class.getClassLoader().getResourceAsStream("products.xlsx")) {
            assertThat(inputStream).isNotNull();
            List<ProductDto> products = parser.parse(inputStream);

            assertThat(products).hasSize(5);

            assertThat(products.get(0)).isEqualTo(product1);
            assertThat(products.get(4)).isEqualTo(product5);
        }
    }

    @Test
    void when_parseEmptyXlsx_then_throwFileParseException() throws IOException {
        try (InputStream inputStream = JsonProductParserTest.class.getClassLoader().getResourceAsStream("productsEmpty.xlsx")) {
            assertThat(inputStream).isNotNull();

            assertThatThrownBy(() -> parser.parse(inputStream))
                    .isExactlyInstanceOf(FileParseException.class)
                    .hasMessageContaining("XLSX файл пустой");
        }
    }


    @Test
    void when_parseOtherFormat_then_throwFileParseException() throws IOException {
        try (InputStream inputStream = JsonProductParserTest.class.getClassLoader().getResourceAsStream("products.csv")) {
            assertThat(inputStream).isNotNull();

            assertThatThrownBy(() -> parser.parse(inputStream))
                    .isExactlyInstanceOf(FileParseException.class)
                    .hasMessageContaining("Ошибка при чтении Excel файла");
        }
    }
}