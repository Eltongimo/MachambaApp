package com.example.machambaapp.model.datamodel;

public class Distrito {

    public String nome, key, provincia;

    public Distrito(String nome, String key, String provincia) {
        this.nome = nome;
        this.key = key;
        this.provincia = provincia;
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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
