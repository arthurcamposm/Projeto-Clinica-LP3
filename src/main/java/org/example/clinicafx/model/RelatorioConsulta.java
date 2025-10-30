package org.example.clinicafx.model;

public class RelatorioConsulta {
    private int id;
    private Consulta consulta;
    private String observacoes;

    // Construtor vazio
    public RelatorioConsulta() {
    }

    // Construtor parametrizado
    public RelatorioConsulta(Consulta consulta, String observacoes) {
        this.consulta = consulta;
        this.observacoes = observacoes;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
