package com.example.machambaapp.model.datamodel;

public class Posto {
    String nome, localidade;

    public Posto(String nome, String localidade) {
        this.nome = nome;
        this.localidade = localidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}
