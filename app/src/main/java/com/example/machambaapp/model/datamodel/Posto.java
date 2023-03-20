package com.example.machambaapp.model.datamodel;

public class Posto {
    String nome, localidade, key;

    public Posto(String nome, String localidade, String key) {
        this.nome = nome;
        this.localidade = localidade;
        this.key = key;

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

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}
