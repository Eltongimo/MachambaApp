package com.example.machambaapp.model.datamodel;

public class Localidade {
    String nome, distrito, key;

    public Localidade(String nome, String distrito, String key) {
        this.nome = nome;
        this.key = key;
        this.distrito = distrito;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
