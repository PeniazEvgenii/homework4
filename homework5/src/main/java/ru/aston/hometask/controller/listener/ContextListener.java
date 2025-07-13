package ru.aston.hometask.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import ru.aston.hometask.connection.ConnectionManagerProxy;
import ru.aston.hometask.connection.factory.ConnectionManagerFactory;
import ru.aston.hometask.service.ServiceFactory;

@WebListener
public class ContextListener implements ServletContextListener {

    @SneakyThrows
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionManagerProxy connectionManagerProxy = (ConnectionManagerProxy)ConnectionManagerFactory.getConnectionManagerProxy();
        connectionManagerProxy.close();
        ServiceFactory.ExecutorProvider.closeExecutorService();
    }
}
