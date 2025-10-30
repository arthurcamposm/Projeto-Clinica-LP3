package org.example.clinicafx.dao;

import org.example.clinicafx.model.Paciente;
import org.example.clinicafx.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PacienteDAO {

    /**
     * Salva um novo paciente no banco de dados.
     * Lida com a herança inserindo em Usuario e depois em Paciente.
     * USA TRANSAÇÃO MANUAL.
     */
    public void salvar(Paciente paciente) {
        Connection conexao = ConexaoMySQL.getConexao();
        PreparedStatement psUsuario = null;
        PreparedStatement psPaciente = null;
        ResultSet generatedKeys = null;

        String sqlUsuario = "INSERT INTO Usuario (nomeCompleto, dataNascimento, cpf, cep, senha) VALUES (?, ?, ?, ?, ?)";
        String sqlPaciente = "INSERT INTO Paciente (usuario_id) VALUES (?)";

        try {
            // Desabilita o auto-commit para tratar como transação
            conexao.setAutoCommit(false);

            // 1. Insere na tabela Usuario
            psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, paciente.getNomeCompleto());

            if (paciente.getDataNascimento() != null) {
                psUsuario.setDate(2, java.sql.Date.valueOf(paciente.getDataNascimento()));
            } else {
                psUsuario.setNull(2, Types.DATE);
            }

            psUsuario.setString(3, paciente.getCpf());
            psUsuario.setString(4, paciente.getCep());
            psUsuario.setString(5, paciente.getSenha()); // Lembre-se de usar HASH em um projeto real

            psUsuario.executeUpdate();

            // 2. Recupera o ID gerado
            generatedKeys = psUsuario.getGeneratedKeys();
            if (generatedKeys.next()) {
                int usuarioId = generatedKeys.getInt(1);
                paciente.setId(usuarioId); // Atualiza o objeto

                // 3. Insere na tabela Paciente
                psPaciente = conexao.prepareStatement(sqlPaciente);
                psPaciente.setInt(1, usuarioId);
                psPaciente.executeUpdate();

                // 4. Commita a transação
                conexao.commit();
                System.out.println("Paciente salvo com sucesso! ID: " + usuarioId);

            } else {
                throw new SQLException("Falha ao obter o ID do usuário, rollback realizado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar paciente: " + e.getMessage());
            try {
                if (conexao != null) {
                    conexao.rollback();
                    System.err.println("Rollback da transação realizado.");
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao tentar fazer rollback: " + ex.getMessage());
            }
        } finally {
            // Habilita auto-commit e fecha recursos
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psUsuario != null) psUsuario.close();
                if (psPaciente != null) psPaciente.close();
                if (conexao != null) {
                    conexao.setAutoCommit(true);
                    // Não fechamos a conexão principal, apenas os statements
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Atualiza os dados de um paciente existente no banco.
     * Atualiza a tabela Usuario.
     * USA TRANSAÇÃO MANUAL.
     */
    public void atualizar(Paciente paciente) {
        Connection conexao = ConexaoMySQL.getConexao();
        PreparedStatement psUsuario = null;

        String sqlUsuario = "UPDATE Usuario SET nomeCompleto = ?, dataNascimento = ?, cpf = ?, cep = ?, senha = ? WHERE id = ?";

        try {
            conexao.setAutoCommit(false);

            // 1. Atualiza a tabela Usuario
            psUsuario = conexao.prepareStatement(sqlUsuario);
            psUsuario.setString(1, paciente.getNomeCompleto());

            if (paciente.getDataNascimento() != null) {
                psUsuario.setDate(2, java.sql.Date.valueOf(paciente.getDataNascimento()));
            } else {
                psUsuario.setNull(2, Types.DATE);
            }

            psUsuario.setString(3, paciente.getCpf());
            psUsuario.setString(4, paciente.getCep());
            psUsuario.setString(5, paciente.getSenha());
            psUsuario.setInt(6, paciente.getId());

            psUsuario.executeUpdate();

            // 2. Commita a transação
            conexao.commit();
            System.out.println("Paciente atualizado com sucesso! ID: " + paciente.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar paciente: " + e.getMessage());
            try {
                if (conexao != null) {
                    conexao.rollback();
                    System.err.println("Rollback da transação realizado.");
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao tentar fazer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (psUsuario != null) psUsuario.close();
                if (conexao != null) conexao.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Exclui um paciente do banco de dados.
     * Graças ao "ON DELETE CASCADE" no banco, ao deletar o Usuario,
     * o Paciente correspondente será deletado automaticamente.
     * USA TRY-WITH-RESOURCES.
     */
    public void excluir(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";

        // try-with-resources fecha a conexão e o statement automaticamente
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Paciente excluído com sucesso! ID: " + id);

        } catch (SQLException e) {
            System.err.println("Erro ao excluir paciente: " + e.getMessage());
        }
    }

    /**
     * Busca um paciente pelo seu ID.
     * USA TRY-WITH-RESOURCES.
     */
    public Paciente buscarPorId(int id) {
        String sql = "SELECT * FROM Usuario u JOIN Paciente p ON u.id = p.usuario_id WHERE u.id = ?";
        Paciente paciente = null;

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = instanciarPaciente(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar paciente por ID: " + e.getMessage());
        }
        return paciente;
    }

    /**
     * Busca um paciente pelo seu CPF.
     * USA TRY-WITH-RESOURCES.
     */
    public Paciente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Usuario u JOIN Paciente p ON u.id = p.usuario_id WHERE u.cpf = ?";
        Paciente paciente = null;

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = instanciarPaciente(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar paciente por CPF: " + e.getMessage());
        }
        return paciente;
    }

    /**
     * Lista todos os pacientes cadastrados.
     * USA TRY-WITH-RESOURCES.
     */
    public List<Paciente> listarTodos() {
        String sql = "SELECT * FROM Usuario u JOIN Paciente p ON u.id = p.usuario_id";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pacientes.add(instanciarPaciente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar pacientes: " + e.getMessage());
        }
        return pacientes;
    }

    /**
     * Busca pacientes por parte do nome.
     * USA TRY-WITH-RESOURCES.
     */
    public List<Paciente> buscarPorNome(String nome) {
        String sql = "SELECT * FROM Usuario u JOIN Paciente p ON u.id = p.usuario_id WHERE u.nomeCompleto LIKE ?";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%"); // O '%' é o curinga do SQL

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pacientes.add(instanciarPaciente(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacientes por nome: " + e.getMessage());
        }
        return pacientes;
    }


    /**
     * Método helper privado para criar um objeto Paciente a partir de um ResultSet.
     * Evita repetição de código nos métodos de busca.
     */
    private Paciente instanciarPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setId(rs.getInt("id")); // 'id' vem da tabela Usuario
        paciente.setNomeCompleto(rs.getString("nomeCompleto"));
        paciente.setCpf(rs.getString("cpf"));
        paciente.setCep(rs.getString("cep"));
        paciente.setSenha(rs.getString("senha")); // Cuidado: não é boa prática trafegar senhas

        Date dataNasc = rs.getDate("dataNascimento");
        if (dataNasc != null) {
            paciente.setDataNascimento(((java.sql.Date) dataNasc).toLocalDate());
        }

        // Aqui você NÃO busca as listas (Cobertura, Consulta, etc.)
        // Isso será feito por outros DAOs (CoberturaDAO, ConsultaDAO)
        // para manter a classe PacienteDAO simples.

        return paciente;
    }
}