package com.fiap.api.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ConnectionMonitor {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionMonitor.class);

    @Autowired
    private DataSource dataSource;

    @Scheduled(fixedRate = 300000) // Executa a cada 5 minutos
    public void monitorConnections() {
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            HikariPoolMXBean poolMXBean = hikariDataSource.getHikariPoolMXBean();

            if (poolMXBean != null) {
                logger.info("Status do Pool de Conexões:");
                logger.info("Conexões ativas: {}", poolMXBean.getActiveConnections());
                logger.info("Conexões ociosas: {}", poolMXBean.getIdleConnections());
                logger.info("Conexões totais: {}", poolMXBean.getTotalConnections());
                logger.info("Threads aguardando conexão: {}", poolMXBean.getThreadsAwaitingConnection());
            }
        }
    }
} 