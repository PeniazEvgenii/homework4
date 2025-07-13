package ru.aston.hometask.dao.api;

import ru.aston.hometask.dao.entity.EStatus;
import ru.aston.hometask.dao.entity.FileEntity;

import java.util.Optional;
import java.util.UUID;

public interface IFileDao {
    FileEntity save(FileEntity fileEntity);
    Optional<FileEntity> findById(UUID id);
    void updateStatus(FileEntity fileEntity, EStatus status);
}
