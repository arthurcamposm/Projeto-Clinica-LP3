package org.example.clinicafx.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Agenda {
    private int id;
    private List<Consulta> consultasMarcadas;
    private List<LocalDateTime> disponibilidades;

    // Construtor vazio
    public Agenda() {
        this.consultasMarcadas = new ArrayList<>();
        this.disponibilidades = new ArrayList<>();
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Consulta> getConsultasMarcadas() {
        return consultasMarcadas;
    }

    public void setConsultasMarcadas(List<Consulta> consultasMarcadas) {
        this.consultasMarcadas = consultasMarcadas;
    }

    public List<LocalDateTime> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<LocalDateTime> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    // --- Outros Métodos (Helpers) ---

    public void adicionarConsulta(Consulta consulta) {
        this.consultasMarcadas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        this.consultasMarcadas.remove(consulta);
    }

    public void adicionarDisponibilidade(LocalDateTime dataHora) {
        this.disponibilidades.add(dataHora);
    }

    public void removerDisponibilidade(LocalDateTime dataHora) {
        this.disponibilidades.remove(dataHora);
    }
}
