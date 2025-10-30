package org.example.clinicafx.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Prescricao {
    private int id;
    private Consulta consulta;
    private List<String> medicamentos;
    private LocalDate dataEmissao;

    // Construtor vazio
    public Prescricao() {
        this.medicamentos = new ArrayList<>();
    }

    // Construtor parametrizado
    public Prescricao(Consulta consulta, LocalDate dataEmissao) {
        this.consulta = consulta;
        this.dataEmissao = dataEmissao;
        this.medicamentos = new ArrayList<>();
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

    public List<String> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<String> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}
