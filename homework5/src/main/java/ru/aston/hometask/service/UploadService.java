package ru.aston.hometask.service;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.adapter.IFileResource;
import ru.aston.hometask.exception.DaoException;
import ru.aston.hometask.exception.FileLoadException;
import ru.aston.hometask.service.dto.EFileType;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.dao.api.IFileDao;
import ru.aston.hometask.dao.entity.EStatus;
import ru.aston.hometask.dao.entity.FileEntity;
import ru.aston.hometask.exception.FileParseException;
import ru.aston.hometask.service.api.IProductService;
import ru.aston.hometask.service.api.IUploadService;
import ru.aston.hometask.service.parser.api.IProductParser;
import ru.aston.hometask.util.TempFileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class UploadService implements IUploadService {
    private final ExecutorService executor ;
    private final Map<EFileType, IProductParser> parsers;
    private final IProductService productService;
    private final IFileDao fileDao;

    /**
     * Принимает inputStream файла и тип файла, сохраняет его содержимое во временный файл,
     * создаёт запись в БД со статусом LOAD, далее в отдельном потоке парсит данные и сохраняет список
     * продуктов в {@link IProductService}. По завершении обработки статус в БД обновляется на FINISH,
     * при ошибках — на ERROR. Временный файл удаляется после обработки.
     *
     * @param fileResource адаптер для Part
     * @param type тип загружаемого файла (CSV, JSON, XLSX), определяет, СТРАТЕГИЮ:
     *             какой парсер {@link IProductParser} из Map<EFileType, IProductParser> использовать.
     * @return уникальный идентификатор записи в таблице файлов.
     * @throws FileLoadException если не удалось записать данные во временный файл.
     */
    @Override
    public UUID upload(IFileResource fileResource, EFileType type) throws FileLoadException {
        Path tmpFile = TempFileUtil.saveToTempFile(fileResource);
        IProductParser parser = parsers.get(type);

        FileEntity fileEntity = save(new FileEntity(
                UUID.randomUUID(), EStatus.LOAD, OffsetDateTime.now(), OffsetDateTime.now()));

        executor.submit(buildFileParseTask(tmpFile, parser, fileEntity));

        return fileEntity.getId();
    }

    @Override
    public Optional<EStatus> findStatusById(UUID id) {
        return fileDao.findById(id)
                .map(FileEntity::getStatus);
    }

    @Override
    public FileEntity save(FileEntity entity) {
        return fileDao.save(entity);
    }

    private void updateStatus(FileEntity fileEntity, EStatus status) {
        fileDao.updateStatus(fileEntity, status);
    }

    private Runnable buildFileParseTask(Path tmpFile, IProductParser parser, FileEntity fileEntity) {
        return () -> {
            try (InputStream is = Files.newInputStream(tmpFile)) {
                List<ProductDto> productList = parser.parse(is);
                productService.save(productList);

                updateStatus(fileEntity, EStatus.FINISH);
            } catch (Exception e) {
                updateStatus(fileEntity, EStatus.ERROR);
            } finally {
                tmpFile.toFile().delete();
            }
        };
    }
}
