package ru.aston.hometask.util;

import lombok.experimental.UtilityClass;
import ru.aston.hometask.adapter.IFileResource;
import ru.aston.hometask.exception.FileLoadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class TempFileUtil {
    private static final String FILE_PREFIX = "upload_prod_";
    private static final String FILE_SUFFIX = ".tmp";
    private static final String ERROR_MESSAGE = "не удалось загрузить файл";

    /**
     * Создаёт временный файл, копирует в него данные из ресурса и возвращает путь.
     * Файл будет удалён автоматически при завершении JVM либо его нужно удалять вручную после использования.
     *
     * @param fileResource источник данных
     * @return путь к временному файлу
     * @throws FileLoadException если не удалось создать или записать файл
     */
    public static Path saveToTempFile(IFileResource fileResource) throws FileLoadException {
        try {
            Path tmpFile = Files.createTempFile(FILE_PREFIX + fileResource.getName() + "-", FILE_SUFFIX);
            tmpFile.toFile().deleteOnExit();
            try (InputStream inputStream = fileResource.getInputStream();
                 OutputStream os = Files.newOutputStream(tmpFile)) {
                inputStream.transferTo(os);
            }
            return tmpFile;
        } catch (IOException e) {
            throw new FileLoadException(ERROR_MESSAGE, e);
        }
    }
}
