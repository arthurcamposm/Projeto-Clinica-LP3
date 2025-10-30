package org.example.clinicafx.model; // Pacote 'model'

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Paciente "é um" Usuario (usando herança)
public class Paciente extends Usuario {

    // Você precisará criar estas classes (Prontuario, Cobertura, Consulta)
    private Prontuario prontuario;
    private List<Cobertura> coberturaPlanos;
    private List<Consulta> historicoDeConsultas;

    // Construtor vazio
    public Paciente() {
        super(); // Chama o construtor de Usuario
        this.coberturaPlanos = new ArrayList<>();
        this.historicoDeConsultas = new ArrayList<>();
    }

    // Construtor parametrizado (para criar um novo paciente)
    public Paciente(String nomeCompleto, LocalDate dataNascimento, String cpf, String cep, String senha) {
        super(nomeCompleto, dataNascimento, cpf, cep, senha); // Passa dados para o construtor de Usuario
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