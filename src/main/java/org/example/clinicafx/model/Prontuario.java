package org.example.clinicafx.model;

import java.util.ArrayList;
import java.util.List;

public class Prontuario {
    private int id;
    private Paciente paciente;
    private List<RelatorioConsulta> relatorios;
    private List<Prescricao> prescricoes;

    // Construtor vazio
    public Prontuario() {
        this.relatorios = new ArrayList<>();
        this.prescricoes = new ArrayList<>();
    }

    // Construtor parametrizado
    public Prontuario(Paciente paciente) {
        this.paciente = paciente;
        this.relatorios = new ArrayList<>();
        this.prescricoes = new ArrayList<>();
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

    public List<RelatorioConsulta> getRelatorios() {
        return relatorios;
    }

    public void setRelatorios(List<RelatorioConsulta> relatorios) {
        this.relatorios = relatorios;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }
}
