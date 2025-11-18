package org.example.clinicafx.dao;

import org.example.clinicafx.model.Medico;
import org.example.clinicafx.model.Paciente;
import org.example.clinicafx.model.Usuario;
import org.example.clinicafx.model.Atendente;
import org.example.clinicafx.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    /**
     * Valida o login de um usuário.
     * ATENÇÃO: Em um projeto real, NUNCA armazene senhas em texto puro.
     * Use HASH (ex: BCrypt). Este método seria "buscarPorCpf" e a senha
     * seria verificada no Java.
     */
    public Usuario validarLogin(String cpf, String senha) {
        // SQL perigoso em produção! Apenas para fins acadêmicos.
        String sql = "SELECT id FROM Usuario WHERE cpf = ? AND senha = ?";

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Usuário e senha corretos!
                    int usuarioId = rs.getInt("id");
                    // Agora, descubra QUE TIPO de usuário ele é
                    return buscarTipoDeUsuario(usuarioId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
        }

        // Se não encontrou ou deu erro
        return null;
    }

    /**
     * Helper que descobre se o usuário é Paciente, Médico ou Atendente
     * e retorna o objeto completo.
     */
    private Usuario buscarTipoDeUsuario(int usuarioId) {
        // Tenta buscar como Paciente
        // (Assume que você tem um PacienteDAO, MedicoDAO, etc.)
        PacienteDAO pacienteDAO = new PacienteDAO();
        Paciente paciente = pacienteDAO.buscarPorId(usuarioId);
        if (paciente != null) {
            return paciente;
        }

        // Tenta buscar como Médico (Implementação futura)
        // MedicoDAO medicoDAO = new MedicoDAO();
        // Medico medico = medicoDAO.buscarPorId(usuarioId);
        // if (medico != null) {
        //     return medico;
        // }

        // Tenta buscar como Atendente (Implementação futura)
        // AtendenteDAO atendenteDAO = new AtendenteDAO();
        // Atendente atendente = atendenteDAO.buscarPorId(usuarioId);
        // if (atendente != null) {
        //     return atendente;
        // }

        // Se for um usuário que não é de nenhum tipo (improvável)
        return null;
    }
}