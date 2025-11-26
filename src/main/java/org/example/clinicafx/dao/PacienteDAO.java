package org.example.clinicafx.dao;

import org.example.clinicafx.model.Paciente;
import org.example.clinicafx.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    /**
     * Salva um novo paciente.
     * Agora usa try-with-resources para a conexão e transação.
     */
    public void salvar(Paciente paciente) {
        String sqlUsuario = "INSERT INTO Usuario (nomeCompleto, dataNascimento, cpf, cep, senha) VALUES (?, ?, ?, ?, ?)";
        String sqlPaciente = "INSERT INTO Paciente (usuario_id) VALUES (?)";

        // try-with-resources para a Conexão
        try (Connection conexao = ConexaoMySQL.getConexao()) {

            // Inicia transação
            conexao.setAutoCommit(false);

            int usuarioId = -1;

            // try-with-resources para o PreparedStatement de Usuário
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, paciente.getNomeCompleto());
                if (paciente.getDataNascimento() != null) {
                    psUsuario.setDate(2, java.sql.Date.valueOf(paciente.getDataNascimento()));
                } else {
                    psUsuario.setNull(2, Types.DATE);
                }
                psUsuario.setString(3, paciente.getCpf());
                psUsuario.setString(4, paciente.getCep());
                psUsuario.setString(5, paciente.getSenha());
                psUsuario.executeUpdate();

                // Recupera o ID gerado
                try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuarioId = generatedKeys.getInt(1);
                        paciente.setId(usuarioId);
                    } else {
                        throw new SQLException("Falha ao obter o ID do usuário.");
                    }
                }
            }

            // try-with-resources para o PreparedStatement de Paciente
            try (PreparedStatement psPaciente = conexao.prepareStatement(sqlPaciente)) {
                psPaciente.setInt(1, usuarioId);
                psPaciente.executeUpdate();
            }

            // Se tudo deu certo, commita
            conexao.commit();
            System.out.println("Paciente salvo com sucesso! ID: " + usuarioId);

        } catch (SQLException e) {
            System.err.println("Erro ao salvar paciente (transação revertida): " + e.getMessage());
            // O try-with-resources já tentará fechar a conexão,
            // e o rollback é feito automaticamente se autoCommit(false) e o commit() não for chamado.
        }
    }

    /**
     * Atualiza os dados de um paciente (apenas na tabela Usuario).
     */
    public void atualizar(Paciente paciente) {
        String sqlUsuario = "UPDATE Usuario SET nomeCompleto = ?, dataNascimento = ?, cpf = ?, cep = ?, senha = ? WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {

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
            System.out.println("Paciente atualizado com sucesso! ID: " + paciente.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    /**
     * Exclui um paciente do banco de dados (da tabela Usuario).
     * O 'ON DELETE CASCADE' remove da tabela Paciente.
     */
    public void excluir(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";

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
     * Método helper PRIVADO para criar um objeto Paciente a partir de um ResultSet.
     * Esta é a implementação que estava faltando.
     */
    private Paciente instanciarPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setId(rs.getInt("id")); // 'id' da tabela Usuario
        paciente.setNomeCompleto(rs.getString("nomeCompleto"));
        paciente.setCpf(rs.getString("cpf"));
        paciente.setCep(rs.getString("cep"));
        paciente.setSenha(rs.getString("senha")); // Cuidado: não é boa prática trafegar senhas

        Date dataNasc = rs.getDate("dataNascimento");
        if (dataNasc != null) {
            paciente.setDataNascimento(dataNasc.toLocalDate());
        }

        // NOTA: Listas (Cobertura, Consulta, etc.) não são carregadas aqui.
        // Elas seriam carregadas por seus próprios DAOs (ex: CoberturaDAO)
        // quando necessário (Lazy Loading).

        return paciente;
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM Paciente";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}