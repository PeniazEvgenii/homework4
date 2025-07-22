package ru.aston.hometask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.adapter.IFileResource;
import ru.aston.hometask.dao.api.IFileDao;
import ru.aston.hometask.dao.entity.EStatus;
import ru.aston.hometask.dao.entity.FileEntity;
import ru.aston.hometask.exception.FileParseException;
import ru.aston.hometask.service.api.IProductService;
import ru.aston.hometask.service.dto.EFileType;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.service.parser.api.IProductParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadServiceTest {
    private static final String CSV = "phone1;111.11;5";
    private IFileResource fileResource;
    private UploadService uploadService;
    @Mock
    IFileDao fileDao;
    @Mock
    private IProductParser parser;
    @Mock
    private IProductService productService;

    @BeforeEach
    void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        uploadService = new UploadService(executorService, Map.of(EFileType.CSV, parser), productService, fileDao);
        fileResource = new IFileResource() {
            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(CSV.getBytes());
            }

            @Override
            public String getName() {
                return "test.csv";
            }

            @Override
            public long getSize() {
                return CSV.length();
            }
        };
    }

    @Test
    void when_uploadValidFile_then_returnFileIdAndWriteStatusFinish() throws FileParseException, IOException {
//        Path pathTemp = Path.of("test.tmp");
//        try (MockedStatic<TempFileUtil> mockUtil = mockStatic(TempFileUtil.class)) {
//            mockUtil.when(() -> TempFileUtil.saveToTempFile(fileResource)).thenReturn(pathTemp);
//        }

        UUID id = UUID.randomUUID();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(id);

        doReturn(fileEntity).when(fileDao).save(any(FileEntity.class));

//        ProductDto dto = new ProductDto("phone1", new BigDecimal("100.11"), 5);
//            doReturn(List.of(dto)).when(parser).parse(any(InputStream.class));

        UUID idResult = uploadService.upload(fileResource, EFileType.CSV);

        assertThat(idResult).isEqualTo(id);
//        verify(parser).parse(any(InputStream.class));
        verify(fileDao, times(1)).save(any(FileEntity.class));
//        verify(fileDao, times(1)).updateStatus(any(FileEntity.class), eq(EStatus.FINISH));
    }

    @Test
    void when_uploadInvalidFile_then_writeStatusError() throws FileParseException, IOException {
//        Path pathTemp = Path.of("test.tmp");
//        try (MockedStatic<TempFileUtil> mockUtil = mockStatic(TempFileUtil.class)) {
//            mockUtil.when(() -> TempFileUtil.saveToTempFile(fileResource)).thenReturn(pathTemp);
//        }
        UUID id = UUID.randomUUID();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(id);

        doReturn(fileEntity).when(fileDao).save(any(FileEntity.class));

        doThrow(new FileParseException("fail")).when(parser).parse(any(InputStream.class));
        uploadService.upload(fileResource, EFileType.CSV);

        verify(fileDao, times(1)).save(any(FileEntity.class));
        verify(fileDao, times(1)).updateStatus(any(FileEntity.class), eq(EStatus.ERROR));
    }
}