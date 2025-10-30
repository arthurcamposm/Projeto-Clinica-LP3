package org.example.clinicafx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    // --- Configure suas credenciais do MySQL aqui ---
    private static final String URL = "jdbc:mysql://localhost:3306/ClinicaFX";
    private static final String USER = "root"; // seu usuário
    private static final String PASSWORD = "Venon@stico01"; // sua senha
    // ------------------------------------------------

    private static Connection conexao = null;

    /**
     * Obtém uma conexão com o banco de dados.
     * Cria a conexão se ela ainda não existir (Singleton).
     */
    public static Connection getConexao() {
        if (conexao == null) {
            try {
                // Carrega o driver JDBC do MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Estabelece a conexão
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o MySQL estabelecida com sucesso!");

            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC do MySQL não encontrado!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados MySQL:");
                e.printStackTrace();
            }
        }
        return conexao;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("Conexão com o MySQL fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o MySQL:");
                e.printStackTrace();
            }
        }
    }
}