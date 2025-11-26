package org.example.clinicafx.dao;

import org.example.clinicafx.model.Consulta;
import org.example.clinicafx.model.Medico;
import org.example.clinicafx.model.Paciente;
import org.example.clinicafx.util.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public void agendar(Consulta consulta) {
        String sql = "INSERT INTO Consulta (paciente_id, medico_id, dataConsulta, horarioConsulta, estado, valorConsulta) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, consulta.getPaciente().getId());
            ps.setInt(2, consulta.getMedico().getId());
            ps.setDate(3, Date.valueOf(consulta.getDataConsulta()));
            ps.setTime(4, Time.valueOf(consulta.getHorarioConsulta()));
            ps.setString(5, "Agendada"); // Estado inicial padrão
            ps.setFloat(6, consulta.getValorConsulta());

            ps.executeUpdate();
            System.out.println("Consulta agendada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao agendar consulta: " + e.getMessage());
        }
    }

    // Método para listar consultas na tela
    public List<Consulta> listarTodas() {
        return new ArrayList<>();
    }


    public List<Consulta> listarTodasComDetalhes() {
        String sql = "SELECT c.*, p.nomeCompleto as nomePaciente, m.nomeCompleto as nomeMedico " +
                "FROM Consulta c " +
                "JOIN Usuario p ON c.paciente_id = p.id " +
                "JOIN Usuario m ON c.medico_id = m.id";

        List<Consulta> lista = new ArrayList<>();

        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setDataConsulta(rs.getDate("dataConsulta").toLocalDate());
                c.setHorarioConsulta(rs.getTime("horarioConsulta").toLocalTime());
                c.setEstado(rs.getString("estado"));

                Paciente p = new Paciente();
                p.setNomeCompleto(rs.getString("nomePaciente"));
                c.setPaciente(p);

                Medico m = new Medico();
                m.setNomeCompleto(rs.getString("nomeMedico"));
                c.setMedico(m);

                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
