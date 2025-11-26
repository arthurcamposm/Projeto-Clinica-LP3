package org.example.clinicafx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Usuario {
    private String crm;
    private List<Especialidade> especialidades;
    private Agenda agenda;


    public Medico() {
        super();
        this.especialidades = new ArrayList<>();
    }


    public Medico(String nomeCompleto, LocalDate dataNascimento, String cpf, String cep, String senha, String crm) {
        super(nomeCompleto, dataNascimento, cpf, cep, senha);
        this.crm = crm;
        this.especialidades = new ArrayList<>();
    }

    // --- Getters e Setters ---

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
