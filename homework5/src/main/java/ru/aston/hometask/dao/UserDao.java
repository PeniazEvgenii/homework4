package ru.aston.hometask.dao;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.connection.api.IConnectionManager;
import ru.aston.hometask.dao.api.IUserDao;
import ru.aston.hometask.dao.entity.UserEntity;
import ru.aston.hometask.service.dto.EGender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserDao implements IUserDao {
    private static final String INSERT_SQL = """
            INSERT INTO app.users
            (id, email, firstname, lastname, birth_date, gender, password, discount, dt_create, dt_update)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String FIND_ALL_SQL = """
            SELECT id, email, firstname, lastname, birth_date, gender, password, discount, dt_create, dt_update
            FROM app.users""";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id = ?";
    private static final String FIND_BY_EMAIL_SQL = FIND_ALL_SQL + " WHERE email = ?";
    private static final String DELETE_SQL = """
            DELETE FROM app.users WHERE id = ?""";

    private final IConnectionManager connectionManager;

    @Override
    public UserEntity create(UserEntity user) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
        ) {
            preparedStatement.setObject(1, user.getId());
            preparedStatement.setObject(2, user.getEmail());
            preparedStatement.setObject(3, user.getFirstname());
            preparedStatement.setObject(4, user.getLastname());
            preparedStatement.setObject(5, user.getBirthDate());
            preparedStatement.setObject(6, user.getGender().name());
            preparedStatement.setObject(7, user.getPassword());
            preparedStatement.setObject(8, user.getDiscount());
            preparedStatement.setObject(9, user.getDtCreate());
            preparedStatement.setObject(10, user.getDtUpdate());

            preparedStatement.executeUpdate();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);             // поменять исключение throw new DaoException("Ошибка вставки пользователя " + user.getId(), e);
        }
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
        ) {
            connection.setReadOnly(true);
            preparedStatement.setObject(1, id);

            UserEntity user = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
        ) {
            connection.setReadOnly(true);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserEntity> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(buildUserEntity(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_SQL);
        ) {
            connection.setReadOnly(true);
            preparedStatement.setObject(1, email);

            UserEntity user = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UserEntity user) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
        ) {
            preparedStatement.setObject(1, user.getId());

            int countDelete = preparedStatement.executeUpdate();
            return countDelete > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserEntity buildUserEntity(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .setId(resultSet.getObject("id", UUID.class))
                .setEmail(resultSet.getObject("email", String.class))
                .setFirstname(resultSet.getObject("firstname", String.class))
                .setLastname(resultSet.getObject("lastname", String.class))
                .setBirthDate(resultSet.getObject("birth_date", LocalDate.class))
                .setGender(EGender.valueOf(resultSet.getObject("gender", String.class)))
                .setPassword(resultSet.getObject("password", String.class))
                .setDiscount(resultSet.getObject("discount", Double.class))
                .setDtCreate(resultSet.getObject("dt_create",OffsetDateTime.class))
                .setDtUpdate(resultSet.getObject("dt_update",OffsetDateTime.class))
                .build();
    }
}
