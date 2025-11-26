package org.example.clinicafx.dao;

import org.example.clinicafx.model.Medico;
import org.example.clinicafx.util.ConexaoMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    /**
     * Salva um novo médico (Inserindo em Usuario e depois em Medico)
     */
    public void salvar(Medico medico) {
        String sqlUsuario = "INSERT INTO Usuario (nomeCompleto, dataNascimento, cpf, cep, senha) VALUES (?, ?, ?, ?, ?)";
        String sqlMedico = "INSERT INTO Medico (usuario_id, crm) VALUES (?, ?)";
        String sqlVinculo = "INSERT INTO Medico_Especialidade (medico_id, especialidade_id) VALUES (?, ?)";

        try (Connection conexao = ConexaoMySQL.getConexao()) {
            conexao.setAutoCommit(false); // Inicia Transação

            int usuarioId = -1;

            // Salva o Usuario
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, medico.getNomeCompleto());
                if (medico.getDataNascimento() != null) {
                    psUsuario.setDate(2, Date.valueOf(medico.getDataNascimento()));
                } else {
                    psUsuario.setNull(2, Types.DATE);
                }
                psUsuario.setString(3, medico.getCpf());
                psUsuario.setString(4, medico.getCep());
                psUsuario.setString(5, medico.getSenha());
                psUsuario.executeUpdate();

                try (ResultSet rs = psUsuario.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuarioId = rs.getInt(1);
                        medico.setId(usuarioId);
                    }
                }
            }

            // Salva o Médico (Vinculando pelo ID)
            try (PreparedStatement psMedico = conexao.prepareStatement(sqlMedico)) {
                psMedico.setInt(1, usuarioId);
                psMedico.setString(2, medico.getCrm());
                psMedico.executeUpdate();
            }

            // Salva a Especialidade (se houver alguma selecionada)
            if (!medico.getEspecialidades().isEmpty()) {
                try (PreparedStatement psVinculo = conexao.prepareStatement(sqlVinculo)) {
                    // Pegamos a primeira especialidade da lista para salvar
                    int especialidadeId = medico.getEspecialidades().get(0).getId();

                    psVinculo.setInt(1, usuarioId);
                    psVinculo.setInt(2, especialidadeId);
                    psVinculo.executeUpdate();
                }
            }

            conexao.commit(); // Confirma
            System.out.println("Médico salvo com sucesso! ID: " + usuarioId);

        } catch (SQLException e) {
            System.err.println("Erro ao salvar médico: " + e.getMessage());
        }
    }

    /**
     * Busca médico por CRM (Importante para evitar duplicidade)
     */
    public Medico buscarPorCrm(String crm) {
        String sql = "SELECT * FROM Usuario u JOIN Medico m ON u.id = m.usuario_id WHERE m.crm = ?";
        Medico medico = null;

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, crm);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico();
                    medico.setId(rs.getInt("id"));
                    medico.setNomeCompleto(rs.getString("nomeCompleto"));
                    medico.setCpf(rs.getString("cpf"));
                    medico.setCrm(rs.getString("crm"));
                    medico.setCep(rs.getString("cep"));

                    // Conversão de Data do SQL para JavaFX
                    java.sql.Date dataSql = rs.getDate("dataNascimento");
                    if (dataSql != null) {
                        medico.setDataNascimento(dataSql.toLocalDate());
                    }


                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar médico por CRM: " + e.getMessage());
        }
        return medico;
    }


    public void atualizar(Medico medico) {
        String sqlUsuario = "UPDATE Usuario SET nomeCompleto = ?, dataNascimento = ?, cpf = ?, cep = ?, senha = ? WHERE id = ?";
        String sqlMedico = "UPDATE Medico SET crm = ? WHERE usuario_id = ?";

        try (Connection conexao = ConexaoMySQL.getConexao()) {
            conexao.setAutoCommit(false); // Transação

            // Atualiza dados pessoais (Tabela Usuario)
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, medico.getNomeCompleto());
                if (medico.getDataNascimento() != null) {
                    psUsuario.setDate(2, java.sql.Date.valueOf(medico.getDataNascimento()));
                } else {
                    psUsuario.setNull(2, java.sql.Types.DATE);
                }
                psUsuario.setString(3, medico.getCpf());
                psUsuario.setString(4, medico.getCep());
                psUsuario.setString(5, medico.getSenha());
                psUsuario.setInt(6, medico.getId());
                psUsuario.executeUpdate();
            }

            // Atualiza dados específicos (Tabela Medico)
            try (PreparedStatement psMedico = conexao.prepareStatement(sqlMedico)) {
                psMedico.setString(1, medico.getCrm());
                psMedico.setInt(2, medico.getId()); // usuario_id
                psMedico.executeUpdate();
            }

            conexao.commit();
            System.out.println("Médico atualizado com sucesso! ID: " + medico.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar médico: " + e.getMessage());
        }
    }


    public List<Medico> listarTodos() {
        String sql = "SELECT * FROM Usuario u JOIN Medico m ON u.id = m.usuario_id";
        List<Medico> medicos = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNomeCompleto(rs.getString("nomeCompleto"));
                m.setCrm(rs.getString("crm"));
                medicos.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar médicos: " + e.getMessage());
        }
        return medicos;
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM Medico";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}
