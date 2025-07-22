package ru.aston.hometask.dao;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.connection.api.IConnectionManager;
import ru.aston.hometask.dao.api.IProductDao;
import ru.aston.hometask.exception.DaoException;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.service.dto.ProductReadDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductDao implements IProductDao {
    private static final int BATCH_SIZE = 50;
    private static final String INSERT_SQL = """
            INSERT INTO app.products(name, price, quantity, dt_create, dt_update)
            VALUES (?, ?, ?, ?, ?)""";
    public static final String FIND_ALL_SQL = """
            SELECT id, name, price, quantity, dt_create, dt_update
            FROM app.products""";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id = ?";

    private final IConnectionManager connectionManager;

    @Override
    public void save(List<ProductDto> products) {
        try (Connection connection = connectionManager.open()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                int generatedCount = 0;
                int size = products.size();
                for (int start = 0; start < size; start += BATCH_SIZE) {
                    int end = Math.min(start + BATCH_SIZE, size);
                    preparedStatement.clearBatch();

                    for (int i = start; i < end; i++) {
                        ProductDto product = products.get(i);
                        preparedStatement.setObject(1, product.getName());
                        preparedStatement.setObject(2, product.getPrice());
                        preparedStatement.setObject(3, product.getQuantity());
                        preparedStatement.setObject(4, OffsetDateTime.now());
                        preparedStatement.setObject(5, OffsetDateTime.now());
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();

                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    while (resultSet.next()) {
                        generatedCount++;
                    }
                }
                if (generatedCount != size) {
                    throw new IllegalStateException("Количество переданных и записанных в бд товаров не сходится");
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new DaoException("Ошибка во время сохранения продуктов", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка соединения с бд", e);
        }
    }

    @Override
    public Optional<ProductReadDto> findById(Long id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            connection.setReadOnly(true);
            preparedStatement.setObject(1, id);

            ProductReadDto product = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = buildProduct(resultSet);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException("Ошибка во время получения продукта", e);
        }
    }

    @Override
    public List<ProductReadDto> findAll() {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            connection.setReadOnly(true);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProductReadDto> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(buildProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException("Ошибка во время получения продуктов", e);
        }
    }

    private static ProductReadDto buildProduct(ResultSet resultSet) throws SQLException {
        return ProductReadDto.builder()
                .id(resultSet.getObject("id", Long.class))
                .name(resultSet.getObject("name", String.class))
                .price(resultSet.getObject("price", BigDecimal.class))
                .quantity(resultSet.getObject("quantity", Integer.class))
                .dtCreate(resultSet.getObject("dt_create", OffsetDateTime.class))
                .dtUpdate(resultSet.getObject("dt_update", OffsetDateTime.class))
                .build();
    }
}
