package org.example.clinicafx.model;

import java.time.LocalDate;

public class Confirmacao {
    private int id;
    private Consulta consulta;
    private String status;
    private LocalDate dataConfirmacao;
    private String canal;

    // Construtor vazio
    public Confirmacao() {
    }

    // Construtor parametrizado
    public Confirmacao(String status, LocalDate dataConfirmacao, String canal, Consulta consulta) {
        this.status = status;
        this.dataConfirmacao = dataConfirmacao;
        this.canal = canal;
        this.consulta = consulta;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDate dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
