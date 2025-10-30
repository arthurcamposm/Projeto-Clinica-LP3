package org.example.clinicafx.model;

public class PlanoDeSaude {
    private int id;
    private String nomePlano;
    private String nomeOperadora;

    // Construtor vazio
    public PlanoDeSaude() {
    }

    // Construtor parametrizado
    public PlanoDeSaude(String nomePlano, String nomeOperadora) {
        this.nomePlano = nomePlano;
        this.nomeOperadora = nomeOperadora;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public String getNomeOperadora() {
        return nomeOperadora;
    }

    public void setNomeOperadora(String nomeOperadora) {
        this.nomeOperadora = nomeOperadora;
    }
}
