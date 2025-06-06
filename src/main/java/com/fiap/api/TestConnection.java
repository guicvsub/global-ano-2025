package com.fiap.api;

import com.fiap.api.config.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try {
            System.out.println("Tentando conectar ao banco de dados...");
            Connection connection = factory.getConnection();
            System.out.println("Conexão estabelecida com sucesso!");
            
            // Testa se a conexão está válida
            if (connection.isValid(5)) {
                System.out.println("A conexão está válida!");
                
                // Informações sobre o banco
                System.out.println("URL: " + connection.getMetaData().getURL());
                System.out.println("Usuário: " + connection.getMetaData().getUserName());
                System.out.println("Banco de Dados: " + connection.getMetaData().getDatabaseProductName());
                System.out.println("Versão: " + connection.getMetaData().getDatabaseProductVersion());
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Código de Erro: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            factory.closeConnection();
        }
    }
} 