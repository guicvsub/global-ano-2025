package com.fiap.api.config;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@Configuration
@Component
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private DataSource dataSource;

    @PreDestroy
    public void destroy() {
        logger.info("Iniciando o processo de limpeza de conexões de banco de dados...");

        try {
            // Tenta fechar o pool de conexões se for um pool
            if (dataSource instanceof AutoCloseable) {
                ((AutoCloseable) dataSource).close();
                logger.info("Pool de conexões fechado com sucesso");
            }
        } catch (Exception e) {
            logger.error("Erro ao fechar o pool de conexões: {}", e.getMessage());
        }

        // Desregistra todos os drivers JDBC
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                logger.info("Driver {} desregistrado com sucesso", driver.getClass().getName());
            } catch (SQLException e) {
                logger.error("Erro ao desregistrar driver: {}", e.getMessage());
            }
        }

        logger.info("Processo de limpeza de conexões concluído");
    }
} 