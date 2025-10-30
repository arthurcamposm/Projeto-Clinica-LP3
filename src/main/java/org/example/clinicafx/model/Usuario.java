package org.example.clinicafx.model; // Pacote 'model'

import java.time.LocalDate;

// Classe base abstrata
public abstract class Usuario {
    private int id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private String cep;
    private String senha;

    // Construtor vazio
    public Usuario() {}

    // Construtor com campos
    public Usuario(String nomeCompleto, LocalDate dataNascimento, String cpf, String cep, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.cep = cep;
        this.senha = senha;
    }

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}