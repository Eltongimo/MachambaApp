package com.example.machambaapp.model.datamodel;

public class Distrito {

    public String nome, key;

    public Distrito(String nome, String key) {
        this.nome = nome;
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
}
