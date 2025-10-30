package org.example.clinicafx.model;

import java.time.LocalDate;

public class Avaliacao {
    private int id;
    private Medico medico;
    private Paciente paciente;
    private int nota;
    private String comentario;
    private LocalDate dataAvaliacao;

    // Construtor vazio
    public Avaliacao() {
    }

    // Construtor parametrizado
    public Avaliacao(int nota, String comentario, LocalDate dataAvaliacao, Medico medico, Paciente paciente) {
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = dataAvaliacao;
        this.medico = medico;
        this.paciente = paciente;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
