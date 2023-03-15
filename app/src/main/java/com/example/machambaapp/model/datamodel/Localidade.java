package com.example.machambaapp.model.datamodel;

public class Localidade {
    String nome, distrito;

    public Localidade(String nome, String distrito) {
        this.nome = nome;
        this.distrito = distrito;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}
