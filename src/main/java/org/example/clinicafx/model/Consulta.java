package org.example.clinicafx.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int id;
    private LocalDate dataConsulta;
    private LocalTime horarioConsulta;
    private Paciente paciente;
    private Medico medico;
    private Atendente atendente;
    private String estado;
    private Float valorConsulta;

    // Construtor vazio
    public Consulta() {
    }

    // Construtor parametrizado
    public Consulta(Paciente paciente, Medico medico, LocalDate dataConsulta, LocalTime horarioConsulta, Atendente atendente, String estado, float valorConsulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataConsulta = dataConsulta;
        this.horarioConsulta = horarioConsulta;
        this.atendente = atendente;
        this.estado = estado;
        this.valorConsulta = valorConsulta;
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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public LocalTime getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(LocalTime horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(float valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
}
