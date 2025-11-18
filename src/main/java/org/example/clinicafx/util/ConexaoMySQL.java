package org.example.clinicafx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    // --- Mantenha suas credenciais aqui ---
    private static final String URL = "jdbc:mysql://localhost:3306/ClinicaFX";
    private static final String USER = "root"; // SEU USUÁRIO
    private static final String PASSWORD = "Venon@stico01"; // SUA SENHA
    // ------------------------------------------------

    /**
     * Obtém uma NOVA conexão com o banco de dados.
     * Agora é uma "fábrica" de conexões.
     */
    public static Connection getConexao() throws SQLException {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Retorna uma NOVA conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado!");
            throw new SQLException("Driver não encontrado", e);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados MySQL:");
            throw e;
        }
    }

    // O método fecharConexao() não é mais necessário aqui,
    // pois o 'try-with-resources' fará isso por nós.
}