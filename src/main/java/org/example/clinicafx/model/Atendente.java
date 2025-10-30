package org.example.clinicafx.model;

import java.time.LocalDate;

public class Atendente extends Usuario {
    private int matricula;
    private String turno;

    // Construtor vazio
    public Atendente() {
        super();
    }

    // Construtor parametrizado
    public Atendente(String nomeCompleto, LocalDate dataNascimento, String cpf, String cep, String senha, int matricula, String turno) {
        super(nomeCompleto, dataNascimento, cpf, cep, senha);
        this.matricula = matricula;
        this.turno = turno;
    }

    // --- Getters e Setters ---

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

}
