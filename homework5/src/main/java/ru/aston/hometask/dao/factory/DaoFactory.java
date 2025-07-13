package ru.aston.hometask.dao.factory;

import ru.aston.hometask.connection.factory.ConnectionManagerFactory;
import ru.aston.hometask.dao.FileDao;
import ru.aston.hometask.dao.ProductDao;
import ru.aston.hometask.dao.UserDao;
import ru.aston.hometask.dao.UserDaoProxy;
import ru.aston.hometask.dao.api.IFileDao;
import ru.aston.hometask.dao.api.IProductDao;
import ru.aston.hometask.dao.api.IUserDao;

public class DaoFactory {
    private static final IUserDao USER_DAO = new UserDao(ConnectionManagerFactory.getConnectionManagerProxy());
    private static final IUserDao USER_DAO_PROXY = new UserDaoProxy(USER_DAO);
    private static final IFileDao FILE_DAO = new FileDao(ConnectionManagerFactory.getConnectionManagerProxy());
    private static final IProductDao PRODUCT_DAO = new ProductDao(ConnectionManagerFactory.getConnectionManagerProxy());

    private DaoFactory(){}

    public static IProductDao getProductDao() {
        return PRODUCT_DAO;
    }

    public static IFileDao getFileDao() {
        return FILE_DAO;
    }

    public static IUserDao getUserDaoProxy() {
        return USER_DAO_PROXY;
    }

    public static IUserDao getUserDao() {
        return USER_DAO;
    }
}
