package org.example.clinicafx.model;

import java.time.LocalDateTime;

public class Pagamento {
    private int id;
    private float valor;
    private String metodo;
    private String status;
    private LocalDateTime dataPagamento;
    private String idTransacao;
    private Consulta consulta;

    // Construtor vazio
    public Pagamento() {
    }

    // Construtor parametrizado
    public Pagamento(float valor, String metodo, String status, LocalDateTime dataPagamento, Consulta consulta) {
        this.valor = valor;
        this.metodo = metodo;
        this.status = status;
        this.dataPagamento = dataPagamento;
        this.consulta = consulta;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
