package ru.aston.hometask.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aston.hometask.service.dto.EFileType;
import ru.aston.hometask.dao.factory.DaoFactory;
import ru.aston.hometask.service.api.IProductService;
import ru.aston.hometask.service.api.IUploadService;
import ru.aston.hometask.service.api.IUserService;
import ru.aston.hometask.discount.DiscountDecoratorFactory;
import ru.aston.hometask.service.mapper.MapperFactory;
import ru.aston.hometask.service.parser.CsvProductParser;
import ru.aston.hometask.service.parser.ExcelProductParser;
import ru.aston.hometask.service.parser.JsonProductParser;
import ru.aston.hometask.service.parser.api.IProductParser;
import ru.aston.hometask.validator.ValidatorChain;
import ru.aston.hometask.validator.api.IUserValidator;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceFactory {
    private static final IProductService PRODUCT_SERVICE = new ProductService(DaoFactory.getProductDao());
    private static final IUserValidator USER_VALIDATOR = ValidatorChain.build(DaoFactory.getUserDao());
    private static final IUploadService UPLOAD_SERVICE = new UploadService(
            ExecutorProvider.getExecutorService(),
            getMapOfParserStrategy(),
            PRODUCT_SERVICE,
            DaoFactory.getFileDao());
    private static final IUserService USER_SERVICE = new UserService(
            USER_VALIDATOR,
            DiscountDecoratorFactory.buildDecoratorChain(),
            MapperFactory.getUserMapper(),
            DaoFactory.getUserDao());

    public static IProductService getProductService() {
        return PRODUCT_SERVICE;
    }

    public static IUploadService getUploadService() {
        return UPLOAD_SERVICE;
    }

    public static IUserService getUserService() {
        return USER_SERVICE;
    }

    private static Map<EFileType, IProductParser> getMapOfParserStrategy() {
        return Map.of(
                EFileType.JSON, new JsonProductParser(),
                EFileType.CSV, new CsvProductParser(),
                EFileType.XLSX, new ExcelProductParser());
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ExecutorProvider {
        private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

        public static ExecutorService getExecutorService() {
            return EXECUTOR_SERVICE;
        }

        public static void closeExecutorService() {
            EXECUTOR_SERVICE.shutdown();
            try {
                EXECUTOR_SERVICE.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                EXECUTOR_SERVICE.shutdownNow();
            }
        }
    }
}
