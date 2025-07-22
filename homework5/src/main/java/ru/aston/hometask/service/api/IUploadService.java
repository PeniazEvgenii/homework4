package ru.aston.hometask.service.api;

import ru.aston.hometask.adapter.IFileResource;
import ru.aston.hometask.dao.entity.FileEntity;
import ru.aston.hometask.exception.FileLoadException;
import ru.aston.hometask.service.dto.EFileType;
import ru.aston.hometask.dao.entity.EStatus;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface IUploadService {
    UUID upload(IFileResource fileResource, EFileType type) throws FileLoadException;
    Optional<EStatus> findStatusById(UUID id);
    FileEntity save(FileEntity entity);
}
