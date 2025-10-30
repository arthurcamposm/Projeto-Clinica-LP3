package org.example.clinicafx.model;

import java.time.LocalDate;

public class Cobertura {
    private int id;
    private Paciente paciente;
    private PlanoDeSaude planoDeSaude;
    private String numeroCarteirinha;
    private LocalDate validade;
    private boolean ativo;

    // Construtor vazio
    public Cobertura() {
    }

    // Construtor parametrizado
    public Cobertura(Paciente paciente, PlanoDeSaude planoDeSaude, String numeroCarteirinha, LocalDate validade, boolean ativo) {
        this.paciente = paciente;
        this.planoDeSaude = planoDeSaude;
        this.numeroCarteirinha = numeroCarteirinha;
        this.validade = validade;
        this.ativo = ativo;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public PlanoDeSaude getPlanoDeSaude() {
        return planoDeSaude;
    }

    public void setPlanoDeSaude(PlanoDeSaude planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }

    public String getNumeroCarteirinha() {
        return numeroCarteirinha;
    }

    public void setNumeroCarteirinha(String numeroCarteirinha) {
        this.numeroCarteirinha = numeroCarteirinha;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
