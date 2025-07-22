package ru.aston.hometask.dao;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.connection.api.IConnectionManager;
import ru.aston.hometask.dao.api.IFileDao;
import ru.aston.hometask.dao.entity.EStatus;
import ru.aston.hometask.dao.entity.FileEntity;
import ru.aston.hometask.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FileDao implements IFileDao {
    private static final String INSERT_SQL = """
            INSERT INTO app.files (id, status, dt_create, dt_update)
            VALUES (?,?,?,?)""";
    private static final String FIND_BY_ID_SQL = """
            SELECT id, status, dt_create, dt_update
            FROM app.files WHERE id = ?""";
    private static final String UPDATE_SQL = """
            UPDATE app.files SET status = ?, dt_update = ?
            WHERE id = ?""";


    private final IConnectionManager connectionManager;

    @Override
    public FileEntity save(FileEntity fileEntity) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setObject(1, fileEntity.getId());
            preparedStatement.setObject(2, fileEntity.getStatus().name());
            preparedStatement.setObject(3, fileEntity.getDtCreate());
            preparedStatement.setObject(4, fileEntity.getDtUpdate());

            preparedStatement.executeUpdate();
            return fileEntity;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при сохранении", e);
        }
    }

    @Override
    public Optional<FileEntity> findById(UUID id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            connection.setReadOnly(true);

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            FileEntity fileEntity = null;
            if (resultSet.next()) {
                fileEntity = new FileEntity(
                        resultSet.getObject("id", UUID.class),
                        EStatus.valueOf(resultSet.getObject(2, String.class)),
                        resultSet.getObject("dt_create", OffsetDateTime.class),
                        resultSet.getObject("dt_update", OffsetDateTime.class));
            }

            return Optional.ofNullable(fileEntity);
        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении состояния загрузки", e);
        }
    }

    @Override
    public void updateStatus(FileEntity fileEntity, EStatus status) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setObject(1, status.name());
            preparedStatement.setObject(2, OffsetDateTime.now());
            preparedStatement.setObject(3, fileEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении состояния загрузки файла", e);
        }
    }
}
