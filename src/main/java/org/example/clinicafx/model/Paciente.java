package org.example.clinicafx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Paciente extends Usuario {

    private Prontuario prontuario;
    private List<Cobertura> coberturaPlanos;
    private List<Consulta> historicoDeConsultas;


    public Paciente() {
        super();
        this.coberturaPlanos = new ArrayList<>();
        this.historicoDeConsultas = new ArrayList<>();
    }


    public Paciente(String nomeCompleto, LocalDate dataNascimento, String cpf, String cep, String senha) {
        super(nomeCompleto, dataNascimento, cpf, cep, senha);
        this.coberturaPlanos = new ArrayList<>();
        this.historicoDeConsultas = new ArrayList<>();
    }

    // --- Getters e Setters ---

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public List<Cobertura> getCoberturaPlanos() {
        return coberturaPlanos;
    }

    public void setCoberturaPlanos(List<Cobertura> coberturaPlanos) {
        this.coberturaPlanos = coberturaPlanos;
    }

    public List<Consulta> getHistoricoDeConsultas() {
        return historicoDeConsultas;
    }

    public void setHistoricoDeConsultas(List<Consulta> historicoDeConsultas) {
        this.historicoDeConsultas = historicoDeConsultas;
    }
}