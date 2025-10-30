package org.example.clinicafx.model;

import java.time.LocalDate;

public class Atestado {
    private int id;
    private Consulta consulta;
    private LocalDate dataEmissao;
    private int quantidadeDiasAfastamento;
    private String descricao;

    // Construtor vazio
    public Atestado() {
    }

    // Construtor parametrizado
    public Atestado(Consulta consulta, LocalDate dataEmissao, int quantidadeDiasAfastamento, String descricao) {
        this.consulta = consulta;
        this.dataEmissao = dataEmissao;
        this.quantidadeDiasAfastamento = quantidadeDiasAfastamento;
        this.descricao = descricao;
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

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getQuantidadeDiasAfastamento() {
        return quantidadeDiasAfastamento;
    }

    public void setQuantidadeDiasAfastamento(int quantidadeDiasAfastamento) {
        this.quantidadeDiasAfastamento = quantidadeDiasAfastamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
