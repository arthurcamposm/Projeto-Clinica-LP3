package org.example.clinicafx.dao;

import org.example.clinicafx.model.PlanoDeSaude;
import org.example.clinicafx.util.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoDeSaudeDAO {

    public void salvar(PlanoDeSaude plano) {
        String sql = "INSERT INTO PlanoDeSaude (nomePlano, nomeOperadora) VALUES (?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plano.getNomePlano());
            ps.setString(2, plano.getNomeOperadora());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanoDeSaude> listarTodos() {
        String sql = "SELECT * FROM PlanoDeSaude";
        List<PlanoDeSaude> lista = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PlanoDeSaude p = new PlanoDeSaude();
                p.setId(rs.getInt("id"));
                p.setNomePlano(rs.getString("nomePlano"));
                p.setNomeOperadora(rs.getString("nomeOperadora"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}