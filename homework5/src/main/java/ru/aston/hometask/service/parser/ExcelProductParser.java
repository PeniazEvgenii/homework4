package ru.aston.hometask.service.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.exception.FileParseException;
import ru.aston.hometask.service.parser.api.IProductParser;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExcelProductParser implements IProductParser {
    private static final int NUMBER_SHEET = 0;
    private static final int COL_NAME = 0;
    private static final int COL_PRICE = 1;
    private static final int COL_QUANTITY = 2;

    @Override
    public List<ProductDto> parse(InputStream inputStream) throws IOException, FileParseException {
        List<ProductDto> result = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(NUMBER_SHEET);

            if (sheet == null) {
                throw new FileParseException("Отсутствует лист с индексом " + NUMBER_SHEET);
            }
            boolean first = true;

            for (Row row : sheet) {
                if (first) {
                    first = false;
                    continue;
                }

                result.add(parseRow(row));
            }

            if (result.isEmpty()) {
                throw new FileParseException("XLSX файл пустой");
            }

            return result;
        } catch (FileParseException | IOException e) {
            throw e;
        } catch (Exception e) {
            throw new FileParseException("Ошибка при чтении Excel файла", e);
        }
    }

    private ProductDto parseRow(Row row) throws FileParseException {
        try {
            Cell cellName = row.getCell(COL_NAME, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cellPrice = row.getCell(COL_PRICE, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cellQuantity = row.getCell(COL_QUANTITY, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            if (cellName == null || cellPrice == null || cellQuantity == null) {
                throw new FileParseException("Строка " + row.getRowNum() + " содержит пустые ячейки");
            }

            return new ProductDto(
                    cellName.getStringCellValue().trim(),
                    new BigDecimal(cellPrice.getStringCellValue()),
                    (int) cellQuantity.getNumericCellValue());
        } catch (NumberFormatException | IllegalStateException e) {
            throw new FileParseException("Неверный формат данных в строке", e);
        }
    }
}
