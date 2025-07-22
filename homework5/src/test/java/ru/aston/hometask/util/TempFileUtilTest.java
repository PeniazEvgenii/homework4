package ru.aston.hometask.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.adapter.IFileResource;
import ru.aston.hometask.exception.FileLoadException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TempFileUtilTest {
    private static final String TEMP_FILE_EXTENSION = "tmp";
    private Path actualPath;
    @Mock
    private IFileResource fileResource;

    @Test
    void when_correctResource_then_returnPath() throws IOException {
        String testString = "Hello world";
        byte[] content = testString.getBytes();
        doReturn(new ByteArrayInputStream(content)).when(fileResource).getInputStream();
        doReturn("hello.json").when(fileResource).getName();

        actualPath = TempFileUtil.saveToTempFile(fileResource);

        assertThat(actualPath).exists();
        assertThat(actualPath).hasExtension(TEMP_FILE_EXTENSION);
        assertThat(actualPath).hasContent(testString);
    }

    @Test
    void when_incorrectResource_then_throwFileLoadException() throws IOException {
        doThrow(new IOException()).when(fileResource).getInputStream();
        doReturn("example.example").when(fileResource).getName();

        assertThatThrownBy(() -> TempFileUtil.saveToTempFile(fileResource))
                .isExactlyInstanceOf(FileLoadException.class)
                .hasMessage("не удалось загрузить файл");

    }

}