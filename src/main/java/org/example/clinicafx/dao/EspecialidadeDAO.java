package org.example.clinicafx.dao;

import org.example.clinicafx.model.Especialidade;
import org.example.clinicafx.util.ConexaoMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {

    /**
     * Salva uma nova especialidade no banco de dados.
     */
    public void salvar(Especialidade especialidade) {
        String sql = "INSERT INTO Especialidade (nome, descricao) VALUES (?, ?)";

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, especialidade.getNome());
            ps.setString(2, especialidade.getDescricao());

            ps.executeUpdate();

            // Recupera o ID gerado automaticamente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    especialidade.setId(rs.getInt(1));
                    System.out.println("Especialidade salva com sucesso! ID: " + especialidade.getId());
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar especialidade: " + e.getMessage());
        }
    }

    /**
     * Lista todas as especialidades cadastradas.
     */
    public List<Especialidade> listarTodas() {
        String sql = "SELECT * FROM Especialidade";
        List<Especialidade> lista = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Especialidade e = new Especialidade();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setDescricao(rs.getString("descricao"));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar especialidades: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Exclui uma especialidade pelo ID.
     */
    public void excluir(int id) {
        String sql = "DELETE FROM Especialidade WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Especialidade excluída com sucesso! ID: " + id);

        } catch (SQLException e) {
            System.err.println("Erro ao excluir especialidade: " + e.getMessage());
        }
    }

    /**
     * Busca por nome (para evitar duplicatas)
     */
    public Especialidade buscarPorNome(String nome) {
        String sql = "SELECT * FROM Especialidade WHERE nome = ?";
        Especialidade especialidade = null;

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, nome);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    especialidade = new Especialidade();
                    especialidade.setId(rs.getInt("id"));
                    especialidade.setNome(rs.getString("nome"));
                    especialidade.setDescricao(rs.getString("descricao"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar especialidade por nome: " + e.getMessage());
        }
        return especialidade;
    }
}